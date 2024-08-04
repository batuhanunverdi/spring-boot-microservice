package org.example.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.clients.NotificationResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping(path = "{customerId}")
    public NotificationResponse sendRegisterNotification(@PathVariable("customerId") Integer customerId) {
        log.info("Customer registered {}", customerId);
        NotificationResponse notificationResponse = notificationService.sendRegisterNotification(customerId);
        return notificationResponse;
    }
}
