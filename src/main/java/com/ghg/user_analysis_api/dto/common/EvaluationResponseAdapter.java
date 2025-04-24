package com.ghg.user_analysis_api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class EvaluationResponseAdapter { 
    @JsonProperty("tested_endpoints")
    private Map<String, EndpointTestResult> testedEndpoints;
}
