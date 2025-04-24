package com.ghg.user_analysis_api.dto.common;

import lombok.Getter;

import java.util.List;

@Getter
public class TopCountriesResponse extends BaseResponse {
    private final List<CountryCount> countries;

    public TopCountriesResponse(List<CountryCount> countries) {
        super();
        this.countries = countries;
    }
}
