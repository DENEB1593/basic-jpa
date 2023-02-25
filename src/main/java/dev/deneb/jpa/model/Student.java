package dev.deneb.jpa.model;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
  name = "students",
  uniqueConstraints = {
    @UniqueConstraint(name = "student_email_unique", columnNames = "email")
  }
)
public class Student {

  @Id
  @SequenceGenerator(
    name = "student_sequence",
    sequenceName = "student_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "student_sequence"
  )
  @Column(updatable = false)
  private Long id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private Integer age;

  @OneToOne(
    mappedBy = "student",
    orphanRemoval = true)
  // student_id_card 내 student 멤벼변수를 매핑한다.
  // orphan removal 옵션 사용 시 연관된 엔티티를 같이 삭제한다.
  private StudentIdCard studentIdCard;
  // mapping 후 student <-> student_id_card는 bi direction 관계를 가지게 되었다.

  @OneToMany(
    mappedBy = "student",
    orphanRemoval = true,
    cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
  )
  private final List<Book> books = new ArrayList<>();

  public Student() { }

  public Student(String firstName, String lastName, String email, Integer age) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
  }

  public Long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public void addBook(Book book) {
    if (!this.books.contains(book)) {
     this.books.add(book);
     book.setStudent(this);
    }
  }

  public void removeBook(Book book) {
    if (this.books.contains(book)) {
      this.books.remove(book);
      book.setStudent(null); //student의 연관관계를 끊음.
    }
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
      .append("id", id)
      .append("firstName", firstName)
      .append("lastName", lastName)
      .append("email", email)
      .append("age", age)
      .append("student_id_card", studentIdCard)
      .toString();
  }
}
