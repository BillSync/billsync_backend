package com.BillSyncOrg.BillSync.controller.groups;

import com.BillSyncOrg.BillSync.dto.groupRecords.CreateGroupRequest;
import com.BillSyncOrg.BillSync.dto.groupRecords.UpdateGroupRequest;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.serverExceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.model.Group;
import com.BillSyncOrg.BillSync.service.group.CreateGroupService;
import com.BillSyncOrg.BillSync.service.group.UpdateGroupService;
import com.BillSyncOrg.BillSync.util.enums.HttpStatusCodeEnum;
import com.BillSyncOrg.BillSync.util.ResponseGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for handling group-related endpoints.
 *
 * <p>Primary responsibility is to expose an API for creating new groups
 * and returning the created group object in a standardized response format.</p>
 *
 * <p>Endpoints:</p>
 * <ul>
 *   <li>POST /api/groups/create-group - Create a new group</li>
 * </ul>
 */
@RestController
@RequestMapping("/api/groups")
public class GroupController {

  private final CreateGroupService createGroupService;
  private final UpdateGroupService updateGroupService;

  @Autowired
  public GroupController(CreateGroupService createGroupService, UpdateGroupService updateGroupService) {
    this.createGroupService = createGroupService;
    this.updateGroupService = updateGroupService;
  }

  /**
   * Creates a new group with the given details.
   *
   * @param createGroupRequest DTO containing group name and optional user IDs.
   * @return {@link ResponseEntity} containing the created {@link Group} object,
   *         HTTP status code, and success message.
   * @throws BillSyncClientException if the group name already exists.
   * @throws BillSyncServerException if an unexpected server error occurs.
   */
  @PostMapping("/create-group")
  public ResponseEntity<Object> createGroup(@RequestBody CreateGroupRequest createGroupRequest)
    throws BillSyncClientException, BillSyncServerException {
    return ResponseGenerator.builder()
      .body(createGroupService.createGroup(createGroupRequest))
      .status(HttpStatusCodeEnum.OK)
      .message("Successful!")
      .build();
  }

  /**
   * Updates the details of an existing group.
   * <p>
   * This endpoint allows clients to modify a group's attributes such as:
   * <ul>
   *   <li>Changing the group's name</li>
   *   <li>Adding new users to the group</li>
   * </ul>
   * </p>
   *
   * <p><b>Request:</b></p>
   * The request body must contain an {@link UpdateGroupRequest} object with:
   * <ul>
   *   <li>{@code groupId} - The ID of the group to be updated (required).</li>
   *   <li>{@code newGroupName} - A new name for the group (optional, must be unique).</li>
   *   <li>{@code addUserIds} - A list of user IDs to be added to the group (optional).</li>
   * </ul>
   *
   * @param updateGroupRequest the request payload containing the group update details
   * @return a {@link ResponseEntity} with the updated group and success message
   * @throws BillSyncClientException if the request data is invalid (e.g., duplicate name, missing groupId)
   * @throws BillSyncServerException if an unexpected error occurs on the server
   */
  @PostMapping("/update-group")
  public ResponseEntity<Object> updateGroup(
    @RequestBody UpdateGroupRequest updateGroupRequest
  ) throws BillSyncClientException, BillSyncServerException {
    return ResponseGenerator.builder()
      .body(updateGroupService.updateGroup(updateGroupRequest))
      .status(HttpStatusCodeEnum.OK)
      .message("Updated successfully!")
      .build();
  }

}
