package dk.polotsk.backend.Catalog.Controller;

import dk.polotsk.backend.Catalog.model.Message;
import dk.polotsk.backend.Catalog.repository.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageRepository messageRepository;

    public MessageController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping
    public ResponseEntity<List<Message>> hello() {
        return ResponseEntity.ok(messageRepository.findAll());
    }
}
