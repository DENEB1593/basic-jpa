package dev.deneb.jpa;

import dev.deneb.jpa.model.Student;
import dev.deneb.jpa.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
    return args -> {
      studentRepository.save(new Student("deneb", "lee", "deneb@gmail.com", 30));

      studentRepository.findStudentByEmailEqualsAndAgeIsGreaterThan(
        "deneb@gmail.com", 20)
        .ifPresentOrElse(
          System.out::println,
          System.out::println
        );

      // @Query
      studentRepository.findByFirstName("deneb")
        .ifPresentOrElse(
          System.out::println,
          System.out::println
        );

      //@Query(native)
      studentRepository.findByFirstNameWithNativeQuery("deneb")
        .ifPresentOrElse(
          System.out::println,
          System.out::println
        );
    };
  }

}
