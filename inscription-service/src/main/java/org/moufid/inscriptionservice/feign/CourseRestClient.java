package org.moufid.inscriptionservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cours-service")
public interface CourseRestClient {

    @GetMapping("/courses/{id}")
    Object getCourseById(@PathVariable Long id);
}