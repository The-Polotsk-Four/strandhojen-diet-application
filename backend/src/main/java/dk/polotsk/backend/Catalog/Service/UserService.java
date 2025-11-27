package dk.polotsk.backend.Catalog.Service;

import dk.polotsk.backend.Catalog.dto.UserCreateDto;
import dk.polotsk.backend.Catalog.dto.UserDto;
import dk.polotsk.backend.Catalog.mapper.Mapper;
import dk.polotsk.backend.Catalog.model.User;
import dk.polotsk.backend.Catalog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserCreateDto dto) {
        User user = userRepository.save(Mapper.toEntity(dto));
        return Mapper.toDto(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

}
