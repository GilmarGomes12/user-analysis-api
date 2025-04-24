package com.ghg.user_analysis_api.service;

import com.ghg.user_analysis_api.dto.common.CountryCount;
import com.ghg.user_analysis_api.dto.common.LoginCount;
import com.ghg.user_analysis_api.dto.common.TeamInsight;
import com.ghg.user_analysis_api.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final AtomicReference<List<User>> usersRef = new AtomicReference<>(Collections.emptyList());

    public int loadUsers(List<User> newUsers) {
        if (newUsers == null) {
            log.warn("Received null user list for loading.");
            usersRef.set(Collections.emptyList());
            return 0;
        }
       
        List<User> immutableUsers = List.copyOf(newUsers);
        usersRef.set(immutableUsers);
        log.info("Successfully loaded {} users into memory.", immutableUsers.size());
        return immutableUsers.size();
    }

    private List<User> getCurrentUsers() {
        return usersRef.get(); 
    }

    public List<User> findSuperUsers() {
        List<User> currentUsers = getCurrentUsers();
        if (currentUsers.isEmpty()) {
            return Collections.emptyList();
        }
        log.debug("Finding super users (score >= 900 and active) from {} users.", currentUsers.size());
        return currentUsers.parallelStream() 
                .filter(user -> user != null && user.getScore() >= 900 && user.isActive())
                .collect(Collectors.toList());
    }

    public List<CountryCount> getTopCountriesBySuperUsers(int limit) {
        List<User> superUsers = findSuperUsers(); 
        if (superUsers.isEmpty()) {
            return Collections.emptyList();
        }
        log.debug("Calculating top {} countries from {} super users.", limit, superUsers.size());

        Map<String, Long> countryCounts = superUsers.parallelStream()
                .filter(user -> user != null && user.getCountry() != null && !user.getCountry().isBlank())
                .collect(Collectors.groupingBy(
                        User::getCountry,
                        ConcurrentHashMap::new, 
                        Collectors.counting()
                ));

        return countryCounts.entrySet().stream()
                .map(entry -> new CountryCount(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparingLong(CountryCount::getTotal).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public List<TeamInsight> getTeamInsights() {
        List<User> currentUsers = getCurrentUsers();
        if (currentUsers.isEmpty()) {
            return Collections.emptyList();
        }
        log.debug("Calculating team insights from {} users.", currentUsers.size());

        Map<String, List<User>> usersByTeam = currentUsers.parallelStream()
                .filter(user -> user != null && user.getTeam() != null && user.getTeam().getName() != null && !user.getTeam().getName().isBlank())
                .collect(Collectors.groupingBy(
                        user -> user.getTeam().getName(),
                        ConcurrentHashMap::new,
                        Collectors.toList()
                ));

        return usersByTeam.entrySet().parallelStream()
                .map(entry -> {
                    String teamName = entry.getKey();
                    List<User> teamMembers = entry.getValue();
                    long totalMembers = teamMembers.size();

                    if (totalMembers == 0) {
                        return null; 
                    }

                    long leaders = teamMembers.stream()
                            .filter(user -> user.getTeam().isLeader())
                            .count();

                    long completedProjects = teamMembers.stream()
                            .mapToLong(User::getCompletedProjectsCount) 
                            .sum();

                    long activeMembers = teamMembers.stream()
                            .filter(User::isActive)
                            .count();

                    double activePercentage = (totalMembers > 0)
                        ? BigDecimal.valueOf(activeMembers * 100.0 / totalMembers)
                                    .setScale(1, RoundingMode.HALF_UP).doubleValue()
                        : 0.0;

                    return new TeamInsight(teamName, totalMembers, leaders, completedProjects, activePercentage);
                })
                .filter(Objects::nonNull) 
                .sorted(Comparator.comparing(TeamInsight::getTeam)) 
                .collect(Collectors.toList());
    }

    public List<LoginCount> getActiveUsersPerDay(Integer minLogins) {
        List<User> currentUsers = getCurrentUsers();
         if (currentUsers.isEmpty()) {
            return Collections.emptyList();
        }
        log.debug("Calculating active users per day from {} users (min logins: {}).", currentUsers.size(), minLogins);

        Map<LocalDate, Long> loginsPerDay = currentUsers.parallelStream()
                .filter(user -> user != null && user.getLastLoginDate() != null)
                .collect(Collectors.groupingBy(
                        User::getLastLoginDate,
                        ConcurrentHashMap::new, 
                        Collectors.counting()
                ));

        return loginsPerDay.entrySet().stream()
                .filter(entry -> minLogins == null || entry.getValue() >= minLogins) 
                .map(entry -> new LoginCount(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(LoginCount::getDate)) 
                .collect(Collectors.toList());
    }
}