package net.aymendev.accountservice;

import net.aymendev.accountservice.clients.CustomerRestClient;
import net.aymendev.accountservice.entities.BankAccount;
import net.aymendev.accountservice.enums.AccountType;
import net.aymendev.accountservice.repository.BankAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableFeignClients
public class AccountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(BankAccountRepository accountRepository, CustomerRestClient customerRestClient){
        return args -> {
            customerRestClient.allCustomers().forEach(customer -> {
                List<BankAccount> accountList = List.of(
                        BankAccount.builder()
                                .accountId(UUID.randomUUID().toString())
                                .currency("MAD")
                                .balance(Math.random()*80000)
                                .createdAt(LocalDate.now())
                                .type(AccountType.CURRENT_ACCOUNT)
                                .customerId(Long.valueOf(customer.getId()))
                                .build(),
                        BankAccount.builder()
                                .accountId(UUID.randomUUID().toString())
                                .currency("MAD")
                                .balance(Math.random()*6453)
                                .createdAt(LocalDate.now())
                                .type(AccountType.SAVING_ACCOUNT)
                                .customerId(Long.valueOf(customer.getId()))
                                .build()
                );
            accountRepository.saveAll(accountList);
            });


        };
    }
}
