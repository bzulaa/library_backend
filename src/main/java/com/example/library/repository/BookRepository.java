package com.example.library.repository;

import com.example.library.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

  // Номын нэрээр хайх (case-insensitive, contains)
  List<Book> findByTitleContainingIgnoreCase(String title);

  // Хичээлээр хайх (exact match, case-insensitive)
  List<Book> findBySubjectIgnoreCase(String subject);

  // Хэвлэгдсэн оноор хайх
  List<Book> findByPublishYear(Integer year);

  // Зохиогчоор хайх (case-insensitive, contains)
  List<Book> findByAuthorContainingIgnoreCase(String author);

  // Хэлээр хайх (exact match, case-insensitive)
  List<Book> findByLanguageIgnoreCase(String language);

  // Номын нэрээр олох (exact match, case-insensitive)
  Optional<Book> findByTitleIgnoreCase(String title);
}
