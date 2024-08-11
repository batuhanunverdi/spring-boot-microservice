package org.example.customer;

import lombok.RequiredArgsConstructor;
import org.example.amqp.RabbitMQMessageProducer;
import org.example.clients.FraudCheckResponse;
import org.example.clients.FraudClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;
    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Customer is a fraud");
        }
      rabbitMQMessageProducer.publish(customer.getId(),"internal.exchange","internal.notification.routing-key");
    }
}
