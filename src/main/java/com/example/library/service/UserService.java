package com.example.library.service;

import com.example.library.model.User;
import com.example.library.repository.UserRepository;
import com.example.library.dto.LoginRequest;
import com.example.library.dto.RegisterRequest;
import com.example.library.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  // *** Бүртгүүлэх ***
  public AuthResponse register(RegisterRequest request) {
    // Username давхцаж байгаа эсэхийг шалгах
    if (userRepository.existsByUsername(request.getUsername())) {
      return new AuthResponse(null, null, null, null, "Хэрэглэгчийн нэр аль хэдийн бүртгэлтэй байна");
    }

    // Email давхцаж байгаа эсэхийг шалгах
    if (userRepository.existsByEmail(request.getEmail())) {
      return new AuthResponse(null, null, null, null, "И-мэйл хаяг аль хэдийн бүртгэлтэй байна");
    }

    // Шинэ хэрэглэгч үүсгэх
    User user = new User();
    user.setUsername(request.getUsername());
    user.setEmail(request.getEmail());
    user.setPassword(encodePassword(request.getPassword())); // Энгийн Base64 encode (тестэд)
    user.setRole(request.getRole());

    // Хадгалах
    User savedUser = userRepository.save(user);

    // Token үүсгэх (энгийн хувилбар - тестэд)
    String token = generateSimpleToken(savedUser);

    return new AuthResponse(
        token,
        savedUser.getUsername(),
        savedUser.getEmail(),
        savedUser.getRole(),
        "Амжилттай бүртгэгдлээ");
  }

  // *** Нэвтрэх ***
  public AuthResponse login(LoginRequest request) {
    // Хэрэглэгч хайх
    Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

    if (userOpt.isEmpty()) {
      return new AuthResponse(null, null, null, null, "Хэрэглэгчийн нэр эсвэл нууц үг буруу байна");
    }

    User user = userOpt.get();

    // Нууц үг шалгах
    if (!checkPassword(request.getPassword(), user.getPassword())) {
      return new AuthResponse(null, null, null, null, "Хэрэглэгчийн нэр эсвэл нууц үг буруу байна");
    }

    // Token үүсгэх
    String token = generateSimpleToken(user);

    return new AuthResponse(
        token,
        user.getUsername(),
        user.getEmail(),
        user.getRole(),
        "Амжилттай нэвтэрлээ");
  }

  // *** Бүх хэрэглэгчийг авах (Admin) ***
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  // *** ID-гаар хэрэглэгч авах ***
  public Optional<User> getUserById(String id) {
    return userRepository.findById(id);
  }

  // *** Хэрэглэгч устгах ***
  public boolean deleteUser(String id) {
    if (userRepository.existsById(id)) {
      userRepository.deleteById(id);
      return true;
    }
    return false;
  }

  // *** Хэрэглэгчийн мэдээлэл шинэчлэх ***
  public User updateUser(String id, User updatedUser) {
    Optional<User> userOpt = userRepository.findById(id);
    if (userOpt.isPresent()) {
      User user = userOpt.get();
      if (updatedUser.getUsername() != null)
        user.setUsername(updatedUser.getUsername());
      if (updatedUser.getEmail() != null)
        user.setEmail(updatedUser.getEmail());
      if (updatedUser.getRole() != null)
        user.setRole(updatedUser.getRole());
      return userRepository.save(user);
    }
    return null;
  }

  // ===== Туслах функцүүд =====

  // Энгийн нууц үг encode (Base64 - зөвхөн тестэд!)
  private String encodePassword(String rawPassword) {
    return Base64.getEncoder().encodeToString(rawPassword.getBytes());
  }

  // Нууц үг шалгах
  private boolean checkPassword(String rawPassword, String encodedPassword) {
    String encoded = encodePassword(rawPassword);
    return encoded.equals(encodedPassword);
  }

  // Энгийн Token үүсгэх (Base64 - зөвхөн тестэд!)
  private String generateSimpleToken(User user) {
    String data = user.getId() + ":" + user.getUsername() + ":" + user.getRole();
    return Base64.getEncoder().encodeToString(data.getBytes());
  }
}
