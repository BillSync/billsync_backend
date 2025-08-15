package com.BillSyncOrg.BillSync.service.users;

import com.BillSyncOrg.BillSync.dto.userRecords.FindUserRequest;
import com.BillSyncOrg.BillSync.exceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.exceptions.RecordNotFoundException;
import com.BillSyncOrg.BillSync.model.User;
import com.BillSyncOrg.BillSync.projection.allUsers.UserIDNameProjection;
import com.BillSyncOrg.BillSync.repository.UserRepository;
import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for retrieving user records from the MongoDB database.
 * <p>
 * This class interacts with the {@link UserRepository} to fetch all user entities stored in the
 * "users" collection of the MongoDB database. It provides an abstraction layer between the
 * controller layer and the persistence layer, ensuring that database access logic is
 * encapsulated within the service.
 * </p>
 * <p>
 * If an error occurs during the retrieval of user records, a {@link BillSyncServerException}
 * is thrown with an appropriate error message and HTTP status code.
 * </p>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>
 * {@code
 * @Autowired
 * private AllUsersService allUsersService;
 *
 * public void printAllUsers() {
 *     List<User> users = allUsersService.getAllUsers();
 *     users.forEach(System.out::println);
 * }
 * }
 * </pre>
 *
 * @author YourName
 * @see UserRepository
 * @see User
 * @see BillSyncServerException
 * @see HttpStatusCodeEnum
 */
@Service
public class AllUsersService {

  private final UserRepository userRepository;

  /**
   * Constructs a new {@code AllUsersService} with the specified {@link UserRepository}.
   *
   * @param userRepository the repository interface for accessing user data from MongoDB
   */
  @Autowired
  public AllUsersService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Retrieves all {@link User} records from the MongoDB database.
   * <p>
   * This method delegates the call to {@link UserRepository#findAll()} to fetch all user
   * entities. In case of any exceptions (e.g., connectivity issues, query errors), it wraps
   * the original exception inside a {@link BillSyncServerException} and assigns the HTTP
   * status code {@link HttpStatusCodeEnum#INTERNAL_SERVER_ERROR}.
   * </p>
   *
   * @return a {@link List} of {@link User} objects representing all users stored in the database
   * @throws BillSyncServerException if any error occurs during database access
   */
  public List<UserIDNameProjection> getAllUsers() throws BillSyncServerException {
    try {
      return userRepository.findAllIDAndName();
    } catch (Exception e) {
      throw new BillSyncServerException(
        "Error while retrieving all user records!",
        e,
        HttpStatusCodeEnum.INTERNAL_SERVER_ERROR
      );
    }
  }

  /**
   * Searches for a user by email or phone number and returns only id and name.
   *
   * @param findUserRequest the email or phone number to search for
   * @return UserIdNameProjection containing id and name
   * @throws BillSyncServerException if no user is found
   */
  public UserIDNameProjection getUserByEmailOrPhone(FindUserRequest findUserRequest) throws BillSyncServerException,
    BillSyncClientException {
    try {

      String value  = findUserRequest.getSearchValue();

      if(value == null || value.isEmpty()) {
        throw new NullPointerException();
      }
      return userRepository.findByEmailOrPhone(findUserRequest.getSearchValue()).orElseThrow(() -> new RecordNotFoundException("User not found: " + value, HttpStatusCodeEnum.NOT_FOUND));
    } catch (NullPointerException e) {
      throw new BillSyncClientException("Please provide correct value!", e,
        HttpStatusCodeEnum.BAD_REQUEST);
    }
    catch (RecordNotFoundException e) {
      throw new BillSyncServerException(e.getMessage(), e.getHttpStatusCode());
    }
    catch (Exception e) {
      throw new BillSyncServerException(
        e.getMessage(),
        e,
        HttpStatusCodeEnum.INTERNAL_SERVER_ERROR
      );
    }
  }
}

