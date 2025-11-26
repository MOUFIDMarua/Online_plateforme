package org.moufid.inscriptionservice.repository;


import org.moufid.inscriptionservice.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
}
