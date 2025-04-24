package com.ghg.user_analysis_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data 
@NoArgsConstructor 
public class User {
    private String id; 
    private String name;
    private int score;
    private boolean active;
    private String country;
    private TeamInfo team;

    @JsonProperty("completed_projects_count") 
    private int completedProjectsCount;
    @JsonProperty("last_login_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastLoginDate;

    // If the original JSON has a list of completed projects instead of a count:
    // private List<String> projectsCompleted;
    // public int getCompletedProjectsCount() {
    //     return projectsCompleted != null ? projectsCompleted.size() : 0;
    // }
}
