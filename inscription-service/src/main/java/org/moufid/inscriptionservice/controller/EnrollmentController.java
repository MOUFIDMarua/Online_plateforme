package org.moufid.inscriptionservice.controller;

import org.moufid.inscriptionservice.entities.Enrollment;
import org.moufid.inscriptionservice.feign.CourseRestClient;
import org.moufid.inscriptionservice.repository.EnrollmentRepository;
import org.moufid.inscriptionservice.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRestClient courseRestClient;

    public EnrollmentController(EnrollmentRepository enrollmentRepository,
                                StudentRepository studentRepository,
                                CourseRestClient courseRestClient) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRestClient = courseRestClient;
    }

    @PostMapping
    public Enrollment enrollStudent(@RequestParam Long studentId, @RequestParam Long courseId) {
        Enrollment e = new Enrollment(null, studentId, courseId, null);
        Enrollment saved = enrollmentRepository.save(e);

        // Appel Feign → cours-service
        Object course = courseRestClient.getCourseById(courseId);
        saved.setCourseDetails(course);

        return saved;
    }

    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> list = enrollmentRepository.findAll();
        list.forEach(e -> e.setCourseDetails(courseRestClient.getCourseById(e.getCourseId())));
        return list;
    }
}