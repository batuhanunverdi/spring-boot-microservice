package org.example.notification;

import lombok.RequiredArgsConstructor;
import org.example.clients.NotificationResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    public NotificationResponse sendRegisterNotification(Integer customerId) {
        Notification notification = Notification.builder()
                .customerId(customerId)
                .message("Onboarding completed successfully")
                .build();
        notificationRepository.save(notification);
        return new NotificationResponse(true);
    }
}
