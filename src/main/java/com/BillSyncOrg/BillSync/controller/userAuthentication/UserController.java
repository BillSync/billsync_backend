package com.BillSyncOrg.BillSync.controller.userAuthentication;

import com.BillSyncOrg.BillSync.context.RequestContext;
import com.BillSyncOrg.BillSync.dto.userAuthentication.SignInRequest;
import com.BillSyncOrg.BillSync.dto.userAuthentication.SignInResponse;
import com.BillSyncOrg.BillSync.dto.userAuthentication.SignupRequest;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.serverExceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.model.User;
import com.BillSyncOrg.BillSync.service.userAuthentication.UserService;
import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;
import com.BillSyncOrg.BillSync.util.ResponseGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for exposing user-related API endpoints.
 * <p>
 * This controller handles all HTTP requests related to user management,
 * including user registration (sign-up).
 * </p>
 *
 * @see UserService
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

  private final UserService userService;

  /**
   * Constructs the controller with the user service.
   *
   * @param userService the service that contains the business logic for user operations
   */
  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * Endpoint for registering a new user.
   * <p>
   * Accepts a validated request body and delegates to the service for processing.
   * </p>
   *
   * @param request the validated sign-up request containing email, phone, and password
   * @return a {@link ResponseEntity} containing the created user
   */
  @PostMapping("/signup")
  public ResponseEntity<Object> signUp(@Valid @RequestBody SignupRequest request) throws BillSyncClientException, BillSyncServerException {
    User user = userService.registerUser(request);
    return ResponseGenerator.builder().body(user).status(HttpStatusCodeEnum.CREATED).message("Account created successfully!").build();
  }

  /**
   * Endpoint for user login.
   * <p>
   * Accepts a validated request body and delegates to the service for processing.
   * </p>
   *
   * @param request the validated sign-in request containing email or phone, and password
   * @return a {@link ResponseEntity} containing the generated jwt.
   */
  @PostMapping("/login")
  public ResponseEntity<Object> login(@Valid @RequestBody SignInRequest request) throws BillSyncClientException, BillSyncServerException {
    SignInResponse response = userService.SignInUser(request);
    return ResponseGenerator.builder().body(response).status(HttpStatusCodeEnum.OK).message("Login successfully!").build();
  }

  /**
   * Logs out the user by blacklisting the JWT token provided in the Authorization header.
   *
   * @param request the HTTP request containing the token.
   * @return a ResponseEntity indicating logout success or failure.
   */
  @PostMapping("/logout")
  public ResponseEntity<Object> logout(HttpServletRequest request) throws BillSyncServerException {
    userService.logoutUser(request);
    return ResponseGenerator.builder().status(HttpStatusCodeEnum.OK).message("Logout " +
      "successfully!").build();
  }

  /**
   * Provides the userId for the logged-in user.
   *
   * @return a ResponseEntity containing the userId.
   */
  @GetMapping("/me")
  public ResponseEntity<Object> getMyUserId() {
    return ResponseGenerator.builder().body(RequestContext.getUserId()).status(HttpStatusCodeEnum.OK).message("successfully!").build();
  }
}
