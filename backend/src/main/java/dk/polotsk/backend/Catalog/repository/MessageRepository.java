package dk.polotsk.backend.Catalog.repository;

import dk.polotsk.backend.Catalog.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}