package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class BookService {

  @Autowired
  private BookRepository bookRepository;

  @PostConstruct
  public void initData() {
    // Хэрэв өгөгдөл хоосон бол жишээ өгөгдөл нэмэх
    if (bookRepository.count() == 0) {
      bookRepository.save(new Book(null, "Java Programming", "John Doe", "978-0134685991",
          2020, "Англи", 15, "Програмчлал", "https://images.unsplash.com/photo-1532012197267-da84d127e765?w=400"));
      bookRepository.save(new Book(null, "Монгол хэл бичиг", "Б.Батаа", "978-9992913451",
          2019, "Монгол", 20, "Монгол хэл", "https://images.unsplash.com/photo-1456513080510-7bf3a84b82f8?w=400"));
      bookRepository.save(new Book(null, "Spring Boot in Action", "Craig Walls", "978-1617292545",
          2021, "Англи", 10, "Програмчлал", "https://images.unsplash.com/photo-1544947950-fa07a98d237f?w=400"));
      bookRepository.save(new Book(null, "Математикийн онол", "Д.Дорж", "978-9992912345",
          2018, "Монгол", 8, "Математик", "https://images.unsplash.com/photo-1509228468518-180dd4864904?w=400"));
      bookRepository.save(new Book(null, "Data Structures", "John Doe", "978-0321573513",
          2019, "Англи", 12, "Мэдээллийн технологи",
          "https://images.unsplash.com/photo-1516979187457-637abb4f9353?w=400"));
    }
  }

  // Номын нэрээр хайлт
  public List<Book> searchByTitle(String title) {
    return bookRepository.findByTitleContainingIgnoreCase(title);
  }

  // Хичээлээр номын санал болгох
  public List<Book> getBooksBySubject(String subject) {
    return bookRepository.findBySubjectIgnoreCase(subject);
  }

  // Хэвлэгдсэн оноор хайлт
  public List<Book> searchByYear(Integer year) {
    return bookRepository.findByPublishYear(year);
  }

  // Үлдэгдэл тоо ширхэг шалгах
  public Integer getStockByTitle(String title) {
    return bookRepository.findByTitleIgnoreCase(title)
        .map(Book::getStock)
        .orElse(0);
  }

  // Монгол хэл дээрх номын тоо
  public Long countMongolianBooks() {
    return (long) bookRepository.findByLanguageIgnoreCase("Монгол").size();
  }

  // Зохиогчийн нэрээр номын жагсаалт
  public List<Book> getBooksByAuthor(String author) {
    return bookRepository.findByAuthorContainingIgnoreCase(author);
  }

  // Бүх ном харах
  public List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  // Ном нэмэх
  public Book addBook(Book book) {
    // MongoDB автоматаар ID үүсгэнэ
    return bookRepository.save(book);
  }

  // Ном засварлах
  public Book updateBook(String id, Book book) {
    if (bookRepository.existsById(id)) {
      book.setId(id);
      return bookRepository.save(book);
    }
    return null;
  }

  // Ном устгах
  public boolean deleteBook(String id) {
    if (bookRepository.existsById(id)) {
      bookRepository.deleteById(id);
      return true;
    }
    return false;
  }

  // ID-аар ном авах
  public Book getBookById(String id) {
    return bookRepository.findById(id).orElse(null);
  }
}
