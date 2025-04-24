package com.ghg.user_analysis_api.dto.common;

import com.ghg.user_analysis_api.model.User;
import lombok.Getter;

import java.util.List;

@Getter
public class SuperUserResponse extends BaseResponse {
    private final List<User> data;

    public SuperUserResponse(List<User> data) {
        super();
        this.data = data;
    }
}