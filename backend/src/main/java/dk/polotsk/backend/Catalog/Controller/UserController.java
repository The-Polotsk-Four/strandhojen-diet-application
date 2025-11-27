package dk.polotsk.backend.Catalog.Controller;


import dk.polotsk.backend.Catalog.Service.UserService;
import dk.polotsk.backend.Catalog.dto.UserCreateDto;
import dk.polotsk.backend.Catalog.dto.UserDto;
import dk.polotsk.backend.Catalog.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateDto dto){
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Long id){
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
