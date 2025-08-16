package com.BillSyncOrg.BillSync.repository;

import com.BillSyncOrg.BillSync.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for performing CRUD operations on {@link Group} documents
 * in MongoDB.
 *
 * <p>This interface extends {@link MongoRepository} to provide basic operations
 * such as save, findAll, findById, and delete.</p>
 *
 * <p>Custom query methods can be added if needed for group-specific searches.</p>
 */
public interface GroupRepository extends MongoRepository<Group, String> {

  /**
   * Checks if a group with the given name exists, excluding the group with provided ID.
   *
   * @param groupName group name to check
   * @param excludeId group ID to exclude from the check
   * @return true if a different group with the same name exists
   */
  boolean existsByGroupNameAndIdNot(String groupName, String excludeId);

}
