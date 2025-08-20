package com.BillSyncOrg.BillSync.dto.expenseRecord;

import com.BillSyncOrg.BillSync.util.enums.SplitMethodEnum;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * DTO for creating a new expense in a group.
 * <p>
 * If splitMethod is EQUAL, `splitAmong` must be provided.
 * If splitMethod is ITEMIZED, `items` must be provided and each item must have userIds.
 * </p>
 */
public class AddExpenseRequest {

  @NotBlank(message = "GroupId is required")
  private String groupId;

  @NotBlank(message = "Description is required")
  private String description;

  @NotBlank(message = "Price is required")
  private double totalAmount;

  @NotBlank(message = "PaidBy is required")
  private String paidBy;

  @NotBlank(message = "SplitMethod is required")
  private String splitMethod;

  private List<String> splitAmong;

  private List<ExpenseItem> items;

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

  public void String(SplitMethodEnum splitMethod) {
    this.splitMethod = splitMethod.getValue();
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
}
