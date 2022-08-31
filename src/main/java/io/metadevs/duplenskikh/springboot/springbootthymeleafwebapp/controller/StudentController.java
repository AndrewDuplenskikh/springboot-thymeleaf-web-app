package io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.controller;

import io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.entity.Student;
import io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/students/")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("showForm")
    public String showStudentForm(Student student) {
        return "add-student";
    }

    @GetMapping("list")
    public String students(Model model) {
        model.addAttribute("students", this.studentRepository.findAll());
        return "index";
    }

    @PostMapping("add")
    public String addStudent(@Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-student";
        }
        this.studentRepository.save(student);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Student student = this.studentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student id: " + id));
        model.addAttribute("student", student);
        return "update-student";
    }

    @PostMapping("update/{id}")
    public RedirectView updateStudent(@PathVariable("id") long id, @Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            student.setId(id);
            return new RedirectView("/students/list");
        }
        studentRepository.save(student);
        model.addAttribute("students", this.studentRepository.findAll());
        return new RedirectView("/students/list");
    }

    @PostMapping("delete/{id}")
    public RedirectView deleteStudent(@PathVariable ("id") long id, Model model) {
        Student student = this.studentRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student id: " + id));
        this.studentRepository.delete(student);
        model.addAttribute("students", this.studentRepository.findAll());
        return new RedirectView("/students/list");
    }
}
