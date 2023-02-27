package dev.deneb.jpa.model;

import jakarta.persistence.*;

@Entity(name = "StudentIdCard")
@Table(
  name = "student_id_card",
  uniqueConstraints = {
  @UniqueConstraint(name = "student_id_card_number_unique", columnNames = "card_number")
}
)
public class StudentIdCard {

  @Id
  @SequenceGenerator(
    name = "student_id_card_sequence",
    sequenceName = "student_id_card_sequence",
    allocationSize = 1
  )
  @GeneratedValue(
    strategy = GenerationType.SEQUENCE,
    generator = "student_sequence"
  )
  @Column(updatable = false)
  private Long id;

  @Column(name = "card_number", nullable = true, length = 15)
  private String cardNumber;

  @OneToOne(
    cascade = CascadeType.ALL,
    fetch = FetchType.EAGER // one-to-one default Eager
  )
  @JoinColumn(
    name = "student_id",
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name = "student_id_card_fk")
  )
  private Student student;

  public StudentIdCard() { }

  public StudentIdCard(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public StudentIdCard(String cardNumber, Student student) {
    this.cardNumber = cardNumber;
    this.student = student;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
    this.student = student;
  }

  @Override
  public String toString() {
    return "StudentIdCard{" +
      "id=" + id +
      ", cardNumber='" + cardNumber + '\'' +
      ", student=" + student +
      '}';
  }
}
