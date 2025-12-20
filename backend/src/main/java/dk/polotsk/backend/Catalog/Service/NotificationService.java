package dk.polotsk.backend.Catalog.Service;
import dk.polotsk.backend.Catalog.model.Notification;
import dk.polotsk.backend.Catalog.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public void create(String message) {
        repository.save(new Notification(message));
    }

    public List<Notification> getUnread() {
        return repository.findByReadFalseOrderByCreatedAtDesc();
    }

    public void markAsRead(Long id) {
        Notification n = repository.findById(id)
                .orElseThrow();
        n.setRead(true);
        repository.save(n);
    }
}

