package com.BillSyncOrg.BillSync.service.group;

import com.BillSyncOrg.BillSync.dto.groupRecords.UpdateGroupRequest;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.groupExceptions.GroupNameNotUniqueException;
import com.BillSyncOrg.BillSync.exceptions.serverExceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.RecordNotFoundException;
import com.BillSyncOrg.BillSync.model.Group;
import com.BillSyncOrg.BillSync.repository.GroupRepository;
import com.BillSyncOrg.BillSync.service.userAuthentication.UserService;
import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service responsible for updating existing {@link Group} entities.
 * <p>
 * This service handles updates to a group including:
 * <ul>
 *   <li>Changing the group's name while ensuring uniqueness</li>
 *   <li>Adding new users to the group after verifying their existence</li>
 * </ul>
 *
 * It uses {@link UtilGroupService} for group existence and uniqueness checks,
 * {@link UserService} for user existence validation, and persists changes
 * using {@link GroupRepository}.
 */
@Service
public class UpdateGroupService {

  private final GroupRepository groupRepository;
  private final UtilGroupService utilGroupService;
  private final UserService userService;

  @Autowired
  public UpdateGroupService(GroupRepository groupRepository, UtilGroupService utilGroupService,
                            UserService userService) {
    this.groupRepository = groupRepository;
    this.utilGroupService = utilGroupService;
    this.userService = userService;
  }

  /**
   * Updates an existing group with new details provided in the request.
   * <p>
   * Supported updates include:
   * <ul>
   *   <li>Updating the group name (ensures the new name is unique across groups)</li>
   *   <li>Adding new users to the group (only if all users exist in the database)</li>
   * </ul>
   *
   * @param request the {@link UpdateGroupRequest} containing the group ID,
   *                new group name (optional), and additional user IDs (optional).
   * @return the updated {@link Group} after persistence.
   * @throws BillSyncClientException if:
   *         <ul>
   *           <li>The specified group does not exist</li>
   *           <li>The new group name is already in use</li>
   *           <li>One or more user IDs do not exist</li>
   *         </ul>
   * @throws BillSyncServerException if an unexpected runtime error occurs
   *         during the update operation (e.g., database issues).
   */
  public Group updateGroup(UpdateGroupRequest request)
    throws BillSyncClientException, BillSyncServerException {
    try {

      String groupId = request.getGroupId();

      // Validate group existence
      Group group = utilGroupService.checkIfGroupExist(groupId);

      // Update group name if provided and unique
      if (!utilGroupService.checkIfGroupNameUnique(request)) {
        group.setGroupName(request.getNewGroupName());
      }

      // Add new users if they exist in the system
      if (userService.checkAllUsersExists(request.getAddUserIds())) {
        List<String> updatedUsers = group.getUserId();
        updatedUsers.addAll(request.getAddUserIds());
        group.setUserId(updatedUsers.stream().distinct().collect(Collectors.toList()));
      }

      return groupRepository.save(group);

    } catch (RecordNotFoundException | GroupNameNotUniqueException e) {
      throw new BillSyncClientException(e.getMessage(), e.getHttpStatusCode());
    } catch (RuntimeException e) {
      throw new BillSyncServerException(
        "Error updating group",
        e,
        HttpStatusCodeEnum.INTERNAL_SERVER_ERROR
      );
    }
  }
}

