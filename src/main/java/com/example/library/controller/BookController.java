package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

  @Autowired
  private BookService bookService;

  // Номын нэрээр хайлт хийдэг
  @GetMapping("/search/title")
  public List<Book> searchByTitle(@RequestParam String title) {
    return bookService.searchByTitle(title);
  }

  // Тухайн хичээлд тохирох номын нэрсийг санал болгодог
  @GetMapping("/search/subject")
  public List<Book> getBooksBySubject(@RequestParam String subject) {
    return bookService.getBooksBySubject(subject);
  }

  // Хэвлэгдсэн оноор хайлт хийх
  @GetMapping("/search/year")
  public List<Book> searchByYear(@RequestParam Integer year) {
    return bookService.searchByYear(year);
  }

  // Өгөгдсөн ном хэдэн ширхэг үлдэгдэлтэй байгааг гаргах
  @GetMapping("/stock")
  public Object getStock(@RequestParam(required = false) String title) {
    // Хэрэв title байхгүй бол үлдэгдэлтэй бүх номыг буцаана
    if (title == null || title.isEmpty()) {
      return bookService.getAllBooks().stream()
          .filter(book -> book.getStock() != null && book.getStock() > 0)
          .toList();
    }
    
    // Хэрэв title өгөгдсөн бол тухайн номын үлдэгдлийг буцаана
    Integer stock = bookService.getStockByTitle(title);
    Map<String, Object> response = new HashMap<>();
    response.put("title", title);
    response.put("stock", stock);
    return response;
  }

  // Монгол хэл дээр хэвлэгдсэн номын тоог гаргах
  @GetMapping("/count/mongolian")
  public Map<String, Object> countMongolianBooks() {
    Long count = bookService.countMongolianBooks();
    Map<String, Object> response = new HashMap<>();
    response.put("count", count);
    response.put("language", "Монгол");
    return response;
  }

  // Зохиогчийн нэрээр номын жагсаалтыг гаргах
  @GetMapping("/search/author")
  public List<Book> getBooksByAuthor(@RequestParam String author) {
    return bookService.getBooksByAuthor(author);
  }

  // Бүх ном харах
  @GetMapping("/all")
  public List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  // Ном нэмэх
  @PostMapping("/add")
  public Book addBook(@RequestBody Book book) {
    return bookService.addBook(book);
  }

  // ID-аар ном авах
  @GetMapping("/{id}")
  public Book getBookById(@PathVariable String id) {
    return bookService.getBookById(id);
  }

  // Ном засварлах
  @PutMapping("/{id}")
  public Map<String, Object> updateBook(@PathVariable String id, @RequestBody Book book) {
    Book updated = bookService.updateBook(id, book);
    Map<String, Object> response = new HashMap<>();
    if (updated != null) {
      response.put("success", true);
      response.put("message", "Ном амжилттай засагдлаа!");
      response.put("book", updated);
    } else {
      response.put("success", false);
      response.put("message", "Ном олдсонгүй!");
    }
    return response;
  }

  // Ном устгах
  @DeleteMapping("/{id}")
  public Map<String, Object> deleteBook(@PathVariable String id) {
    boolean deleted = bookService.deleteBook(id);
    Map<String, Object> response = new HashMap<>();
    if (deleted) {
      response.put("success", true);
      response.put("message", "Ном амжилттай устгагдлаа!");
    } else {
      response.put("success", false);
      response.put("message", "Ном олдсонгүй!");
    }
    return response;
  }
}
