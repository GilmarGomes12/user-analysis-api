package com.ghg.user_analysis_api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ApiResponse<T> {
    private final String timestamp;
    @JsonProperty("execution_time_ms")
    private final long executionTimeMs;
    private final T data; 

    private ApiResponse(long executionTimeMs, T data) {
        this.timestamp = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
        this.executionTimeMs = executionTimeMs;
        this.data = data;
    }

    public static <T> ApiResponse<T> create(long startTimeNanos, T data) {
        long endTimeNanos = System.nanoTime();
        long durationMs = (endTimeNanos - startTimeNanos) / 1_000_000;
        return new ApiResponse<>(durationMs, data);
    }

     public static <T> ApiResponse<T> createDirect(long startTimeNanos, T data) {
        long endTimeNanos = System.nanoTime();
        long durationMs = (endTimeNanos - startTimeNanos) / 1_000_000;

         return new ApiResponse<>(durationMs, data);
    }
}
