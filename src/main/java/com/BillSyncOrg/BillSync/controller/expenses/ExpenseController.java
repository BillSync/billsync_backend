package com.BillSyncOrg.BillSync.controller.expenses;

import com.BillSyncOrg.BillSync.dto.expenseRecord.AddExpenseRequest;
import com.BillSyncOrg.BillSync.exceptions.clientExceptions.BillSyncClientException;
import com.BillSyncOrg.BillSync.exceptions.serverExceptions.BillSyncServerException;
import com.BillSyncOrg.BillSync.model.Expense;
import com.BillSyncOrg.BillSync.service.expenses.AddExpenseService;
import com.BillSyncOrg.BillSync.util.ResponseGenerator;
import com.BillSyncOrg.BillSync.util.enums.HttpStatusCodeEnum;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

  private final AddExpenseService addExpenseService;

  @Autowired
  public ExpenseController(AddExpenseService addExpenseService) {
    this.addExpenseService = addExpenseService;
  }

  /**
   * Add a new expense to a group.
   *
   * @param request the expense creation request
   * @return ResponseEntity with the saved Expense and success message
   * @throws BillSyncClientException if validation fails
   * @throws BillSyncServerException for unexpected errors
   */
  @PostMapping("/add-expense")
  public ResponseEntity<Object> addExpense(@RequestBody AddExpenseRequest request)
    throws BillSyncClientException, BillSyncServerException {
    Expense expense = addExpenseService.addExpense(request);
    return ResponseGenerator.builder()
      .body(expense)
      .status(HttpStatusCodeEnum.OK)
      .message("Expense added successfully!")
      .build();
  }

}
