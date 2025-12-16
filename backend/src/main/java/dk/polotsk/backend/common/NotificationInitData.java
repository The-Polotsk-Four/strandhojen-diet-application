package dk.polotsk.backend.common;

import dk.polotsk.backend.Catalog.model.Notification;
import dk.polotsk.backend.Catalog.repository.NotificationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NotificationInitData implements CommandLineRunner {

    private final NotificationRepository notificationRepository;

    public NotificationInitData(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (notificationRepository.count() == 0) {
            notificationRepository.save(new Notification("Ny beboer oprettet: Hans Hansen"));
            notificationRepository.save(new Notification("Ny beboer oprettet: Lise olesen"));
            notificationRepository.save(new Notification("Ny beboer oprettet: Sofus Vingård"));
            notificationRepository.save(new Notification("Ny beboer oprettet: Daniel Thomsen"));
            System.out.println("Initial notifications added");
        }
    }
}
