package com.ghg.user_analysis_api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EndpointTestResult {
    private int status;
    @JsonProperty("time_ms")
    private long timeMs;
    @JsonProperty("valid_response")
    private boolean validResponse;
    private String error; 
}