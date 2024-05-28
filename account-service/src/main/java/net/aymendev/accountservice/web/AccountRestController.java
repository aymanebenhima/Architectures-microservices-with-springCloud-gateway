package net.aymendev.accountservice.web;

import net.aymendev.accountservice.clients.CustomerRestClient;
import net.aymendev.accountservice.entities.BankAccount;
import net.aymendev.accountservice.model.Customer;
import net.aymendev.accountservice.repository.BankAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AccountRestController {
    private static final Logger logger = LoggerFactory.getLogger(AccountRestController.class);
    private final BankAccountRepository accountRepository;
    private final CustomerRestClient customerRestClient;

    public AccountRestController(BankAccountRepository accountRepository, CustomerRestClient customerRestClient) {
        this.accountRepository = accountRepository;
        this.customerRestClient = customerRestClient;
    }

    @GetMapping("/accounts")
    public List<BankAccount> accountList(){
        List<BankAccount> accountList = accountRepository.findAll();
        accountList.forEach(account-> {
            try {
            account.setCustomer(customerRestClient.findCustomerById(account.getCustomerId()));
            } catch (Exception e) {
                logger.error("Error fetching customer for account ID: {}", account.getAccountId(), e);
            }
        });
        return accountList;
    }

    @GetMapping("/accounts/{id}")
    public BankAccount bankAccountById(@PathVariable String id){
        Optional<BankAccount> optionalBankAccount = accountRepository.findById(id);
        if (optionalBankAccount.isPresent()) {
            BankAccount bankAccount = optionalBankAccount.get();
            try {
                Customer customer = customerRestClient.findCustomerById(bankAccount.getCustomerId());
                bankAccount.setCustomer(customer);
            } catch (Exception e) {
                logger.error("Error fetching customer for account ID: {}", bankAccount.getAccountId(), e);
            }
            return bankAccount;
        } else {
            logger.warn("Bank account not found for ID: {}", id);
            throw new RuntimeException("Bank account not found");
        }
    }
}
