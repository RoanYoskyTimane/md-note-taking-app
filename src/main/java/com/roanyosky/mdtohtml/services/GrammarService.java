package com.roanyosky.mdtohtml.services;

import com.roanyosky.mdtohtml.dtos.GrammarCheckDto;
import io.github.bucket4j.Bucket;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
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

    public GrammarCheckDto checkGrammar(String content, String userId) {
        // Use the modern v8+ API to build/get the bucket
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
                .bodyValue("text=" + content + "&language=en-US")
                .retrieve()
                .bodyToMono(GrammarCheckDto.class)
                .block();
    }
}