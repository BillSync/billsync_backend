package com.BillSyncOrg.BillSync.dto.expenseRecord;

import java.util.List;

/**
 * Represents a single item inside an expense.
 * <p>
 * Always embedded inside the Expense document.
 * </p>
 */
public class ExpenseItem {

  private String name;              // e.g., "Milk", "Eggs"
  private double price;             // item price
  private List<String> sharedAmong; // userIds sharing this item

  // Getters and Setters
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public double getPrice() { return price; }
  public void setPrice(double price) { this.price = price; }

  public List<String> getSharedAmong() { return sharedAmong; }
  public void setSharedAmong(List<String> sharedAmong) { this.sharedAmong = sharedAmong; }
}
