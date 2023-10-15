package com.service.teamService.TeamService.controllers;

import java.util.*;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.service.teamService.TeamService.model.Player;
import com.service.teamService.TeamService.model.ResponseRest;
import com.service.teamService.TeamService.model.playerTeam;
import io.micrometer.core.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.service.teamService.TeamService.model.Team;
import org.springframework.web.client.RestTemplate;

@Api(value = "Swagger2DemoRestController", description = "REST Apis related to Team Service Entity!!!!")
@RestController
@EnableCircuitBreaker
@RequestMapping(value = "/teams")
public class TeamServiceController {

    @Autowired
    RestTemplate restTemplate;
    private static final Map<Integer, Team> teamData = new HashMap<Integer, Team>() {


        {
                  put(1,  new Team(1, "Team A", Arrays.asList()));
                  put(2,  new Team(2, "Team B",Arrays.asList()));

        }

    };



    @Timed(
            value = "teamService.deleteTeam.request",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "1.0"}
    )
    @ApiOperation(value = "delete specific Team in the System ", response = String.class, tags = "deleteTeam")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })
    public String deleteTeam(@PathVariable int id){
        System.out.println("Updating by id " + id);
        String msg = "Success";
        Team team = teamData.get(id);
        if (team == null) {
            msg = "The ID does not exist";
            return msg;
        }else{
            teamData.remove(id);
            return msg;
        }
    }




    @Timed(
            value = "teamService.updateTeam.request",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "1.0"}
    )
    @ApiOperation(value = "Put specific Team in the System ", response = String.class, tags = "updateTeam")
    @HystrixCommand(fallbackMethod = "getPlayerByIdUpdateTeamFallback")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })
    public String updateTeam(@PathVariable int id, @RequestBody Map<String, Object> updateTeam) {
                System.out.println("Updating by id " + updateTeam);

            String msg = "Success";

            Team team = teamData.get(id);
        if(updateTeam.get("id") == null || updateTeam.get("name") == null ){
            msg=  "Fail\nYour body should be like\n{id = 1, name = name, players:[]}";
        }else if (team == null) {
                msg = "The ID does not exist";
        } else if (id != Integer.parseInt(updateTeam.get("id").toString())){
            msg = "The url ID doesn't match with the body ID";
        } else{
                teamData.remove(id);
                List<Player> playerList = new ArrayList<Player>();
            for (Player player : team.getPlayers()) {
                // Define the request headers and body
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                player.getPlayerTeam().setTeamName(updateTeam.get("name").toString());
                HttpEntity<Player> requestEntity = new HttpEntity<>(player, headers);

                // Make the POST request
                String url = "http://playerService/players/" + player.getId();
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

            }
            teamData.put(id, new Team(id, updateTeam.get("name").toString(), Arrays.asList()));
            }
        return msg;
    }


    @Timed(
            value = "teamService.postTeam.request",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "1.0"}
    )
    @ApiOperation(value = "Post specific Team in the System ", response = String.class, tags = "postTeam")
    @HystrixCommand(fallbackMethod = "getPlayerByIdPostTeamFallback")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })
    public String postTeam(@RequestBody Team team) {
        System.out.println("Posting Team " + team);
        if(!team.areAllFieldsNotEmpty()){
            return  "Fail\nYour body should be like\n{id = 1, name = name, players:[ id = 1, name = name, playerTeam: [id=1,teamName=TeamName]}";
        }else {
            boolean allPlayersExist = true;
            boolean allTeamExist = true;
            boolean allTeamNameCorrect = true;

            int playerNotExistID = -1;
            int teamNotExistID = -1;


            String msg = "Success";
            for (Player player : team.getPlayers()) {
                ResponseEntity<Player> response = restTemplate.exchange("http://playerService/players/" + player.getId(),
                        HttpMethod.GET, null, Player.class);
                Player player1 = response.getBody();
                if (player1 == null || player1.getId() == -1 ) {
                    allPlayersExist = false;
                    playerNotExistID = player.getId();
                }
                System.out.println("response Player " + player1.getName());
            }
            for (Player player : team.getPlayers()) {
                if (!teamData.containsKey(player.getPlayerTeam().getId())){
                    allTeamExist = false;
                    teamNotExistID = player.getPlayerTeam().getId();
                    if (allPlayersExist){
                        playerNotExistID = player.getId();
                    }
                }

            }
            for (Player player : team.getPlayers()) {
                if (teamData.containsKey(player.getPlayerTeam().getId()) && teamData.get(player.getId()).getName() != player.getPlayerTeam().getTeamName()){
                    allTeamNameCorrect = false;
                    if (allPlayersExist){
                        playerNotExistID = player.getId();
                    }
                    teamNotExistID = player.getPlayerTeam().getId();
                }

            }
            System.out.println("allPlayersExist:  " + allPlayersExist);
            if (allPlayersExist && allTeamExist && allTeamNameCorrect){

                Team t = teamData.get(team.getId());
                if (t != null) {
                    msg = "ID already exist";
                    return msg;
                } else {
                    teamData.put(team.getId(), team);
                    return msg;
                }
            }else{
                if (!allPlayersExist){
                    msg = "The Player with Id "+ playerNotExistID+ " Doesn't exist";
                }else if (!allTeamExist){
                    msg = "The Player with Id "+ playerNotExistID+ " has a wrong team id = "+ teamNotExistID;
                }else{
                    msg = "The Player with Id "+ playerNotExistID+ " and team id "+ teamNotExistID+ " has wrong team name";
                }

                    return msg;
                }


        }

    }


    @Timed(
            value = "teamService.getTeamById.request",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "1.0"}
    )
    @ApiOperation(value = "Get specific Team in the System ", response = Team.class, tags = "getStudent")
    @HystrixCommand(fallbackMethod = "getPlayerByIdGetTeamFallback")
    @GetMapping(value = "/{teamId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })
    public Team getTeamById(@PathVariable int teamId) {
        System.out.println("Getting Team by id " + teamId);
        Team team = teamData.get(teamId);
        if (team == null) {
            team = new Team(-1, "N/A",Arrays.asList());
        }
        ParameterizedTypeReference<List<Player>> responseType = new ParameterizedTypeReference<List<Player>>() {};
        ResponseEntity<List<Player>> response = restTemplate.exchange(
                "http://playerService/players/getPlayerByTeamId/" + team.getId(),
                HttpMethod.GET,
                null,
                responseType
        );

        List<Player> players = response.getBody();
        team.setPlayers(players);
        System.out.println("Getting Team by response " + players.toString());
        return team;
    }

    @Timed(
            value = "teamService.getTeamExistById.request",
            histogram = true,
            percentiles = {0.95, 0.99},
            extraTags = {"version", "1.0"}
    )
    @ApiOperation(value = "Get if Team exist in the System ", response = Team.class, tags = "getTeamExistById")
    @GetMapping(value = "/teamsExist/{teamId}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })
    public ResponseRest getTeamExistById(@PathVariable int teamId) {
        System.out.println("Getting Team by id " + teamId);
        Team team = teamData.get(teamId);
        if (team == null) {
            return new ResponseRest("Empty","Team doesn't exist",false);
        }else{
            return  new ResponseRest(team.getName(),"Team  exist",true);
        }
    }



    public String getPlayerByIdUpdateTeamFallback(int id, @RequestBody Map<String, Object> updateTeam) {
        System.out.println("Player or Match Service is down!!! fallback route enabled...");
        return "Player Service is down!!!";
    }
    public String getPlayerByIdPostTeamFallback(@RequestBody Team team) {
        System.out.println("Player or Match Service is down!!! fallback route enabled...");
        return "Player Service is down!!!";
    }
    public Team getPlayerByIdGetTeamFallback(int teamId){
        System.out.println("Player or Match Service is down!!! fallback route enabled...");
        return new Team(-1,"Player Service is down!!!", new ArrayList<>());
    }
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
