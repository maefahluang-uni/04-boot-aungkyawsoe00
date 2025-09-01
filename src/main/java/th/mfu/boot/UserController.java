package th.mfu.boot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    // In-memory store for users
    public static Map<String, User> users = new HashMap<>();

    // POST /users - Register a new user
    @PostMapping("/users")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (users.containsKey(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }
        users.put(user.getUsername(), user);
        return ResponseEntity.status(HttpStatus.OK)
                .body("User registered successfully");
    }

    // GET /users - List all users
    @GetMapping("/users")
    public ResponseEntity<Collection<User>> list() {
        return ResponseEntity.ok(users.values());
    }

    // GET /users/{username} - Get a specific user by username
    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = users.get(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }
}
