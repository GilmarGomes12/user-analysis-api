package com.ghg.user_analysis_api.service;

import com.ghg.user_analysis_api.dto.common.EndpointTestResult;
import java.util.Map;

public class EvaluationResponse {
    private Map<String, EndpointTestResult> results;

    public EvaluationResponse(Map<String, EndpointTestResult> results) {
        this.results = results;
    }

    public EvaluationResponse(String string, int i) {
       
    }

    public Map<String, EndpointTestResult> getResults() {
        return results;
    }

    public void setResults(Map<String, EndpointTestResult> results) {
        this.results = results;
    }
}
