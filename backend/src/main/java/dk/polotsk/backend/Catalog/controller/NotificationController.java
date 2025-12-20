package dk.polotsk.backend.Catalog.controller;

import dk.polotsk.backend.Catalog.Service.NotificationService;
import dk.polotsk.backend.Catalog.model.Notification;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService service;

    public NotificationController(NotificationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Notification> getUnread() {
        return service.getUnread();
    }

    @PutMapping("/{id}/read")
    public void markAsRead(@PathVariable Long id) {
        service.markAsRead(id);
    }
}
