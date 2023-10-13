package com.service.matchService.MatchService.utils;

import com.service.matchService.MatchService.model.Match;
import com.service.matchService.MatchService.model.Player;
import com.service.matchService.MatchService.model.ResponseRest;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class utils {

    public static ResponseRest teamExist(Integer id, RestTemplate restTemplate){
        String url = "http://teamService/teams/teamsExist/" + id;
        System.out.println("url response " + url);
        ResponseEntity<Map>
                response = restTemplate.exchange(url,
                HttpMethod.GET, null, Map.class);

        Map<String, Object> resp = response.getBody();
        System.out.println("Resp=========================== "+response.getBody());
        ResponseRest responseRest = new ResponseRest(resp.get("data").toString(),resp.get("msg").toString(),Boolean.parseBoolean( resp.get("valid").toString()));
        return  responseRest;
    }

    public static Integer allPlayerExist(Match match,RestTemplate restTemplate){
        int userNotExistId = -1;
        for (Player player : match.getPlayersScore()) {
            ResponseEntity<Player> response = restTemplate.exchange("http://playerService/players/" + player.getId(),
                    HttpMethod.GET, null, Player.class);
            Player player1 = response.getBody();
            if (player1 == null || player1.getId() == -1 ) {
                userNotExistId = player.getId();
            }


            System.out.println("response Player " + player1.getName());
        }


        return userNotExistId;

    }

    public static Integer allPlayerScoreExist(Match match,RestTemplate restTemplate){
        int userNotExistId = -1;
        for (Player player : match.getPlayersScore()) {
            ResponseRest responseRest = utils.teamExist(player.getPlayerTeam().getId(), restTemplate);
            if (!responseRest.isValid()){
                userNotExistId = player.getPlayerTeam().getId();
            }
        }
        return userNotExistId;
    }

}
