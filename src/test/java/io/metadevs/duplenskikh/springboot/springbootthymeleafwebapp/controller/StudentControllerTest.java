package io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.entity.Student;
import io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void indexPageShouldHaveSpecificContent() throws Exception {
        String title = "Students!";
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(title)));
    }

    @Test
    public void addStudentPageShouldHaveSpecificContent() throws Exception {
        String title = "Add Student";
        mockMvc.perform(get("/students/add-student"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(title)));
    }

    @Test
    public void editStudentPageShouldHaveSpecificContent() throws Exception {
        String title = "Update Student";
        Student student = Student.builder()
                .id(1)
                .name("Vasya")
                .phone(900)
                .email("vasya@gmail.com")
                .build();
        when(studentService.getById(1L)).thenReturn(student);
        mockMvc.perform(get("/students/edit/{id}", 1L).sessionAttr("student", student))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(title)));
    }

    @Test
    public void addStudent() throws Exception {
        Student student = Student.builder()
                .name("Vasya")
                .phone(900)
                .email("vasya@gmail.com")
                .build();
        mockMvc.perform(post("/students/add").content(asJsonString(student)))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/students"));
    }

    @Test
    public void editStudent() throws Exception {
        Student student = Student.builder()
                .name("Vasya")
                .phone(900)
                .email("vasya@gmail.com")
                .build();
        mockMvc.perform(post("/students/update/{id}", 1L).content(asJsonString(student)))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/students"));
    }

    @Test
    public void deleteStudent() throws Exception {
        mockMvc.perform(post("/students/delete/{id}", 1L))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/students"));
    }


    private String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
