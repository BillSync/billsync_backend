package com.BillSyncOrg.BillSync.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * Represents a group of users where members can share expenses.
 * Each group keeps track of debts between users.
 *
 * <p>Fields:</p>
 * <ul>
 *   <li>{@code id} - Unique identifier for the group (MongoDB ObjectId).</li>
 *   <li>{@code groupName} - Name of the group. Must be unique.</li>
 *   <li>{@code userId} - List of user IDs that are members of the group.</li>
 *   <li>{@code debts} - Nested map representing debts.
 *       Format: { "userId1": { "userId2": 50.0, "userId3": 20.0 }, ... }
 *       meaning userId1 owes 50 to userId2 and 20 to userId3.
 *   </li>
 * </ul>
 *
 * <p>MongoDB Index:</p>
 * <ul>
 *   <li>{@code @Indexed(unique = true)} ensures groupName is unique.</li>
 * </ul>
 */
@Document(collection = "groups")
public class Group {

  @Id
  private String id;

  @Indexed(unique = true)
  private String groupName;

  private List<String> userId;

  private Map<String, Map<String, Double>> debts;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public List<String> getUserId() {
    return userId;
  }

  public void setUserId(List<String> userId) {
    this.userId = userId;
  }

  public Map<String, Map<String, Double>> getDebts() {
    return debts;
  }

  public void setDebts(Map<String, Map<String, Double>> debts) {
    this.debts = debts;
  }
}
