package com.BillSyncOrg.BillSync.controller.groups;

import com.BillSyncOrg.BillSync.dto.groupRecords.CreateGroupRequest;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.serverExceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.model.Group;
import com.BillSyncOrg.BillSync.service.group.CreateGroupService;
import com.BillSyncOrg.BillSync.util.HttpStatusCodeEnum;
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
public class CreateGroupController {

  private final CreateGroupService createGroupService;

  @Autowired
  public CreateGroupController(CreateGroupService createGroupService) {
    this.createGroupService = createGroupService;
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
}
