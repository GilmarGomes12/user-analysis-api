package com.ghg.user_analysis_api.dto.common;

import lombok.Getter;
import java.util.List;

@Getter
public class ActiveUsersResponse extends BaseResponse {
    private final List<LoginCount> logins;

    public ActiveUsersResponse(List<LoginCount> logins) {
        super();
        this.logins = logins;
    }
}
