package org.moufid.inscriptionservice.repository;


import org.moufid.inscriptionservice.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}