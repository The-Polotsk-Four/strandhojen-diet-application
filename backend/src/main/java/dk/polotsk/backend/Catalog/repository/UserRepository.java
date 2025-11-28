package dk.polotsk.backend.Catalog.repository;

import dk.polotsk.backend.Catalog.dto.UserDto;
import dk.polotsk.backend.Catalog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findUserByLogin(String login);
}
