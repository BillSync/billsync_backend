package com.BillSyncOrg.BillSync.projection.allUsers;

import com.BillSyncOrg.BillSync.model.User;

/**
 * Projection interface for retrieving only the {@code id} and {@code name} fields
 * of a {@link User} document from the MongoDB database.
 * <p>
 * This interface is used in conjunction with Spring Data MongoDB's projection
 * feature to limit the fields returned by a query, thereby improving performance
 * by reducing unnecessary data transfer from the database.
 * </p>
 *
 * <p>
 * The primary use case for this projection is when only minimal identifying
 * information about a user is needed (e.g., for dropdown lists, reference lookups,
 * or lightweight API responses) without fetching the full user document and all its
 * associated fields.
 * </p>
 *
 * <h3>Example Repository Usage</h3>
 * <pre>
 * {@code
 * public interface UserRepository extends MongoRepository<User, String> {
 *
 *     @Query(value = "{}", fields = "{ 'id': 1, 'name': 1 }")
 *     List<UserIdNameProjection> findAllIdAndName();
 * }
 * }
 * </pre>
 *
 * <h3>Example Service Usage</h3>
 * <pre>
 * {@code
 * public List<UserIdNameProjection> getAllUserIdAndNames() {
 *     return userRepository.findAllIdAndName();
 * }
 * }
 * </pre>
 *
 * <h3>Benefits</h3>
 * <ul>
 *     <li>Reduces data transfer by selecting only required fields.</li>
 *     <li>Improves query performance for large collections.</li>
 *     <li>Keeps code clean and type-safe by avoiding raw {@code Object[]} results.</li>
 * </ul>
 *
 * @see User
 * @see org.springframework.data.mongodb.repository.Query
 */
public interface UserIDNameProjection {

  /**
   * Returns the unique identifier of the user.
   *
   * @return the user's {@code id} field
   */
  String getId();

  /**
   * Returns the name of the user.
   *
   * @return the user's {@code name} field
   */
  String getName();
}

