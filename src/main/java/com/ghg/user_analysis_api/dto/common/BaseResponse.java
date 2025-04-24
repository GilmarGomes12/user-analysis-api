package com.ghg.user_analysis_api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter; 

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public abstract class BaseResponse { 
    private final String timestamp;
    @JsonProperty("execution_time_ms")
    @Setter 
    private long executionTimeMs;

    protected BaseResponse() {
        this.timestamp = ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_INSTANT);
    }
}
