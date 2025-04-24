package com.ghg.user_analysis_api.service;

import org.springframework.stereotype.Service;


@Service
public class EvaluationService {
    
    public EvaluationResponse runEvaluation() {
        // Implemente a lógica de avaliação aqui
        // Create response with required parameters, modify according to your EvaluationResponse constructor
        EvaluationResponse response = new EvaluationResponse("success", 100);
        // Configure os dados da resposta
        return response;
    }
}
