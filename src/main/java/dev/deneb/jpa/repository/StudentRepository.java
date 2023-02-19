package dev.deneb.jpa.repository;

import dev.deneb.jpa.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

  @Query("select s from Student s where s.firstName = ?1")
  Optional<Student> findByFirstName(String firstName);

  @Query(value = "select * from students where first_name = ?1", nativeQuery = true)
  Optional<Student> findByFirstNameWithNativeQuery(String firstName);

  Optional<Student> findStudentByEmailEqualsAndAgeIsGreaterThan(
    String email, Integer age);


  @Transactional
  @Modifying
  @Query("DELETE FROM Student s where s.id = ?1")
  int deleteByStudentId(Long id);


}
