package io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.service;

import io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.entity.Student;
import io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void save(Student student) {
        studentRepository.save(student);
    }

    public Student getById(Long id) {
        return studentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student id: " + id));
    }

    public void delete(Long id) {
        Student student = this.studentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student id: " + id));
        this.studentRepository.delete(student);
    }
}
