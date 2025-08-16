package com.BillSyncOrg.BillSync.service.group;

import com.BillSyncOrg.BillSync.dto.groupRecords.UpdateGroupRequest;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.RecordNotFoundException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.groupExceptions.GroupNameNotUniqueException;
import com.BillSyncOrg.BillSync.model.Group;
import com.BillSyncOrg.BillSync.repository.GroupRepository;
import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Utility service for performing common group-related operations.
 * <p>
 * This service acts as a helper layer for validating groups during creation and updates,
 * such as verifying group existence and ensuring group name uniqueness.
 * </p>
 *
 * <p><b>Responsibilities:</b></p>
 * <ul>
 *   <li>Check if a group exists by its ID.</li>
 *   <li>Validate uniqueness of a new group name when updating an existing group.</li>
 * </ul>
 *
 * <p><b>Exceptions:</b></p>
 * <ul>
 *   <li>{@link RecordNotFoundException} – Thrown when a requested group does not exist.</li>
 *   <li>{@link GroupNameNotUniqueException} – Thrown when a new group name already exists in the system.</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 *   Group group = utilGroupService.checkIfGroupExist(groupId);
 *   boolean canUseName = utilGroupService.checkIfGroupNameUnique(updateGroupRequest);
 * }</pre>
 *
 * @author YourName
 */
@Service
public class UtilGroupService {

  private final GroupRepository groupRepository;

  /**
   * Constructs a new {@code UtilGroupService} with the required repository dependency.
   *
   * @param groupRepository the repository used to perform group-related database operations
   */
  @Autowired
  public UtilGroupService(GroupRepository groupRepository) {
    this.groupRepository = groupRepository;
  }

  /**
   * Verifies that a group exists in the database using its unique identifier.
   *
   * @param groupId the unique identifier of the group
   * @return the {@link Group} entity if found
   * @throws RecordNotFoundException if no group exists with the provided ID
   */
  public Group checkIfGroupExist(String groupId) throws RecordNotFoundException {
    return groupRepository.findById(groupId)
      .orElseThrow(() -> new RecordNotFoundException(
        "Group not found", HttpStatusCodeEnum.BAD_REQUEST));
  }

  /**
   * Ensures that the new group name provided in the update request is unique across all groups,
   * except the group currently being updated.
   *
   * <p>If the new group name is {@code null} or blank, this method simply returns {@code true}
   * without performing any uniqueness checks.</p>
   *
   * @param updateGroupRequest the request object containing the group ID and the new group name
   * @return {@code true} if the name is either not provided or valid/unique, {@code false} if a valid unique name is found
   * @throws GroupNameNotUniqueException if the new group name already exists for a different group
   */
  public boolean checkIfGroupNameUnique(UpdateGroupRequest updateGroupRequest)
    throws GroupNameNotUniqueException {

    if (updateGroupRequest.getNewGroupName() != null && !updateGroupRequest.getNewGroupName().isBlank()) {
      boolean exists = groupRepository.existsByGroupNameAndIdNot(
        updateGroupRequest.getNewGroupName(), updateGroupRequest.getGroupId());

      if (exists) {
        throw new GroupNameNotUniqueException(
          "New group name already exists, please choose a unique name",
          HttpStatusCodeEnum.BAD_REQUEST);
      }
      return false;
    }
    return true;
  }
}

