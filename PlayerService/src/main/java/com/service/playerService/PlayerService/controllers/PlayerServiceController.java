package com.service.playerService.PlayerService.controllers;

import com.service.playerService.PlayerService.model.Player;
import com.service.playerService.PlayerService.model.ResponseRest;
import com.service.playerService.PlayerService.model.Team;
import com.service.playerService.PlayerService.model.playerTeam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Api(value = "Swagger2DemoRestController", description = "REST Apis related to Player Service Entity!!!!")
@RestController
@RequestMapping(value = "/players")
public class PlayerServiceController {
    @Autowired
    RestTemplate restTemplate;


    private static final Map<Integer, Player> playerData = new HashMap<Integer, Player>() {

        {
            put(1,  new Player(1, "player A",new playerTeam(1,"Team A")));
            put(2,  new Player(2, "player B",new playerTeam(1,"Team A")));
        }

    };

    @ApiOperation(value = "delete specific Player in the System ", response = String.class, tags = "deletePlayer")
    @DeleteMapping("/{id}")
    public String deletePlayer(@PathVariable int id){
        System.out.println("Updating by id " + id);
        String msg = "Success";
        Player player = playerData.get(id);
        if (player == null) {
            msg = "The ID does not exist";
            return msg;
        }else{
            playerData.remove(id);
            return msg;
        }
    }

    @ApiOperation(value = "Put specific Player in the System ", response = String.class, tags = "updatePlayer")
    @PutMapping("/{id}")
    public String updatePlayer(@PathVariable int id, @RequestBody Player updatePlayer) {
        System.out.println("Updating by id " + updatePlayer.toString());
        String msg = "Success";
        Player player = playerData.get(id);
        if(!player.areAllFieldsNotEmpty() || !updatePlayer.areAllFieldsNotEmpty() || updatePlayer.getPlayerTeam() == null || !updatePlayer.getPlayerTeam().areAllFieldsNotEmpty() ){
            return  "Fail\nYour body should be like\n{id = 1, name = name, playerTeam = {id = 1, teamName = Team A}";
        }else if (player == null) {
            msg = "The ID does not exist";
            return msg;
        }else{


            playerData.remove(id);
            playerData.put(id, new Player(id, updatePlayer.getName(), updatePlayer.getPlayerTeam()));
            return msg;
        }
    }
    @ApiOperation(value = "Post specific Player in the System ", response = String.class, tags = "postPlayer")
    @PostMapping
    public String postPlayer(@RequestBody Player player) {
        System.out.println("Posting player " + player);

        if(player.getId() == null || player.getName() == null || player.getPlayerTeam() == null ){
            return  "Fail\nYour body should be like\n{id = 1, name = name}";
        }else{

            Player t = playerData.get(player.getId());
            String msg = "Success";
            if (t != null) {
                msg = "ID already exist";
                return  msg;
            }else{
                String url = "http://teamService/teams/teamsExist/" + player.getPlayerTeam().getId();
                System.out.println("url response " + url);
            ResponseEntity<Map>
                response = restTemplate.exchange(url,
                HttpMethod.GET, null, Map.class);

                Map<String, Object> resp = response.getBody();

                ResponseRest responseRest = new ResponseRest(resp.get("data").toString(),resp.get("msg").toString(),Boolean.parseBoolean( resp.get("valid").toString()));

        if (!responseRest.isValid()||responseRest.getData() == "Empty"){
            msg = "Fail\nThe Team doesn't exist";
        }else{
            player.getPlayerTeam().setTeamName(responseRest.getData());
            playerData.put(player.getId(),player);
        }
                return  msg;
            }

        }

    }

    @ApiOperation(value = "Get specific Player in the System ", response = Player.class, tags = "getPlayerById")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!")
    })
    @GetMapping(value = "/{playerId}")
    public Player getPlayerById(@PathVariable int playerId) {
        System.out.println("Getting Player by id " + playerId);
        Player player = playerData.get(playerId);
        if (player == null) {
            player = new Player(-1, "N/A", new playerTeam(-1,"N/A"));
        }
//
        return player;
    }

    @ApiOperation(value = "Get specific Player in the System ", response = Player.class, tags = "getPlayerByTeamId")
    @GetMapping(value = "/getPlayerByTeamId/{teamId}")
    public List<Player> getPlayerByTeamId(@PathVariable int teamId) {
        System.out.println("Getting Player by teamId " + teamId);
        List<Player> playerList = new ArrayList<Player>();
        for (Player player : playerData.values()) {
            if (player.getPlayerTeam().getId() == teamId){
                playerList.add(player);
            }
        }
        return  playerList;
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
