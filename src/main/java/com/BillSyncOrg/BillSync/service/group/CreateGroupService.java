package com.BillSyncOrg.BillSync.service.group;

import com.BillSyncOrg.BillSync.dto.groupRecords.CreateGroupRequest;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.serverExceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.RecordNotFoundException;
import com.BillSyncOrg.BillSync.model.Group;
import com.BillSyncOrg.BillSync.repository.GroupRepository;
import com.BillSyncOrg.BillSync.service.userAuthentication.UserService;
import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service class responsible for handling group creation logic.
 * <p>
 * This service validates and creates groups, initializes empty debts,
 * and ensures group names are unique.
 * </p>
 *
 * <p>Exceptions:</p>
 * <ul>
 *   <li>{@link BillSyncClientException} - Thrown if the group name already exists.</li>
 *   <li>{@link BillSyncServerException} - Thrown if any unexpected runtime error occurs.</li>
 * </ul>
 */
@Service
public class CreateGroupService {

  private final GroupRepository groupRepository;
  private final UserService userService;

  @Autowired
  public CreateGroupService(GroupRepository groupRepository, UserService userService) {
    this.groupRepository = groupRepository;
    this.userService = userService;
  }

  /**
   * Creates a new group with the provided details.
   *
   * @param createGroupRequest DTO containing group name and optional user IDs.
   * @return {@link Group} object that was persisted in the database.
   * @throws BillSyncServerException if an unexpected server error occurs.
   * @throws BillSyncClientException if the group name already exists.
   */
  public Group createGroup(CreateGroupRequest createGroupRequest) throws BillSyncServerException,
    BillSyncClientException {
    try {
      List<String> userIds = createGroupRequest.getUserIds();

      userService.checkAllUsersExists(userIds);

      Group group = new Group();
      group.setGroupName(createGroupRequest.getGroupName());
      group.setUserId(userIds);
      group.setDebts(Map.of());

      return groupRepository.save(group);
    } catch (RecordNotFoundException e) {
      throw new BillSyncClientException(e.getMessage(), e.getHttpStatusCode());
    }
    catch (DuplicateKeyException e) {
      throw new BillSyncClientException(
        "Group name already exists, please provide unique name!",
        e,
        HttpStatusCodeEnum.BAD_REQUEST
      );
    } catch (RuntimeException e) {
      throw new BillSyncServerException(
        "Error occurred creating a group!",
        e,
        HttpStatusCodeEnum.INTERNAL_SERVER_ERROR
      );
    }
  }
}
