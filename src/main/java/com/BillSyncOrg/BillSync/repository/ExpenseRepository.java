package com.BillSyncOrg.BillSync.repository;

import com.BillSyncOrg.BillSync.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Repository for managing Expense documents in MongoDB.
 */
public interface ExpenseRepository extends MongoRepository<Expense, String> {

  /**
   * Find all expenses belonging to a given group.
   *
   * @param groupId the ID of the group
   * @return list of expenses for the group
   */
  List<Expense> findByGroupId(String groupId);
}
