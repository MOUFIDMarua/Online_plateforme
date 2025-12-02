package org.moufid.statistiqueservice.controller;

import lombok.RequiredArgsConstructor;
import org.moufid.statistiqueservice.model.YoutubeApiResponse;
import org.moufid.statistiqueservice.service.VideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class VideoController {

    private final VideoService service;

    @GetMapping("/api/videos")
    public Mono<YoutubeApiResponse> search(
            @RequestParam String keyword) {
        return service.searchVideos(keyword);
    }
}
