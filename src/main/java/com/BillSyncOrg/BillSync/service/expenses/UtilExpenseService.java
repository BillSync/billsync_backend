package com.BillSyncOrg.BillSync.service.expenses;

import com.BillSyncOrg.BillSync.dto.expenseRecord.ExpenseItem;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.expenseExceptions.ItemizedExpenseValidationException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.expenseExceptions.UserForExpenseRecordNotPresentInGroup;
import com.BillSyncOrg.BillSync.model.Expense;
import com.BillSyncOrg.BillSync.model.Group;
import com.BillSyncOrg.BillSync.util.enums.HttpStatusCodeEnum;
import com.BillSyncOrg.BillSync.util.enums.SplitMethodEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UtilExpenseService {

  public boolean validateItemizedExpense(List<ExpenseItem> items, Group group) throws ItemizedExpenseValidationException, UserForExpenseRecordNotPresentInGroup {
    if (items == null || items.isEmpty()) {
      throw new ItemizedExpenseValidationException("Itemized split must have items with sharedAmong",
        HttpStatusCodeEnum.BAD_REQUEST);
    }
    // Validate each item's sharedAmong is in group
    for (ExpenseItem item : items) {
      if (item.getSharedAmong() == null || item.getSharedAmong().isEmpty()) {
        throw new ItemizedExpenseValidationException("Each item must have at least one user sharing it",
          HttpStatusCodeEnum.BAD_REQUEST);
      }
      for (String userId : item.getSharedAmong()) {
        if (!group.getUserId().contains(userId)) {
          throw new UserForExpenseRecordNotPresentInGroup("User " + userId + " in item " + item.getName() + " is not in the group", HttpStatusCodeEnum.BAD_REQUEST);
        }
      }
    }
    return true;
  }

  public boolean validateEqualExpense(List<String> splitAmong, Group group) throws UserForExpenseRecordNotPresentInGroup {
    // Validate all users exist in group
    for (String userId : splitAmong) {
      if (!group.getUserId().contains(userId)) {
        throw new UserForExpenseRecordNotPresentInGroup("User " + userId + " in splitAmong is not in the " +
          "group", HttpStatusCodeEnum.BAD_REQUEST);
      }
    }
    return true;
  }

  /**
   * Updates the debts map of a group based on the newly added expense.
   *
   * @param group   the group to update
   * @param expense the newly added expense
   */
  public void updateGroupDebts(Group group, Expense expense) {
    Map<String, Map<String, Double>> debts = group.getDebts();

    if (expense.getSplitMethod().equals(SplitMethodEnum.EQUAL.getValue())) {
      double share = expense.getTotalAmount() / expense.getSplitAmong().size();
      for (String userId : expense.getSplitAmong()) {
        if (!userId.equals(expense.getPaidBy())) {
          debts.computeIfAbsent(userId, k -> new java.util.HashMap<>())
            .merge(expense.getPaidBy(), share, Double::sum);
        }
      }
    } else { // ITEMIZED
      for (ExpenseItem item : expense.getItems()) {
        double share = item.getPrice() / item.getSharedAmong().size();
        for (String userId : item.getSharedAmong()) {
          if (!userId.equals(expense.getPaidBy())) {
            debts.computeIfAbsent(userId, k -> new java.util.HashMap<>())
              .merge(expense.getPaidBy(), share, Double::sum);
          }
        }
      }
    }
  }

}
