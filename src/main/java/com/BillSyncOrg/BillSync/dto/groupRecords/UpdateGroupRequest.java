package com.BillSyncOrg.BillSync.dto.groupRecords;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * Data Transfer Object (DTO) for updating an existing group.
 * <p>
 * This request object is used when modifying a group's details, such as:
 * <ul>
 *   <li>Changing the group's name</li>
 *   <li>Adding new users to the group</li>
 * </ul>
 * </p>
 *
 * <p><b>Validation:</b></p>
 * <ul>
 *   <li>{@code groupId} is required and cannot be blank.</li>
 *   <li>{@code newGroupName} is optional but, if provided, must be unique (validated in service layer).</li>
 *   <li>{@code addUserIds} is optional and represents the list of user IDs to be added to the group.</li>
 * </ul>
 *
 * <p><b>Usage Example:</b></p>
 * <pre>{@code
 *   UpdateGroupRequest request = new UpdateGroupRequest();
 *   request.setGroupId("12345");
 *   request.setNewGroupName("Project Team Alpha");
 *   request.setAddUserIds(Arrays.asList("u001", "u002", "u003"));
 * }</pre>
 *
 * @author YourName
 */
public class UpdateGroupRequest {

  /**
   * The unique identifier of the group being updated.
   * <p>
   * This field is mandatory and must not be blank.
   * </p>
   */
  @NotBlank(message = "groupId is required")
  private String groupId;

  /**
   * The new name to assign to the group.
   * <p>
   * This field is optional. If provided, it must be unique across all groups.
   * </p>
   */
  private String newGroupName;

  /**
   * A list of user IDs to be added to the group.
   * <p>
   * This field is optional. If provided, the service will attempt to add each user ID
   * to the group’s membership.
   * </p>
   */
  private List<String> addUserIds;

  /** @return the group ID of the group being updated */
  public String getGroupId() {
    return groupId;
  }

  /** @param groupId the unique identifier of the group */
  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  /** @return the new group name (if provided) */
  public String getNewGroupName() {
    return newGroupName;
  }

  /** @param newGroupName the new name for the group */
  public void setNewGroupName(String newGroupName) {
    this.newGroupName = newGroupName;
  }

  /** @return the list of user IDs to be added to the group */
  public List<String> getAddUserIds() {
    return addUserIds;
  }

  /** @param addUserIds the user IDs to add to the group */
  public void setAddUserIds(List<String> addUserIds) {
    this.addUserIds = addUserIds;
  }
}

