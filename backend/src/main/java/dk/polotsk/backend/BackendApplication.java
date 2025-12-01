package dk.polotsk.backend;

import dk.polotsk.backend.Catalog.model.Message;
import dk.polotsk.backend.Catalog.repository.MessageRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(BackendApplication.class, args);

        // GET DB URL
        String dbUrl;
        var datasource = context.getBean(DataSource.class);
        try {
            dbUrl = datasource.getConnection().getMetaData().getURL();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Print profile
        String[] profiles = context.getEnvironment().getActiveProfiles();
        String activeProfiles = profiles.length == 0 ? "default" : String.join(", ", profiles);

        System.out.println("---------------");
        System.out.println("DB URL: " + dbUrl);
        System.out.println("Active Profiles: " + activeProfiles);
        System.out.println("---------------");

    }

    // COMMANDLINE RUNNER
    @Bean
    CommandLineRunner commandLineRunner(MessageRepository messageRepository) {
        return args -> {
            System.out.println("---------------");
            System.out.println("Initializing Database using CommandLineRunner");

            if (messageRepository.count() > 0) {
                return;
            }
            Message msg1 = new Message("Hello, Docker!");
            Message msg2 = new Message("Hello, Spring Boot!");
            Message msg3 = new Message("Hello, MySQL!");
            messageRepository.saveAll(List.of(msg1, msg2, msg3));
        };
    }

}
