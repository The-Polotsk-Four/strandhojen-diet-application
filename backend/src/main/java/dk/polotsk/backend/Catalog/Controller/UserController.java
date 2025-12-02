package dk.polotsk.backend.Catalog.Controller;


import dk.polotsk.backend.Catalog.Service.UserService;
import dk.polotsk.backend.Catalog.dto.ErrorDto;
import dk.polotsk.backend.Catalog.dto.UserCreateDto;
import dk.polotsk.backend.Catalog.dto.UserDto;
import dk.polotsk.backend.Catalog.model.User;
import dk.polotsk.backend.Catalog.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(
            @RequestParam(value = "login", required = false) String login){
        if (login != null) {
            return ResponseEntity.ok(userService.getByUserLogin(login));
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDto dto){

        if (userRepository.existsByLogin(dto.login())){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorDto("Der findes allerede en bruger med dette Login"));
        }

        return ResponseEntity.ok(userService.createUser(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/current")
    public ResponseEntity<?> getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() ||
                authentication.getPrincipal().equals("anonymousUser")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username= authentication.getName();
        List<UserDto> users = userService.getByUserLogin(username);

        if (users.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(users.get(0));

    }


}
