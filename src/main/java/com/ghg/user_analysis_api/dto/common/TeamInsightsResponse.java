package com.ghg.user_analysis_api.dto.common;

import lombok.Getter;
import java.util.List;

@Getter
public class TeamInsightsResponse extends BaseResponse {
    private final List<TeamInsight> teams;

    public TeamInsightsResponse(List<TeamInsight> teams) {
        super();
        this.teams = teams;
    }
}
