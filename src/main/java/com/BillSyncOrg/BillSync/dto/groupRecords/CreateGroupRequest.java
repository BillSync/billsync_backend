package com.BillSyncOrg.BillSync.dto.groupRecords;

import com.BillSyncOrg.BillSync.controller.groups.GroupController;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * Data Transfer Object (DTO) used to capture the input data for creating a new group.
 * <p>
 * The DTO is used in the {@link GroupController#createGroup(CreateGroupRequest)}
 * endpoint.
 * </p>
 *
 * <p><b>Fields:</b></p>
 * <ul>
 *   <li>{@code groupName} - Required. Name of the group. Must be unique.</li>
 *   <li>{@code userIds} - Optional. List of user IDs to add to the group at creation.</li>
 * </ul>
 *
 * <p><b>Validation:</b></p>
 * <ul>
 *   <li>{@code @NotBlank} ensures the group name is provided.</li>
 * </ul>
 *
 * <p><b>Example JSON Request:</b></p>
 * <pre>
 * {
 *   "groupName": "Trip to Toronto",
 *   "userIds": ["user1", "user2", "user3"]
 * }
 * </pre>
 */
public class CreateGroupRequest {

  @NotBlank(message = "group name is required!")
  private String groupName;

  private List<String> userIds;

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public List<String> getUserIds() {
    return userIds;
  }

  public void setUserIds(List<String> userIds) {
    this.userIds = userIds;
  }
}
