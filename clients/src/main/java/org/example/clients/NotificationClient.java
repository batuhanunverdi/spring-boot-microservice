package org.example.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("notification")
public interface NotificationClient {
    @PostMapping("api/v1/notifications/{customerId}")
    NotificationResponse sendRegisterNotification(@PathVariable("customerId") Integer customerId);
}
