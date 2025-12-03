package dk.polotsk.backend.common;


import dk.polotsk.backend.Catalog.Service.UserService;
import dk.polotsk.backend.Catalog.model.User;
import dk.polotsk.backend.Catalog.model.Userrole;
import dk.polotsk.backend.Catalog.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserInitData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserInitData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User();
        user1.setName("test");
        user1.setLogin("admin");
        user1.setPassword(passwordEncoder.encode("test"));
        user1.setUserrole(Userrole.ADMIN);



        User user2 = new User();
        user2.setName("test");
        user2.setLogin("sygeplejer");
        user2.setPassword(passwordEncoder.encode("test"));
        user2.setUserrole(Userrole.SYGEPLEJERSKE);

        User user3 = new User();
        user3.setName("test");
        user3.setLogin("køkken");
        user3.setPassword(passwordEncoder.encode("test"));
        user3.setUserrole(Userrole.KØKKEN);

        User user4 = new User();
        user4.setName("test");
        user4.setLogin("plejer");
        user4.setPassword(passwordEncoder.encode("test"));
        user4.setUserrole(Userrole.PLEJER);



        userRepository.saveAll(List.of(
                user1, user2, user3, user4
                ));




    }
}
