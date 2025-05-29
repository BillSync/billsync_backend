package com.BillSyncOrg.BillSync.service;


import com.BillSyncOrg.BillSync.dto.SignInRequest;
import com.BillSyncOrg.BillSync.dto.SignInResponse;
import com.BillSyncOrg.BillSync.exceptions.UserSignInException;
import com.BillSyncOrg.BillSync.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.BillSyncOrg.BillSync.dto.SignupRequest;
import com.BillSyncOrg.BillSync.model.User;
import com.BillSyncOrg.BillSync.repository.UserRepository;
import com.BillSyncOrg.BillSync.exceptions.UserSignupException;

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

  private final JwtUtil jwtUtil;

  /**
   * Constructs the service with required dependencies.
   *
   * @param userRepository the repository used to persist and fetch user data
   */
  @Autowired
  public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
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
   * @throws UserSignupException if the email or phone number already exists
   */
  public User registerUser(SignupRequest request) throws UserSignupException {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new UserSignupException("Email already in use");
    }

    if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
      throw new UserSignupException("Phone number already in use");
    }

    User user = new User();
    user.setEmail(request.getEmail());
    user.setPhoneNumber(request.getPhoneNumber());
    user.setPassword(passwordEncoder.encode(request.getPassword()));

    return userRepository.save(user);
  }

  /**
   * Authenticates a user using either email or phone number and issues a JWT on success.
   * @param SignInRequest the login credentials provided by the user
   * @return a {@link SignInResponse} containing the generated JWT
   * @throws UserSignInException if no matching user is found or if the password is incorrect
   */
  public SignInResponse SignInUser(SignInRequest SignInRequest) throws UserSignInException {
    String password = SignInRequest.getPassword();

    User user;

    if (SignInRequest.getEmail() != null && !SignInRequest.getEmail().isBlank()) {
      user = userRepository.findByEmail(SignInRequest.getEmail())
        .orElseThrow(() -> new UserSignInException("Invalid email/phone or password!"));
    } else {
      user = userRepository.findByPhoneNumber(SignInRequest.getPhoneNumber())
        .orElseThrow(() -> new UserSignInException("Invalid email/phone or password!"));
    }

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new UserSignInException("Invalid email/phone or password!");
    }

    String token = jwtUtil.generateToken(user.getId());
    user.setToken(token); // Store JWT in the user's record
    userRepository.save(user); // Update user with new token

    return new SignInResponse(token);
  }
}
