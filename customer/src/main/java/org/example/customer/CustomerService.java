package org.example.customer;

import lombok.RequiredArgsConstructor;
import org.example.clients.FraudCheckResponse;
import org.example.clients.FraudClient;
import org.example.clients.NotificationClient;
import org.example.clients.NotificationResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;
    private final FraudClient fraudClient;
    private final NotificationClient notificationClient;
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
        NotificationResponse notificationResponse = notificationClient.sendRegisterNotification(customer.getId());
        if (!notificationResponse.isSent()) {
            throw new IllegalStateException("Notification not sent");
        }
    }
}
