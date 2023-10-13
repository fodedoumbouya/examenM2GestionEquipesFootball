package com.service.statsService.StatsService.model;
import io.swagger.annotations.ApiModelProperty;

import io.swagger.annotations.ApiModelProperty;

public class StatsPlayer {
    @ApiModelProperty(notes = "Player's name", name = "playerName", required = true, value = "Name of the player")
    private String playerName;

    @ApiModelProperty(notes = "Number of matches played", name = "nbrMatch", required = true, value = "Number of matches played")
    private int nbrMatch;

    @ApiModelProperty(notes = "Number of goals scored", name = "nbrGoal", required = true, value = "Number of goals scored")
    private int nbrGoal;

    public StatsPlayer(String playerName, int nbrMatch, int nbrGoal) {
        this.playerName = playerName;
        this.nbrMatch = nbrMatch;
        this.nbrGoal = nbrGoal;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getNbrMatch() {
        return nbrMatch;
    }

    public void setNbrMatch(int nbrMatch) {
        this.nbrMatch = nbrMatch;
    }

    public int getNbrGoal() {
        return nbrGoal;
    }

    public void setNbrGoal(int nbrGoal) {
        this.nbrGoal = nbrGoal;
    }

    @Override
    public String toString() {
        return "PlayerStatistics{" +
                "playerName='" + playerName + '\'' +
                ", nbrMatch=" + nbrMatch +
                ", nbrGoal=" + nbrGoal +
                '}';
    }
}
