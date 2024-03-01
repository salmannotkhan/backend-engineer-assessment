package com.midas.app.workflows;

import com.midas.app.activities.AccountActivity;
import com.midas.app.models.Account;
import com.midas.generated.model.UpdateAccountDto;
import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;

@WorkflowImpl(taskQueues = UpdateAccountWorkflow.QUEUE_NAME)
public class UpdateAccountWorkflowImpl implements UpdateAccountWorkflow {

  private AccountActivity activity =
      Workflow.newActivityStub(
          AccountActivity.class,
          ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(2)).build());

  @Override
  public Account updateAccount(Account account, UpdateAccountDto details) {
    if (details.getEmail() != null) {
      account.setEmail(details.getEmail());
    }
    if (details.getFirstName() != null) {
      account.setFirstName(details.getFirstName());
    }
    if (details.getLastName() != null) {
      account.setLastName(details.getLastName());
    }
    return activity.saveAccount(account);
  }
}
