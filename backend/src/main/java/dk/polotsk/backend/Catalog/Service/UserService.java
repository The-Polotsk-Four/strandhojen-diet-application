package dk.polotsk.backend.Catalog.Service;

import dk.polotsk.backend.Catalog.dto.UserCreateDto;
import dk.polotsk.backend.Catalog.dto.UserDto;
import dk.polotsk.backend.Catalog.mapper.Mapper;
import dk.polotsk.backend.Catalog.model.User;
import dk.polotsk.backend.Catalog.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> getAllUsers(){
        List<UserDto> userDtos = new ArrayList<>();
        List<User> users = userRepository.findAll();
        for (User user : users){
            userDtos.add(Mapper.toDto(user));
        }
        return userDtos;
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto createUser(UserCreateDto dto) {
        User user = userRepository.save(Mapper.toEntity(dto));
        return Mapper.toDto(user);
    }



    public List<UserDto> getByUserLogin(String login){
        List<User> users = userRepository.findUserByLogin(login);
        if (users.isEmpty()){
            throw new RuntimeException("Can't find user with login "+login);
        }
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users){
            userDtos.add(Mapper.toDto(user));
        }
        return userDtos;
    }


    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with id " + id);
        }
        userRepository.deleteById(id);
    }

}
