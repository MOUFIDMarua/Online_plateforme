package org.moufid.statistiqueservice.service;

import lombok.RequiredArgsConstructor;
import org.moufid.statistiqueservice.model.YoutubeApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class VideoService {

    private final WebClient webClient;

    @Value("${youtube.api-key}")
    private String apiKey;

    @Value("${youtube.base-url}")
    private String baseUrl;

    public VideoService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public Mono<YoutubeApiResponse> searchVideos(String keyword) {

        String url = baseUrl + "/search?part=snippet&type=video&maxResults=5&q="
                + keyword + "&key=" + apiKey;

        System.out.println("CALLING URL ---> " + url);

        return webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(YoutubeApiResponse.class); // ❌ plus de block()
    }
}