package com.service.statsService.StatsService.controller;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.service.statsService.StatsService.model.*;
import com.service.statsService.StatsService.utils.utils;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Api(value = "Swagger2DemoRestController", description = "REST Apis related to Team Service Entity!!!!")
@RestController
@EnableCircuitBreaker
@RequestMapping(value = "/stats")
public class StatsServiceController {
    @Autowired
    RestTemplate restTemplate;


    @Timed(
            value = "StatsService.getStatsPlayerById.request",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "1.0"}
    )
    @HystrixCommand(fallbackMethod = "getPlayerByIdFallback")
    @ApiOperation(value = "Get player stats from playerId ", response = StatsTeam.class, tags = "getTeamExistById")
    @GetMapping(value = "/player-stats/{playerId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })
    public StatsPlayer getStatsPlayerById(@PathVariable int playerId) {
        Player player = utils.getPlayerById(playerId,restTemplate);
        List<Match> listMatch = utils.getAllMatchByTeamId(player.getPlayerTeam().getId(),restTemplate);
        int nbrMatch = listMatch.size();
        int nbrGoal = 0;
        for (Match match : listMatch) {
            for (Player playerScore : match.getPlayersScore()){
                if (playerScore.getId() == playerId){
                    nbrGoal = nbrGoal +1;
                }
            }
        }

        StatsPlayer statsPlayer = new StatsPlayer(player.getName(),nbrMatch,nbrGoal);
        return  statsPlayer;

    }

    @Timed(
            value = "StatsService.getStatsTeamById.request",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "1.0"}
    )
    @HystrixCommand(fallbackMethod = "getStatsTeamByIdFallback")
    @ApiOperation(value = "Get team stats from teamId ", response = StatsTeam.class, tags = "getTeamExistById")
    @GetMapping(value = "/team-stats/{teamId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })
    public StatsTeam getStatsTeamById(@PathVariable int teamId) {

        List<Match> listMatch = utils.getAllMatchByTeamId(teamId,restTemplate);
        int nbrMatch = listMatch.size();
        int nbrWins = 0;
        int nbrLoses = 0;
        String teamName = "";

        for (Match match : listMatch) {
            teamName = match.getPlayersScore().get(0).getPlayerTeam().getTeamName();
            if (match.getTeamAId() == teamId && match.getTeamAWin() || match.getTeamBId() == teamId && !match.getTeamAWin()){
                nbrWins = nbrWins +1;
            }else {
                nbrLoses = nbrLoses +1;
            }
        }
        StatsTeam statsTeam = new StatsTeam(nbrMatch,nbrWins,nbrLoses,teamName);
        return  statsTeam;
    }

    public StatsPlayer getPlayerByIdFallback(int playerId) {
        System.out.println("Player or Match Service is down!!! fallback route enabled...");
        return new  StatsPlayer("Player/Match Service is down!!!",-1,-1);
    }
    public StatsTeam getStatsTeamByIdFallback(int teamId){
        System.out.println("Match Service is down!!! fallback route enabled...");
        return  new StatsTeam(-1,-1,-1,"Match Service is down!!!");
    }
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
