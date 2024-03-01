package com.midas.app.controllers;

import com.midas.app.mappers.Mapper;
import com.midas.app.models.Account;
import com.midas.app.services.AccountService;
import com.midas.generated.api.AccountsApi;
import com.midas.generated.model.AccountDto;
import com.midas.generated.model.CreateAccountDto;
import com.midas.generated.model.UpdateAccountDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class AccountController implements AccountsApi {
  private final AccountService accountService;
  private final Logger logger = LoggerFactory.getLogger(AccountController.class);

  /**
   * POST /accounts : Create a new user account Creates a new user account with the given details
   * and attaches a supported payment provider such as &#39;stripe&#39;.
   *
   * @param createAccountDto User account details (required)
   * @return User account created (status code 201)
   */
  @Override
  public ResponseEntity<AccountDto> createUserAccount(CreateAccountDto createAccountDto) {
    logger.info("Creating account for user with email: {}", createAccountDto.getEmail());

    var account =
        accountService.createAccount(
            Account.builder()
                .firstName(createAccountDto.getFirstName())
                .lastName(createAccountDto.getLastName())
                .email(createAccountDto.getEmail())
                .build());

    return new ResponseEntity<>(Mapper.toAccountDto(account), HttpStatus.CREATED);
  }

  /**
   * PATCH /accounts/{accountId} : Update user account Updates a user account with the given
   * details.
   *
   * @param accountId accountId of user account (required)
   * @param updateAccountDto User account details (required)
   * @return User account updated (status code 201) or Bad request (status code 400) or Unauthorized
   *     (status code 401) or Forbidden (status code 403) or Internal server error (status code 500)
   */
  @Override
  public ResponseEntity<AccountDto> updateUserAccount(UUID accountId, UpdateAccountDto details) {
    var account = accountService.getAccount(accountId);
    if (account.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    var updatedAccount = accountService.updateAccount(account.get(), details);
    return new ResponseEntity<>(Mapper.toAccountDto(updatedAccount), HttpStatus.OK);
  }

  /**
   * GET /accounts : Get list of user accounts Returns a list of user accounts.
   *
   * @return List of user accounts (status code 200)
   */
  @Override
  public ResponseEntity<List<AccountDto>> getUserAccounts() {
    logger.info("Retrieving all accounts");

    var accounts = accountService.getAccounts();
    var accountsDto = accounts.stream().map(Mapper::toAccountDto).toList();

    return new ResponseEntity<>(accountsDto, HttpStatus.OK);
  }
}
