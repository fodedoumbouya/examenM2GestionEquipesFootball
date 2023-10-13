package com.service.matchService.MatchService.controller;

import com.service.matchService.MatchService.model.Match;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Api(value = "Swagger2DemoRestController", description = "REST Apis related to Match Service Entity!!!!")
@RestController
@RequestMapping(value = "/match")
public class MatchServiceController {

    @Autowired
    RestTemplate restTemplate;
    private static final Map<Integer, Match> matchData = new HashMap<Integer, Match>() {

//  this.id = id;
//        this.teamAId = teamAId;
//        this.teamBId = teamBId;
//        this.score = score;
//        this.playersScore = playersScore;
        {
            put(1,  new Match(1,1,2,"2-0",Arrays.asList(1,2)));


        }

    };

    @GetMapping(value = "/{matchId}")
    public Match getTeamById(@PathVariable int matchId) {
        System.out.println("Getting Match by id " + matchId);
        Match match = teamData.get(teamId);
        if (team == null) {
            team = new Team(0, "N/A", Arrays.asList());
        }
        return team;
    }


    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
