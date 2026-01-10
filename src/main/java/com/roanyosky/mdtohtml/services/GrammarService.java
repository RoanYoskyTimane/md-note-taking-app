package com.roanyosky.mdtohtml.services;

import com.roanyosky.mdtohtml.dtos.LanguageToolResponseDto;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class GrammarService {
    private final WebClient webClient;

    private final Map<String, Bucket> userBuckets = new ConcurrentHashMap<>();

    public LanguageToolResponseDto checkGrammar(String content, String userId) {
        Bucket bucket = userBuckets.computeIfAbsent(userId, key ->
                Bucket.builder()
                        .addLimit(limit -> limit.capacity(20).refillIntervally(20, Duration.ofMinutes(1)))
                        .build()
        );

        if (!bucket.tryConsume(1)) {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests for user: " + userId);
        }

        return webClient.post()
                .uri("/check")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData("text", content)
                        .with("language", "auto"))
                .retrieve()
                .bodyToMono(LanguageToolResponseDto.class)
                .block();
    }
}