package com.BillSyncOrg.BillSync.controller;

import com.BillSyncOrg.BillSync.dto.SignInRequest;
import com.BillSyncOrg.BillSync.dto.SignInResponse;
import com.BillSyncOrg.BillSync.dto.SignupRequest;
import com.BillSyncOrg.BillSync.exceptions.BillSyncException;
import com.BillSyncOrg.BillSync.exceptions.UserSignInException;
import com.BillSyncOrg.BillSync.exceptions.UserSignupException;
import com.BillSyncOrg.BillSync.model.User;
import com.BillSyncOrg.BillSync.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseEntity<User> signUp(@Valid @RequestBody SignupRequest request) throws BillSyncException {
    User user = userService.registerUser(request);
    return ResponseEntity.ok(user);
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
  public ResponseEntity<SignInResponse> login(@Valid @RequestBody SignInRequest request) throws BillSyncException {
    SignInResponse response = userService.SignInUser(request);
    return ResponseEntity.ok(response);
  }
}
