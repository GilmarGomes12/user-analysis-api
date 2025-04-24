package com.ghg.user_analysis_api.dto.common;

public class UserLoadResponse extends BaseResponse {
    private String message;
    private int count;
    
    public UserLoadResponse(String message, int count) {
        this.message = message;
        this.count = count;
    }
    
    // Getters e setters
}
