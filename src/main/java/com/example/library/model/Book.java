package com.example.library.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class Book {
  @Id
  private String id; // MongoDB ObjectId
  private String title; // Номын нэр
  private String author; // Зохиогч
  private String isbn;
  private Integer publishYear; // Хэвлэгдсэн он
  private String language; // Хэл (Монгол, Англи, гэх мэт)
  private Integer stock; // Үлдэгдэл тоо ширхэг
  private String subject; // Хичээлийн нэр
  private String imageUrl; // Номын зургийн URL

  public Book() {
  }

  public Book(String id, String title, String author, String isbn, Integer publishYear,
      String language, Integer stock, String subject, String imageUrl) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.isbn = isbn;
    this.publishYear = publishYear;
    this.language = language;
    this.stock = stock;
    this.subject = subject;
    this.imageUrl = imageUrl;
  }

  // Getters and Setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public Integer getPublishYear() {
    return publishYear;
  }

  public void setPublishYear(Integer publishYear) {
    this.publishYear = publishYear;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Integer getStock() {
    return stock;
  }

  public void setStock(Integer stock) {
    this.stock = stock;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
