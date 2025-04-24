package com.ghg.user_analysis_api.controller;

import com.ghg.user_analysis_api.dto.common.ActiveUsersResponse;
import com.ghg.user_analysis_api.dto.common.BaseResponse;
import com.ghg.user_analysis_api.dto.common.CountryCount;
import com.ghg.user_analysis_api.dto.common.LoginCount;
import com.ghg.user_analysis_api.dto.common.SuperUserResponse;
import com.ghg.user_analysis_api.dto.common.TeamInsight;
import com.ghg.user_analysis_api.dto.common.TeamInsightsResponse;
import com.ghg.user_analysis_api.dto.common.TopCountriesResponse;
import com.ghg.user_analysis_api.dto.common.UserLoadResponse;
import com.ghg.user_analysis_api.model.User;
import com.ghg.user_analysis_api.service.EvaluationResponse;
import com.ghg.user_analysis_api.service.EvaluationService;
import com.ghg.user_analysis_api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") 
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private EvaluationService evaluationService; 

    private <T extends BaseResponse> ResponseEntity<T> buildResponse(T response, long startTimeNanos) {
        long endTimeNanos = System.nanoTime();
        response.setExecutionTimeMs((endTimeNanos - startTimeNanos) / 1_000_000);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/users")
    public ResponseEntity<UserLoadResponse> loadUsers(@RequestBody List<User> users) {
        log.info("Received POST /users request");
        long startTime = System.nanoTime(); 
        if (users == null) {
             log.warn("Received empty or null user list in POST /users");
             return ResponseEntity.badRequest().body(new UserLoadResponse("Received null or empty user list.", 0));
        }
        int count = userService.loadUsers(users);
        log.info("Finished processing POST /users request, loaded {} users.", count);
        return ResponseEntity.ok(new UserLoadResponse("Arquivo recebido com sucesso", count));
    }

    @GetMapping("/superusers")
    public ResponseEntity<SuperUserResponse> getSuperUsers() {
        log.info("Received GET /superusers request");
        long startTime = System.nanoTime();
        List<User> superUsers = userService.findSuperUsers();
        SuperUserResponse response = new SuperUserResponse(superUsers);
        log.info("Finished processing GET /superusers request");
        return buildResponse(response, startTime);
    }

    @GetMapping("/top-countries")
    public ResponseEntity<TopCountriesResponse> getTopCountries() {
        log.info("Received GET /top-countries request");
        long startTime = System.nanoTime();
        List<CountryCount> topCountries = userService.getTopCountriesBySuperUsers(5); 
        TopCountriesResponse response = new TopCountriesResponse(topCountries);
        log.info("Finished processing GET /top-countries request");
        return buildResponse(response, startTime);
    }

    @GetMapping("/team-insights")
    public ResponseEntity<TeamInsightsResponse> getTeamInsights() {
        log.info("Received GET /team-insights request");
        long startTime = System.nanoTime();
        List<TeamInsight> insights = userService.getTeamInsights();
        TeamInsightsResponse response = new TeamInsightsResponse(insights);
        log.info("Finished processing GET /team-insights request");
        return buildResponse(response, startTime);
    }

    @GetMapping("/active-users-per-day")
    public ResponseEntity<ActiveUsersResponse> getActiveUsersPerDay(
            @RequestParam(name = "min", required = false) Integer minLogins) {
        log.info("Received GET /active-users-per-day request with min={}", minLogins);
        long startTime = System.nanoTime();
        List<LoginCount> logins = userService.getActiveUsersPerDay(minLogins);
        ActiveUsersResponse response = new ActiveUsersResponse(logins);
        log.info("Finished processing GET /active-users-per-day request");
        return buildResponse(response, startTime);
    }

    @GetMapping("/evaluation")
    public ResponseEntity<EvaluationResponse> runEvaluation() {
        log.info("Received GET /evaluation request");
        EvaluationResponse report = evaluationService.runEvaluation();
         log.info("Finished processing GET /evaluation request");

        return ResponseEntity.ok(report);
    }
}
