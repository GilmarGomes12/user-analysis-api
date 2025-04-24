package com.ghg.user_analysis_api.dto.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamInsight {
    private String team;
    @JsonProperty("total_members")
    private long totalMembers;
    private long leaders;
    @JsonProperty("completed_projects")
    private long completedProjects;
    @JsonProperty("active_percentage")
    private double activePercentage;
}
