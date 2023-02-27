package dev.deneb.jpa.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "Book")
@Table(name = "books")
public class Book {

  @Id
  @SequenceGenerator(
    name = "book_sequence",
    sequenceName = "book_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "book_sequence"
  )
  @Column(updatable = false)
  private Long id;

  @Column(name = "book_name", nullable = false)
  private String bookName;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(
    name = "student_id",
    referencedColumnName = "id",
    nullable = false,
    foreignKey = @ForeignKey(name = "student_book_fk")
  )
  private Student student;

  public Book() {
  }

  public Book(String bookName, LocalDateTime createdAt) {
    this.bookName = bookName;
    this.createdAt = createdAt;
  }

  public Book(String bookName, LocalDateTime createdAt, Student student) {
    this.bookName = bookName;
    this.createdAt = createdAt;
    this.student = student;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBookName() {
    return bookName;
  }

  public void setBookName(String bookName) {
    this.bookName = bookName;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  @Override
  public String toString() {
    return "Book{" +
      "id=" + id +
      ", bookName='" + bookName + '\'' +
      ", createdAt=" + createdAt +
      ", student=" + student +
      '}';
  }
}
