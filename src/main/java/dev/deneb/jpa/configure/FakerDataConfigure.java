package dev.deneb.jpa.configure;

import com.github.javafaker.Faker;
import dev.deneb.jpa.model.Student;
import dev.deneb.jpa.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;

@Configuration
public class FakerDataConfigure {

  @Profile("!prod")
  @Bean
  public CommandLineRunner set(StudentRepository studentRepository) {
    return args -> {
      generateTestStudents(studentRepository);


      Sort sort = Sort.by(Sort.Direction.DESC, "id");
      studentRepository.findAll(sort)
        .forEach(student -> System.out.println(student.getFirstName()));
    };
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
