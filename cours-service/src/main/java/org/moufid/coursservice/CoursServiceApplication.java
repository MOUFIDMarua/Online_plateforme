package org.moufid.coursservice;

import org.moufid.coursservice.entities.Course;
import org.moufid.coursservice.repositories.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CoursServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CourseRepository repo){
        return args -> {
            repo.save(new Course(null,"Java Basics","Introduction to Java","Mr Maroua",20));
            repo.save(new Course(null,"Spring Boot","Create REST APIs","Mme Sara",15));
            repo.save(new Course(null,"React JS","Frontend Development","Mr Yassine",10));
        };
    }
}