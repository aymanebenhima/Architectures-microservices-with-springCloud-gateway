package net.aymendev.customerservice;

import net.aymendev.customerservice.entities.Customer;
import net.aymendev.customerservice.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository customerRepository){
        return args -> {
            List<Customer> customerList = List.of(
                   Customer.builder()
                            .firstName("Aymane")
                            .lastName("Benhima")
                            .email("aymanebenhima@gmail.com")
                            .build(),
                    Customer.builder()
                            .firstName("Ouail")
                            .lastName("Benhima")
                            .email("benhimao@gmail.com")
                            .build()
            );

            customerRepository.saveAll(customerList);
        };
    }

}
