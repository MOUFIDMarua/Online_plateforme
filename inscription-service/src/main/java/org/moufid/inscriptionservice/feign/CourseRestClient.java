package org.moufid.inscriptionservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "cours-service")
public interface CourseRestClient {

    @GetMapping("/courses/search/findByTitle")
    Map<String,Object> findCourseByTitle(@RequestParam("title") String title);

    @GetMapping("/courses/{id}")
    Map<String,Object> getCourseById(@PathVariable Long id);
}
