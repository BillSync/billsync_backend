package com.BillSyncOrg.BillSync.controller.users;

import com.BillSyncOrg.BillSync.dto.userRecords.FindUserRequest;
import com.BillSyncOrg.BillSync.exceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.model.User;
import com.BillSyncOrg.BillSync.projection.allUsers.UserIDNameProjection;
import com.BillSyncOrg.BillSync.repository.UserRepository;
import com.BillSyncOrg.BillSync.service.users.AllUsersService;
import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;
import com.BillSyncOrg.BillSync.util.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller that exposes HTTP endpoints for retrieving user records from the MongoDB database.
 * <p>
 * This controller serves as the entry point for client requests related to retrieving all user
 * records. It delegates business logic to the {@link AllUsersService} layer, which handles the
 * actual data retrieval from the database via the {@link UserRepository}.
 * </p>
 *
 * <p>
 * The controller returns results wrapped in a {@link ResponseEntity} using a custom
 * {@link ResponseGenerator} builder to maintain a standardized API response format. This ensures
 * consistent status codes, response messages, and payload structures across the application.
 * </p>
 *
 * <p><b>Example Request:</b></p>
 * <pre>
 * GET /api/user-records/all-users
 * </pre>
 *
 * <p><b>Example JSON Response:</b></p>
 * <pre>
 * {
 *   "status": "OK",
 *   "message": "successful!",
 *   "body": [
 *     {
 *       "id": "64f1a8b7c1",
 *       "name": "Alice",
 *       "email": "alice@example.com"
 *     },
 *     {
 *       "id": "64f1a8b8d2",
 *       "name": "Bob",
 *       "email": "bob@example.com"
 *     }
 *   ]
 * }
 * </pre>
 *
 * @author YourName
 * @see AllUsersService
 * @see User
 * @see ResponseGenerator
 * @see BillSyncServerException
 */
@RestController
@RequestMapping("/api/user-records")
public class AllUsersController {

  private final AllUsersService allUsersService;

  /**
   * Constructs a new {@code AllUsersController} with the specified {@link AllUsersService}.
   *
   * @param allUsersService the service used to retrieve user records from the database
   */
  @Autowired
  public AllUsersController(AllUsersService allUsersService) {
    this.allUsersService = allUsersService;
  }

  /**
   * Retrieves all user records from the MongoDB database and returns them in a standardized API response.
   * <p>
   * This method delegates the data retrieval task to the {@link AllUsersService#getAllUsers()} method.
   * The resulting list of {@link User} entities is wrapped inside a custom response object using
   * the {@link ResponseGenerator} builder, ensuring a consistent response structure across endpoints.
   * </p>
   *
   * <p><b>Response:</b></p>
   * <ul>
   *   <li>{@code status} - HTTP status code represented by {@link HttpStatusCodeEnum}</li>
   *   <li>{@code message} - A human-readable message indicating the operation's result</li>
   *   <li>{@code body} - The list of all {@link User} objects retrieved from the database</li>
   * </ul>
   *
   * @return a {@link ResponseEntity} containing a standardized API response with all user records
   * @throws BillSyncServerException if an error occurs while retrieving the user records
   */
  @GetMapping("/all-users")
  public ResponseEntity<Object> getAllUserRecords() throws BillSyncServerException {
    List<UserIDNameProjection> users = allUsersService.getAllUsers();
    return ResponseGenerator.builder()
      .body(users)
      .status(HttpStatusCodeEnum.OK)
      .message("successful!")
      .build();
  }

  /**
   * Handles HTTP POST requests for finding a user by email or phone number.
   * <p>
   * This endpoint accepts a {@link FindUserRequest} containing the search value.
   * The controller delegates the search operation to {@link AllUsersService#getUserByEmailOrPhone(String)},
   * which performs a lookup in the MongoDB database using a projection to return only
   * the user's id and name.
   * </p>
   *
   * <p><b>Endpoint:</b></p>
   * <ul>
   *     <li>POST /api/user-records/find-user</li>
   * </ul>
   *
   * <p><b>Request Body:</b></p>
   * <pre>
   * {
   *   "searchValue": "alice@example.com"
   * }
   * </pre>
   *
   * <p><b>Response:</b></p>
   * <ul>
   *     <li>{@code status} - HTTP status code represented by {@link HttpStatusCodeEnum}</li>
   *     <li>{@code message} - Indicates success of the operation</li>
   *     <li>{@code body} - {@link UserIDNameProjection} containing only {@code id} and {@code
   *     name}</li>
   * </ul>
   *
   * <p><b>Exceptions:</b></p>
   * <ul>
   *     <li>{@link BillSyncServerException} - thrown if a server-side error occurs during the search</li>
   *     <li>{@link BillSyncClientException} - thrown if the client request is invalid</li>
   * </ul>
   *
   * <p><b>Example Response:</b></p>
   * <pre>
   * {
   *   "status": "OK",
   *   "message": "Successful!",
   *   "body": {
   *       "id": "64f1a8b7c1",
   *       "name": "Alice"
   *   }
   * }
   * </pre>
   *
   * @param findUserRequest the request DTO containing the email or phone number to search
   * @return {@link ResponseEntity} containing the projection of the user record
   * @throws BillSyncServerException if an internal server error occurs
   * @throws BillSyncClientException if the client request is invalid
   */
  @PostMapping("/find-user")
  public ResponseEntity<Object> getUserByEmailOrPhone(@RequestBody FindUserRequest findUserRequest) throws BillSyncServerException, BillSyncClientException {
    UserIDNameProjection user =
      allUsersService.getUserByEmailOrPhone(findUserRequest);
    return ResponseGenerator.builder().body(user).status(HttpStatusCodeEnum.OK).message(
      "Successful!").build();
  }
}

