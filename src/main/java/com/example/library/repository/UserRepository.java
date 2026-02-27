package com.example.library.repository;

import com.example.library.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Username-р хэрэглэгч хайх
    Optional<User> findByUsername(String username);
    
    // Email-р хэрэглэгч хайх
    Optional<User> findByEmail(String email);
    
    // Username эсвэл email давхцаж байгаа эсэхийг шалгах
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
