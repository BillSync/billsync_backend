package com.BillSyncOrg.BillSync.model;

import com.BillSyncOrg.BillSync.dto.expenseRecord.ExpenseItem;
import com.BillSyncOrg.BillSync.util.enums.SplitMethodEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Represents an expense within a group.
 * <p>
 * Can be split equally or itemized among group members.
 * </p>
 */
@Document(collection = "expenses")
public class Expense {

  @Id
  private String id;

  private String groupId;

  private String description;

  private double totalAmount;

  private String paidBy;

  private String splitMethod;

  private List<String> splitAmong;

  private List<ExpenseItem> items;

  private Date createAt;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(String groupId) {
    this.groupId = groupId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public String getPaidBy() {
    return paidBy;
  }

  public void setPaidBy(String paidBy) {
    this.paidBy = paidBy;
  }

  public String getSplitMethod() {
    return splitMethod;
  }

  public void setSplitMethod(String splitMethod) {
    this.splitMethod = splitMethod;
  }

  public List<String> getSplitAmong() {
    return splitAmong;
  }

  public void setSplitAmong(List<String> splitAmong) {
    this.splitAmong = splitAmong;
  }

  public List<ExpenseItem> getItems() {
    return items;
  }

  public void setItems(List<ExpenseItem> items) {
    this.items = items;
  }

  public Date getCreateAt() {
    return createAt;
  }

  public void setCreateAt(Date createAt) {
    this.createAt = createAt;
  }
}
