package com.midas.app.workflows;

import com.midas.app.models.Account;
import com.midas.generated.model.UpdateAccountDto;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface UpdateAccountWorkflow {
  String QUEUE_NAME = "update-account-workflow";

  /**
   * updateAccount updates an account in the system.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  @WorkflowMethod
  Account updateAccount(Account account, UpdateAccountDto details);
}
