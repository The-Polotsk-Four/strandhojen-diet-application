package dk.polotsk.backend;

//import dk.polotsk.backend.Catalog.model.Message;
import dk.polotsk.backend.Catalog.Service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class BackendApplication {
    @Autowired
    private EmailSenderService SenderService;

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

    //Email Sender
    @EventListener(ApplicationReadyEvent.class)
    public void sendEmail() {
        SenderService.sendEmail("Test@gmail.com", "Test Email", "This is a test email");
    }

}
