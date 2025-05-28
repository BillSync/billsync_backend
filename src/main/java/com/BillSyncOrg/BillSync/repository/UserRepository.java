package com.BillSyncOrg.BillSync.repository;


import com.BillSyncOrg.BillSync.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

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
}
