package com.service.matchService.MatchService.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.service.matchService.MatchService.model.Match;
import com.service.matchService.MatchService.model.Player;
import com.service.matchService.MatchService.model.ResponseRest;
import com.service.matchService.MatchService.model.playerTeam;
import com.service.matchService.MatchService.utils.utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Api(value = "Swagger2DemoRestController", description = "REST Apis related to Match Service Entity!!!!")
@RestController
@RequestMapping(value = "/match")
public class MatchServiceController {

    @Autowired
    RestTemplate restTemplate;
    private static final Map<Integer, Match> matchData = new HashMap<Integer, Match>() {

        {
            put(1,  new Match(1,1,2,"2-0",true,Arrays.asList(new Player(1,"player 1", new playerTeam(1,"team A")))));


        }

    };

    @ApiOperation(value = "delete specific Match in the System ", response = String.class, tags = "deleteMatch")
    @DeleteMapping("/{id}")
    public String deleteMatch(@PathVariable int id){
        System.out.println("Updating by id " + id);
        String msg = "Success";
        Match team = matchData.get(id);
        if (team == null) {
            msg = "The ID does not exist";
        }else{
            matchData.remove(id);
        }
        return msg;
    }




    @HystrixCommand(fallbackMethod = "getPlayerAndMatchByIdFallback2")
    @ApiOperation(value = "Put specific Team in the System ", response = String.class, tags = "putMatch")
    @PutMapping("/{id}")
    public String putMatch(@PathVariable int id, @RequestBody Match match) {
        System.out.println("Posting match " + match);
        String msg = "Success";
        ResponseRest responseRest1 = utils.teamExist(match.getTeamAId(), restTemplate);
        ResponseRest responseRest2 = utils. teamExist(match.getTeamBId(), restTemplate);
        Integer playerNotExistId =utils. allPlayerExist(match,restTemplate);
        Integer playerScoreExist = utils.allPlayerScoreExist(match,restTemplate);
        if (!responseRest1.isValid()){
            msg = "Fail\nTeam "+match.getTeamAId()+" doesn't not exist in Team Service";
        }else if  (!responseRest2.isValid()){
            msg = "Fail\nTeam "+match.getTeamBId()+" doesn't not exist in Team Service";
        } else if (playerNotExistId != -1){
            msg = "Fail\nPlayer "+playerNotExistId+" doesn't not exist";
        } else if(playerScoreExist != -1){
            msg = "Fail\nPlayer Score "+playerScoreExist+" doesn't not exist";
        } else if(id != match.getId()){
            msg = "Fail\nUrl ID and the body ID doesn't match";
        } else {
            Match m = matchData.get(id);
            if (m == null) {
                msg = "ID doesn't exist";
            } else {
                matchData.remove(id);
                matchData.put(id, match);
            }
        }
        return  msg;
    }

    @HystrixCommand(fallbackMethod = "getPlayerAndMatchByIdFallback")
    @ApiOperation(value = "Post specific Team in the System ", response = String.class, tags = "postMatch")
    @PostMapping
    public String postMatch(@RequestBody Match match) {
        System.out.println("Posting match " + match);
        String msg = "Success";
        if(!match.areAllFieldsNotEmpty()){
            msg=  "Fail\nYour body should be like\n{id = 1, teamAId = 1, teamBId = 1, score = 2-0,playersScore=[id = 1,name=player,playerTeam = [id =1,teamName=teamA]]}";
        }else{
            ResponseRest responseRest1 = utils.teamExist(match.getTeamAId(), restTemplate);
            ResponseRest responseRest2 = utils. teamExist(match.getTeamBId(), restTemplate);
            Integer playerNotExistId =utils. allPlayerExist(match,restTemplate);
            Integer playerScoreExist = utils.allPlayerScoreExist(match,restTemplate);
            if (!responseRest1.isValid()){
                msg = "Fail\nTeam "+match.getTeamAId()+" doesn't not exist in Team Service";
            }else if  (!responseRest2.isValid()){
                msg = "Fail\nTeam "+match.getTeamBId()+" doesn't not exist in Team Service";
            } else if (playerNotExistId != -1){
                msg = "Fail\nPlayer "+playerNotExistId+" doesn't not exist";
            } else if(playerScoreExist != -1){
                msg = "Fail\nPlayer Score "+playerScoreExist+" doesn't not exist";
            } else {
                Match m = matchData.get(match.getId());
                if (m != null) {
                    msg = "ID already exist";
                } else {
                    matchData.put(match.getId(), match);
                }
            }
        }

        return msg;

    }

    @ApiOperation(value = "Get specific Match exist in the System ", response = Match.class, tags = "getMatchById")
    @GetMapping(value = "/{matchId}")
    public Match getMatchById(@PathVariable int matchId) {
        System.out.println("Getting Match by id " + matchId);
        Match match = matchData.get(matchId);
        if (match == null) {
            match = new Match(-1,-1,-1,"",false,Arrays.asList());
        }
        return match;
    }
    @ApiOperation(value = "Get specific all Match of a team exist in the System ", response = Match.class, tags = "getAllMatchByTeamId")
    @GetMapping(value = "/allMatch/{teamId}")
    public List<Match> getAllMatchByTeamId(@PathVariable int teamId) {
        List<Match> matchList = new ArrayList<Match>();
        for (Match match : matchData.values()) {
            if (match.getTeamAId() == teamId || match.getTeamBId() == teamId){
                matchList.add(match);
            }
        }

        return  matchList;

    }


    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })

    public String getPlayerAndMatchByIdFallback(@RequestBody Match match) {
        System.out.println("Player or Match Service is down!!! fallback route enabled...");
        return "Player/Match Service is down!!!";
    }

    public String getPlayerAndMatchByIdFallback2(@PathVariable int id, @RequestBody Match match) {
        System.out.println("Player or Match Service is down!!! fallback route enabled...");
        return "Player/Match Service is down!!!";
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
