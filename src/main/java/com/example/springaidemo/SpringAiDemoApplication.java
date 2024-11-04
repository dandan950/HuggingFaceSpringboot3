package com.example.springaidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ClassUtils;

import java.nio.file.Paths;

@SpringBootApplication
public class SpringAiDemoApplication {

	public static void main(String[] args) {
		System.out.println("Current working directory: " + Paths.get("").toAbsolutePath().toString());
		System.out.println(ClassUtils.getDefaultClassLoader().getResource("").getPath());

		SpringApplication.run(SpringAiDemoApplication.class, args);
	}

}
