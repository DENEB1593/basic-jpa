package dev.deneb.jpa.configure;

import com.github.javafaker.Faker;
import dev.deneb.jpa.model.*;
import dev.deneb.jpa.repository.StudentIdCardRepository;
import dev.deneb.jpa.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class FakerDataConfigure {

  private static final Logger log = LoggerFactory.getLogger(FakerDataConfigure.class);

  @Profile("!prod")
  @Bean
  public CommandLineRunner set(StudentRepository studentRepository) {
    return args -> {
      Faker faker = new Faker();
      for (int i = 0; i < 1; i++) {

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = """ 
          %s.%s@deneb.com 
          """.formatted(firstName, lastName);
        int age = faker.number().numberBetween(15, 25);

        Student student = new Student(firstName, lastName, email, age);

        student.addBook(new Book("Clean Code", LocalDateTime.now().minusDays(5), student));
        student.addBook(new Book("Effective Java", LocalDateTime.now().minusDays(2), student));
        student.addBook(new Book("Practice spring boot", LocalDateTime.now().minusDays(1), student));

        StudentIdCard studentIdCard = new StudentIdCard("123456789", student);

        // student_id_card 저장 시 student도 저장되는 것을 확인할 수 있다.
        student.setStudentIdCard(studentIdCard);

        student.addEnrolment(new Enrolment(
          new EnrolmentId(1L, 1L),
          student,
          new Course("Computer Science", "IT"), LocalDateTime.now().minusDays(10)
          )
        );

        student.addEnrolment(new Enrolment(
            new EnrolmentId(1L, 2L),
            student, new Course("Economy", "EP"), LocalDateTime.now().minusDays(18)

          )
        );

        studentRepository.save(student);

        studentRepository.findById(2L)
          .ifPresent(s -> {
            System.out.println("student fetch lazy ...");
            List<Book> books = s.getBooks();
            books.forEach(book -> {
              System.out.println(s.getFirstName() + " borrowed " + book.getBookName());
            });

          });
      }
    };
  }

  private static void sorting(StudentRepository studentRepository) {
    Sort sort = Sort.by("firstName").descending()
      .and(Sort.by("age").ascending());

    studentRepository.findAll(sort)
      .forEach(student ->
        log.info("{} : {}", student.getFirstName(), student.getAge())
      );
  }

  private static void generateTestStudents(StudentRepository studentRepository) {
    Faker faker = new Faker();
    for (int i = 0; i < 100; i++) {

      String firstName = faker.name().firstName();
      String lastName = faker.name().lastName();
      String email = """ 
        %s.%s@deneb.com 
        """.formatted(firstName,lastName);
      int age = faker.number().numberBetween(15, 25);

      studentRepository.save(
        new Student(
          firstName,
          lastName,
          email,
          age
        )
      );

    }
  }

}
