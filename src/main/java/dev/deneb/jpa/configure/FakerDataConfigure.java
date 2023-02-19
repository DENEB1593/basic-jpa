package dev.deneb.jpa.configure;

import com.github.javafaker.Faker;
import dev.deneb.jpa.model.Student;
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

@Configuration
public class FakerDataConfigure {

  private static final Logger log = LoggerFactory.getLogger(FakerDataConfigure.class);

  @Profile("!prod")
  @Bean
  public CommandLineRunner set(StudentRepository studentRepository) {
    return args -> {
      generateTestStudents(studentRepository);


      PageRequest pageRequest = PageRequest.of(
        0,
        10,
        Sort.by("firstName").ascending()
      );

      // select first_name from students order by first_name asc limit 0, 10;
      Page<Student> students = studentRepository.findAll(pageRequest);
      log.info("{}", students.getContent());
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
