package org.moufid.coursservice.repositories;

import org.moufid.coursservice.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource
public interface CourseRepository extends JpaRepository<Course, Long> {

    @RestResource(path = "findByTitle", rel = "findByTitle")
    List<Course> findByTitle(@Param("title") String title);
}