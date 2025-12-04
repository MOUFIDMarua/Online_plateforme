package org.moufid.statistiqueservice.service;

import lombok.RequiredArgsConstructor;
import org.moufid.statistiqueservice.model.YoutubeApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final WebClient.Builder webClientBuilder;

    @Value("${youtube.api-key}")
    private String apiKey;

    @Value("${youtube.base-url}")
    private String baseUrl;

    public Mono<YoutubeApiResponse> searchVideos(String keyword) {

        return webClientBuilder.build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(baseUrl)                          // www.googleapis.com
                        .path("/youtube/v3/search")             // correct path
                        .queryParam("part", "snippet")
                        .queryParam("maxResults", 25)           // → 25 vidéos
                        .queryParam("q", keyword)
                        .queryParam("type", "video")
                        .queryParam("key", apiKey)
                        .build()
                )
                .retrieve()
                .bodyToMono(YoutubeApiResponse.class);
    }
}
