package io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.controller;

import io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.entity.Student;
import io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/add-student")
    public String showStudentForm(Student student) {
        return "add-student";
    }

    @GetMapping("")
    public String students(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "index";
    }

    @PostMapping("/add")
    public RedirectView addStudent(@Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return new RedirectView("/add-student");
        }
        studentService.save(student);
        return new RedirectView("/students");
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Student student = studentService.getById(id);
        model.addAttribute("student", student);
        return "update-student";
    }

    @PostMapping("/update/{id}")
    public RedirectView updateStudent(@PathVariable("id") long id, @Valid Student student, BindingResult result, Model model) {
        if (result.hasErrors()) {
            student.setId(id);
            return new RedirectView("/students");
        }
        studentService.save(student);
        model.addAttribute("students", studentService.getAllStudents());
        return new RedirectView("/students");
    }

    @PostMapping("/delete/{id}")
    public RedirectView deleteStudent(@PathVariable ("id") long id, Model model) {
        studentService.delete(id);
        model.addAttribute("students", studentService.getAllStudents());
        return new RedirectView("/students");
    }
}
