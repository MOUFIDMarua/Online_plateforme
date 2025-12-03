package org.moufid.coursservice;

import org.moufid.coursservice.entities.Course;
import org.moufid.coursservice.repositories.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class    CoursServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoursServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CourseRepository courseRepo) {
        return args -> {
            courseRepo.save(new Course(null, "Java Basics", "Intro", "Mme Sara", 20,"https://www.youtube.com/watch?v=vQYKsCyovuE&list=PLVwJuon7YOmB9RrHl2yXOf7yx2-G6Sz-m&index=1"));
            courseRepo.save(new Course(null, "Spring Boot", "REST APIs", "Mr Yassine", 30,"https://www.youtube.com/watch?v=vQYKsCyovuE&list=PLVwJuon7YOmB9RrHl2yXOf7yx2-G6Sz-m&index=1"));
        };
    }

}