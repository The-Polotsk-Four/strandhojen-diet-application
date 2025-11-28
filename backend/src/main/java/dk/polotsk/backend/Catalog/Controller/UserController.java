package dk.polotsk.backend.Catalog.Controller;


import dk.polotsk.backend.Catalog.Service.UserService;
import dk.polotsk.backend.Catalog.dto.UserCreateDto;
import dk.polotsk.backend.Catalog.dto.UserDto;
import dk.polotsk.backend.Catalog.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(
            @RequestParam(value = "login", required = false) String login){
        if (login != null) {
            return ResponseEntity.ok(userService.getByUserLogin(login));
        }
        return ResponseEntity.ok(userService.getByUserLogin(login));
    }
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateDto dto){
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
}
