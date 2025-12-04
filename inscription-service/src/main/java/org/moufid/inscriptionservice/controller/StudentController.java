package org.moufid.inscriptionservice.controller;

import org.moufid.inscriptionservice.entities.Student;
import org.moufid.inscriptionservice.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository repo) {
        this.studentRepository = repo;
    }

    @PostMapping("/login")
    public Student login(@RequestParam String email,
                         @RequestParam String password) {

        Student s = studentRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (!s.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        return s;
    }

    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }
}

