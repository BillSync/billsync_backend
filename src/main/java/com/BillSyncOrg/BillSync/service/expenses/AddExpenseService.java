package com.BillSyncOrg.BillSync.service.expenses;

import com.BillSyncOrg.BillSync.dto.expenseRecord.AddExpenseRequest;
import com.BillSyncOrg.BillSync.dto.expenseRecord.ExpenseItem;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.RecordNotFoundException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.expenseExceptions.ItemizedExpenseValidationException;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.expenseExceptions.UserForExpenseRecordNotPresentInGroup;
import com.BillSyncOrg.BillSync.exceptions.serverExceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.model.Expense;
import com.BillSyncOrg.BillSync.model.Group;
import com.BillSyncOrg.BillSync.repository.ExpenseRepository;
import com.BillSyncOrg.BillSync.repository.GroupRepository;
import com.BillSyncOrg.BillSync.service.group.UtilGroupService;
import com.BillSyncOrg.BillSync.util.enums.HttpStatusCodeEnum;
import com.BillSyncOrg.BillSync.util.enums.SplitMethodEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AddExpenseService {

  private final ExpenseRepository expenseRepository;

  private final UtilGroupService utilGroupService;

  private final GroupRepository groupRepository;

  private final UtilExpenseService utilExpenseService;

  @Autowired
  public AddExpenseService(ExpenseRepository expenseRepository, UtilGroupService utilGroupService
    , GroupRepository groupRepository, UtilExpenseService utilExpenseService) {
    this.expenseRepository = expenseRepository;
    this.utilGroupService = utilGroupService;
    this.groupRepository = groupRepository;
    this.utilExpenseService = utilExpenseService;
  }

  public Expense addExpense(AddExpenseRequest request) throws BillSyncServerException,
    BillSyncClientException{

    try{

      Group group = utilGroupService.checkIfGroupExist(request.getGroupId());

      if(!group.getUserId().contains(request.getPaidBy())) {
        throw new UserForExpenseRecordNotPresentInGroup("Paid by user not present in the group!",
          HttpStatusCodeEnum.BAD_REQUEST);
      }

      Expense expense = new Expense();
      expense.setGroupId(request.getGroupId());
      expense.setDescription(request.getDescription());
      expense.setPaidBy(request.getPaidBy());
      expense.setTotalAmount(request.getTotalAmount());
      expense.setSplitMethod(request.getSplitMethod());
      expense.setCreateAt(new Date());

      // 4. Set embedded items for ITEMIZED split
      if (request.getSplitMethod().equals(SplitMethodEnum.ITEMIZED.getValue())) {
        List<ExpenseItem> items = request.getItems();
        utilExpenseService.validateItemizedExpense(items, group);
        expense.setItems(items);
      }

      // 5. Set splitAmong for EQUAL split
      if (request.getSplitMethod().equals(SplitMethodEnum.EQUAL.getValue())) {
        List<String> splitAmong = request.getSplitAmong() != null ? request.getSplitAmong() : group.getUserId();
        utilExpenseService.validateEqualExpense(splitAmong, group);
        expense.setSplitAmong(splitAmong);
      }

      Expense savedExpense = expenseRepository.save(expense);

      utilExpenseService.updateGroupDebts(group, savedExpense);
      groupRepository.save(group);

      return expense;

    } catch (RecordNotFoundException | UserForExpenseRecordNotPresentInGroup |
             ItemizedExpenseValidationException e) {
      throw new BillSyncClientException(e.getMessage(), e.getHttpStatusCode());
    }
    catch (Exception e) {
      throw new BillSyncServerException("Unable to add expense", e,
        HttpStatusCodeEnum.INTERNAL_SERVER_ERROR);
    }

  }

}
