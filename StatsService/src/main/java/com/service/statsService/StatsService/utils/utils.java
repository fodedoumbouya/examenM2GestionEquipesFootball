package com.service.statsService.StatsService.utils;

import com.service.statsService.StatsService.model.Match;
import com.service.statsService.StatsService.model.Player;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class utils {

    public static List<Match> getAllMatchByTeamId(int teamId, RestTemplate restTemplate){
        ParameterizedTypeReference<List<Match>> responseType = new ParameterizedTypeReference<List<Match>>() {};
        ResponseEntity<List<Match>> response =restTemplate.exchange("http://matchService/match/allMatch/" + teamId,
                HttpMethod.GET,
                null,
                responseType
        );

        List<Match> listMatch = response.getBody();

        return  listMatch;
    }


    public static Player getPlayerById(int playerId,RestTemplate restTemplate){
        ResponseEntity<Player> response = restTemplate.exchange("http://playerService/players/" + playerId,
                HttpMethod.GET, null, Player.class);
        Player player = response.getBody();

        return  player;
    }
}
