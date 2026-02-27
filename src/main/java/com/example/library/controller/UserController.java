package com.example.library.controller;

import com.example.library.model.User;
import com.example.library.service.UserService;
import com.example.library.dto.LoginRequest;
import com.example.library.dto.RegisterRequest;
import com.example.library.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // CORS идэвхжүүлэх
public class UserController {

  @Autowired
  private UserService userService;

  // *** Бүртгүүлэх ***
  @PostMapping("/auth/register")
  public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
    AuthResponse response = userService.register(request);

    if (response.getToken() == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    return ResponseEntity.ok(response);
  }

  // *** Нэвтрэх ***
  @PostMapping("/auth/login")
  public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
    AuthResponse response = userService.login(request);

    if (response.getToken() == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    return ResponseEntity.ok(response);
  }

  // *** Бүх хэрэглэгчийг авах (Admin only) ***
  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  // *** ID-гаар хэрэглэгч авах ***
  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUserById(@PathVariable String id) {
    Optional<User> user = userService.getUserById(id);
    return user.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // *** Хэрэглэгч устгах ***
  @DeleteMapping("/users/{id}")
  public ResponseEntity<String> deleteUser(@PathVariable String id) {
    boolean deleted = userService.deleteUser(id);
    if (deleted) {
      return ResponseEntity.ok("Хэрэглэгч устгагдлаа");
    }
    return ResponseEntity.notFound().build();
  }

  // *** Хэрэглэгч шинэчлэх ***
  @PutMapping("/users/{id}")
  public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
    User updated = userService.updateUser(id, user);
    if (updated != null) {
      return ResponseEntity.ok(updated);
    }
    return ResponseEntity.notFound().build();
  }
}
