package io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.repository;

import io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByName(String name);

    Student getById(Long id);
}
