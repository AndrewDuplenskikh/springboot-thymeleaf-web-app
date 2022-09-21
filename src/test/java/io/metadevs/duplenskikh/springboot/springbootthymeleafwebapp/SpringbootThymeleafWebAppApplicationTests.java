package io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp;

import io.metadevs.duplenskikh.springboot.springbootthymeleafwebapp.controller.StudentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringbootThymeleafWebAppApplicationTests {

	@Autowired
	StudentController controller;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
	}
}
