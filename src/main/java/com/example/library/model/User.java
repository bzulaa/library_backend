package com.example.library.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "users")
public class User {
  @Id
  private String id;
  private String username; // Хэрэглэгчийн нэр
  private String email; // И-мэйл
  private String password; // Нууц үг (шифрлэгдсэн)
  private UserRole role; // Үүрэг (USER, EMPLOYEE, ADMIN)
  private LocalDateTime createdAt; // Бүртгүүлсэн огноо

  // Хэрэглэгчийн үүргүүд
  public enum UserRole {
    USER, // Энгийн хэрэглэгч (ном авах)
    EMPLOYEE, // Ажилтан (ном удирдах)
    ADMIN // Админ (бүх эрх)
  }

  // Constructors
  public User() {
    this.createdAt = LocalDateTime.now();
  }

  public User(String username, String email, String password, UserRole role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.createdAt = LocalDateTime.now();
  }

  // Getters and Setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public UserRole getRole() {
    return role;
  }

  public void setRole(UserRole role) {
    this.role = role;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public String toString() {
    return "User{" +
        "id='" + id + '\'' +
        ", username='" + username + '\'' +
        ", email='" + email + '\'' +
        ", role=" + role +
        ", createdAt=" + createdAt +
        '}';
  }
}
