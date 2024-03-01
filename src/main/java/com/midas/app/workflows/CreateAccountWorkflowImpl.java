package com.midas.app.workflows;

import com.midas.app.activities.AccountActivity;
import com.midas.app.models.Account;
import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;

@WorkflowImpl(taskQueues = CreateAccountWorkflow.QUEUE_NAME)
public class CreateAccountWorkflowImpl implements CreateAccountWorkflow {

  private AccountActivity activity =
      Workflow.newActivityStub(
          AccountActivity.class,
          ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(2)).build());

  @Override
  public Account createAccount(Account details) {
    System.out.println("inside WorkflowImpl");
    var account = activity.createPaymentAccount(details);
    return activity.saveAccount(account);
  }
}
