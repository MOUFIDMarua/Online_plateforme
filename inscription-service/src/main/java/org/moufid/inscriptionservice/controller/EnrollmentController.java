package org.moufid.inscriptionservice.controller;

import org.moufid.inscriptionservice.entities.Enrollment;
import org.moufid.inscriptionservice.feign.CourseRestClient;
import org.moufid.inscriptionservice.repository.EnrollmentRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRestClient courseRestClient;

    public EnrollmentController(EnrollmentRepository enrollmentRepository,
                                CourseRestClient courseRestClient) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRestClient = courseRestClient;
    }

    // ===========================
    // ✅ CREATE ENROLLMENT
    // ===========================
    @PostMapping
    public Enrollment enrollStudent(
            @RequestParam Long studentId,
            @RequestParam String courseTitle
    ) {
        String cleanTitle = cleanCourseTitle(courseTitle);
        Map<String, Object> course = findCourse(cleanTitle);

        Long courseId = extractCourseIdFromHal(course);

        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setEnrolledAt(LocalDateTime.now());
        enrollment.setCourseDetails(course);

        return enrollmentRepository.save(enrollment);
    }

    // ===========================
    // ✅ UPDATE ENROLLMENT
    // PUT /enrollments/{id}?studentId=5&courseTitle=Java Basics
    // ===========================
    @PutMapping("/{id}")
    public Enrollment updateEnrollment(
            @PathVariable Long id,
            @RequestParam Long studentId,
            @RequestParam String courseTitle
    ) {
        Enrollment existing = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        String cleanTitle = cleanCourseTitle(courseTitle);
        Map<String, Object> course = findCourse(cleanTitle);
        Long courseId = extractCourseIdFromHal(course);

        existing.setStudentId(studentId);
        existing.setCourseId(courseId);
        existing.setEnrolledAt(LocalDateTime.now());
        existing.setCourseDetails(course);

        return enrollmentRepository.save(existing);
    }

    // ===========================
    // ❌ DELETE ENROLLMENT
    // DELETE /enrollments/{id}
    // ===========================
    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable Long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new RuntimeException("Enrollment not found");
        }
        enrollmentRepository.deleteById(id);
    }

    // ===========================
    // GET ALL ENROLLMENTS
    // ===========================
    @GetMapping
    public List<Enrollment> getAllEnrollments() {
        List<Enrollment> list = enrollmentRepository.findAll();

        list.forEach(e -> {
            try {
                Object course = courseRestClient.getCourseById(e.getCourseId());
                e.setCourseDetails(course);
            } catch (Exception ignored) {}
        });

        return list;
    }

    // ===========================
    // 🔧 Helper: Clean Title
    // ===========================
    private String cleanCourseTitle(String title) {
        if (title == null) return null;

        if (title.contains("—")) return title.split("—")[0].trim();
        if (title.contains("-")) return title.split("-")[0].trim();

        return title.trim();
    }

    // ===========================
    // 🔧 Helper: Find Course with Feign
    // ===========================
    @SuppressWarnings("unchecked")
    private Map<String, Object> findCourse(String title) {
        Map<String, Object> result = courseRestClient.findCourseByTitle(title);

        if (result == null) {
            throw new RuntimeException("Course-service returned null for title: " + title);
        }

        if (result.containsKey("_embedded")) {
            Map<String, Object> embedded = (Map<String, Object>) result.get("_embedded");
            List<Map<String, Object>> courses =
                    (List<Map<String, Object>>) embedded.get("courses");
            if (courses == null || courses.isEmpty()) {
                throw new RuntimeException("No course found with title: " + title);
            }
            return courses.get(0);
        }

        return result;
    }

    // ===========================
    // 🔧 Helper: Extract ID from HAL
    // ===========================
    @SuppressWarnings("unchecked")
    private Long extractCourseIdFromHal(Map<String, Object> course) {
        Map<String, Object> links = (Map<String, Object>) course.get("_links");
        Map<String, Object> self = (Map<String, Object>) links.get("self");
        String href = (String) self.get("href");

        return Long.parseLong(href.substring(href.lastIndexOf('/') + 1));
    }
}
