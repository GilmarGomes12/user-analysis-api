package com.ghg.user_analysis_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamInfo {
    private String name;
    @JsonProperty("is_leader") 
    private boolean leader; 
}
