package com.midas.app.services;

import com.midas.app.models.Account;
import com.midas.generated.model.UpdateAccountDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountService {
  /**
   * createAccount creates a new account in the system or provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  Account createAccount(Account details);

  /**
   * updateAccount creates a new account in the system or provider.
   *
   * @param account is the account to be updated.
   * @param details is the details of the account to be updated.
   * @return Account
   */
  Account updateAccount(Account account, UpdateAccountDto details);

  /**
   * getAccounts returns a list of accounts.
   *
   * @return List<Account>
   */
  List<Account> getAccounts();

  /**
   * getAccount returns an account by accountId.
   *
   * @return Optional<Account>
   */
  Optional<Account> getAccount(UUID accountId);
}
