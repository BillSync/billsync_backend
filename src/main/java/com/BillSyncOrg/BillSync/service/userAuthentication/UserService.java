package com.BillSyncOrg.BillSync.service.userAuthentication;


import com.BillSyncOrg.BillSync.context.RequestContext;
import com.BillSyncOrg.BillSync.dto.userAuthentication.SignInRequest;
import com.BillSyncOrg.BillSync.dto.userAuthentication.SignInResponse;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.RecordNotFoundException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.UserSignInClientException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.UserSignupClientException;
import com.BillSyncOrg.BillSync.exceptions.serverExceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.exceptions.serverExceptions.JWTException;
import com.BillSyncOrg.BillSync.model.BlacklistedToken;
import com.BillSyncOrg.BillSync.repository.BlacklistedTokenRepository;
import com.BillSyncOrg.BillSync.util.enums.HttpStatusCodeEnum;
import com.BillSyncOrg.BillSync.util.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.BillSyncOrg.BillSync.dto.userAuthentication.SignupRequest;
import com.BillSyncOrg.BillSync.model.User;
import com.BillSyncOrg.BillSync.repository.UserRepository;

import java.util.List;

/**
 * Service class that handles business logic related to user registration.
 * <p>
 * This class provides the core implementation of the sign-up process, including:
 * <ul>
 *     <li>Validating uniqueness of email and phone number</li>
 *     <li>Encrypting user password using BCrypt</li>
 *     <li>Saving a new user to the MongoDB database</li>
 * </ul>
 * </p>
 */
@Service
public class UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final BlacklistedTokenRepository blacklistRepo;
  private final JwtUtil jwtUtil;

  /**
   * Constructs the service with required dependencies.
   *
   * @param userRepository the repository used to persist and fetch user data
   */
  @Autowired
  public UserService(UserRepository userRepository, JwtUtil jwtUtil, BlacklistedTokenRepository blacklistRepo) {
    this.userRepository = userRepository;
    this.blacklistRepo = blacklistRepo;
    this.passwordEncoder = new BCryptPasswordEncoder();
    this.jwtUtil = jwtUtil;
  }

  /**
   * Registers a new user in the system.
   * <p>
   * Validates that the email and phone number are not already in use.
   * Encrypts the password before persisting to the database.
   * </p>
   *
   * @param request the DTO containing user sign-up data
   * @return the created {@link User} object
   * @throws BillSyncClientException if the email or phone number already exists
   * @throws BillSyncServerException if there is an internal server error
   */
  public User registerUser(SignupRequest request) throws BillSyncClientException, BillSyncServerException {
    try {
      if (userRepository.findByEmail(request.getEmail()).isPresent()) {
        throw new UserSignupClientException("Email already in use", HttpStatusCodeEnum.BAD_REQUEST);
      }

      if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
        throw new UserSignupClientException("Phone number already in use", HttpStatusCodeEnum.BAD_REQUEST);
      }

      User user = new User();
      user.setName(request.getName());
      user.setEmail(request.getEmail());
      user.setPhoneNumber(request.getPhoneNumber());
      user.setPassword(passwordEncoder.encode(request.getPassword()));

      return userRepository.save(user);
    } catch (UserSignupClientException e) {
      throw new BillSyncClientException(e.getMessage(), e.getHttpStatusCode());
    } catch (Exception e) {
      throw new BillSyncServerException("Error while registering the user!", e, HttpStatusCodeEnum.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Authenticates a user using either email or phone number and issues a JWT on success.
   * @param SignInRequest the login credentials provided by the user
   * @return a {@link SignInResponse} containing the generated JWT
   * @throws UserSignInClientException if no matching user is found or if the password is incorrect
   */
  public SignInResponse SignInUser(SignInRequest SignInRequest) throws BillSyncClientException, BillSyncServerException {
    try {
      String password = SignInRequest.getPassword();

      User user;

      if (SignInRequest.getEmail() != null && !SignInRequest.getEmail().isBlank()) {
        user = userRepository.findByEmail(SignInRequest.getEmail())
          .orElseThrow(() -> new UserSignInClientException("Invalid email/phone or password!", HttpStatusCodeEnum.BAD_REQUEST));
      } else {
        user = userRepository.findByPhoneNumber(SignInRequest.getPhoneNumber())
          .orElseThrow(() -> new UserSignInClientException("Invalid email/phone or password!", HttpStatusCodeEnum.BAD_REQUEST));
      }

      if (!passwordEncoder.matches(password, user.getPassword())) {
        throw new UserSignInClientException("Invalid email/phone or password!", HttpStatusCodeEnum.BAD_REQUEST);
      }

      String token = jwtUtil.generateToken(user.getId());
      user.setCurrentToken(token);
      userRepository.save(user); // Update user with new token

      return new SignInResponse(token);
    } catch (UserSignInClientException e) {
      throw new BillSyncClientException(e.getMessage(), e.getHttpStatusCode());
    } catch (JWTException e) {
      throw new BillSyncServerException(e.getMessage(), e.getHttpStatusCode());
    } catch (Exception e) {
      throw new BillSyncServerException("Error while user login!", e, HttpStatusCodeEnum.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Logs out the user by blacklisting their JWT token.
   *
   * @param request the httpRequest to get header values.
   * @throws BillSyncServerException if token or userId is missing.
   */
  public void logoutUser(HttpServletRequest request) throws BillSyncServerException {
    try {
      String authHeader = request.getHeader("Authorization");
      String token = authHeader != null && authHeader.startsWith("Bearer ") ? authHeader.substring(7) : null;
      String userId = RequestContext.getUserId();

      if (userId == null || token == null) {
        throw new JWTException("Token is missing.", HttpStatusCodeEnum.UNAUTHORIZED);
      }

      BlacklistedToken blacklistedToken = new BlacklistedToken(token);
      blacklistRepo.save(blacklistedToken);
    } catch (JWTException e) {
      throw new BillSyncServerException(e.getMessage(), e.getHttpStatusCode());
    }
  }

  /**
   * Validates that all provided user IDs exist in the system.
   * <p>
   * This method queries the {@link UserRepository} to check if each user ID in the provided list
   * corresponds to an existing {@link User} record in the database. If one or more user IDs are not found,
   * a {@link RecordNotFoundException} is thrown.
   * </p>
   *
   * <p><b>Validation rules:</b></p>
   * <ul>
   *   <li>If {@code userIds} is {@code null} or empty, the method returns {@code true} without validation.</li>
   *   <li>If all user IDs exist, the method returns {@code true}.</li>
   *   <li>If any user ID does not exist, a {@link RecordNotFoundException} is thrown with
   *   {@link HttpStatusCodeEnum#BAD_REQUEST}.</li>
   * </ul>
   *
   * @param userIds the list of user IDs to validate; may be {@code null} or empty
   * @return {@code true} if all provided user IDs exist or if the list is {@code null}/empty
   * @throws RecordNotFoundException if one or more user IDs do not exist in the database
   */
  public boolean checkAllUsersExists (List<String> userIds) throws RecordNotFoundException {
    if (userIds !=null && !userIds.isEmpty()) {
      List<User> existingUsers = userRepository.findByIdIn(userIds);

      if(existingUsers.size() != userIds.size()) {
        throw new RecordNotFoundException("One or more users do not exist",
          HttpStatusCodeEnum.BAD_REQUEST);
      }
      return true;
    }
    return false;
  }
}
