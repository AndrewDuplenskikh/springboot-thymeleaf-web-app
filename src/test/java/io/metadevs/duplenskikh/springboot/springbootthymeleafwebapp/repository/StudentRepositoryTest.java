package io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.repository;

import io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.entity.Student;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    @Rollback(false)
    @Order(1)
    public void save() {
        Student student = Student.builder()
                .name("zverek")
                .email("zverek.meta-devs.io")
                .phone(777)
                .build();

        Student savedStudent = studentRepository.save(student);

        assertThat(savedStudent.getId()).isNotNull();
    }

    @Test
    @Rollback(false)
    @Order(2)
    public void update() {
        Student student = studentRepository.findByName("zverek");
        student.setPhone(111);
        studentRepository.save(student);
        Student updatedStudent = studentRepository.findByName("zverek");
        assertThat(updatedStudent.getPhone()).isEqualTo(111);
    }

    @Test
    @Rollback(false)
    @Order(3)
    public void delete() {
        Student student = studentRepository.findByName("zverek");
        studentRepository.deleteById(student.getId());
        Student deletedStudent = studentRepository.findByName(student.getName());
        assertThat(deletedStudent).isNull();
    }
}
