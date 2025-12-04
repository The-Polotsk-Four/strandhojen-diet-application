
package dk.polotsk.backend.Catalog.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public LoginController(AuthenticationManager authenticationManager,
                           SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Create SecurityContext and save it explicitly
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            // Save the security context to the session
            securityContextRepository.saveContext(context, request, response);

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "username", username,
                    "roles", authentication.getAuthorities()
            ));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body(Map.of(
                    "message", "Invalid credentials"
            ));
        }
    }
}
