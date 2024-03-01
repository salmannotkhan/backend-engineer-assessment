package com.midas.app.providers.external.stripe;

import com.midas.app.models.Account;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.generated.model.AccountDto;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Getter
public class StripePaymentProvider implements PaymentProvider {
  private final Logger logger = LoggerFactory.getLogger(StripePaymentProvider.class);

  private final StripeConfiguration configuration;

  /** providerName is the name of the payment provider */
  @Override
  public String providerName() {
    return "stripe";
  }

  /**
   * createAccount creates a new account in the payment provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  @Override
  public Account createAccount(Account details) {
    try {
      logger.info("Inside Stripe Provider");
      Stripe.apiKey = configuration.getApiKey();
      CustomerCreateParams params =
          CustomerCreateParams.builder()
              .setName(details.getFirstName() + " " + details.getLastName())
              .setEmail(details.getEmail())
              .build();
      Customer customer = Customer.create(params);
      details.setProviderType(AccountDto.ProviderTypeEnum.STRIPE);
      details.setProviderId(customer.getId());
    } catch (StripeException e) {
      logger.error(e.getMessage());
    }
    return details;
  }
}
