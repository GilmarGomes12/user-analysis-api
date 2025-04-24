package com.ghg.user_analysis_api.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CountryCount {
    private String country;
    private long total;
}
