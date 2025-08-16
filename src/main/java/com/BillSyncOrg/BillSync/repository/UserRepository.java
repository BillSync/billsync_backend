package com.BillSyncOrg.BillSync.repository;


import com.BillSyncOrg.BillSync.model.User;
import com.BillSyncOrg.BillSync.projection.allUsers.UserIDNameProjection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for accessing User data from MongoDB.
 * <p>
 * Extends {@link MongoRepository} to provide CRUD operations out of the box.
 * Defines custom finder methods for querying users by email and phone number.
 * </p>
 *
 * @see User
 */
public interface UserRepository extends MongoRepository<User, String> {

  /**
   * Finds a user by their email address.
   *
   * @param email the email to search for
   * @return an {@link Optional} containing the user if found, or empty if not found
   */
  Optional<User> findByEmail(String email);

  /**
   * Finds a user by their phone number.
   *
   * @param phoneNumber the phone number to search for
   * @return an {@link Optional} containing the user if found, or empty if not found
   */
  Optional<User> findByPhoneNumber(String phoneNumber);

  /**
   * Retrieves all users from the MongoDB {@code users} collection, selecting only
   * the {@code id} and {@code name} fields for each document.
   * <p>
   * This method uses a MongoDB projection to limit the returned fields, thereby
   * improving performance by reducing the size of the query results. The results
   * are mapped to the {@link UserIDNameProjection} interface, providing type-safe
   * access to the selected fields without exposing unnecessary user data.
   * </p>
   *
   * <h3>MongoDB Query Details:</h3>
   * <ul>
   *     <li><b>value = "{}"</b> — Matches all documents in the collection.</li>
   *     <li><b>fields = "{ 'id': 1, 'name': 1 }"</b> — Includes only the {@code id}
   *     and {@code name} fields in the query result. All other fields are excluded.</li>
   * </ul>
   *
   *
   * @return a list of {@link UserIDNameProjection} objects containing only
   *         {@code id} and {@code name} for each user
   */
  @Query(value = "{}", fields = "{ '_id': 1, 'name': 1 }")
  List<UserIDNameProjection> findAllIDAndName();

  /**
   * Finds a user by matching either email or phone number and returns only id and name.
   *
   * @param value the email or phone number to search for
   * @return an Optional containing UserIDNameProjection if found
   */
  @Query(value = "{ $or: [ { 'email': ?0 }, { 'phoneNumber': ?0 } ] }",
    fields = "{ '_id': 1, 'name': 1 }")
  Optional<UserIDNameProjection> findByEmailOrPhone(String value);

  /**
   * Finds all users whose IDs are in the given list.
   *
   * @param ids list of user IDs
   * @return list of matching users
   */
  List<User> findByIdIn(List<String> ids);
}
