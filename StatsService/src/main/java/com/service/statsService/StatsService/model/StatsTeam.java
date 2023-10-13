package com.service.statsService.StatsService.model;
import io.swagger.annotations.ApiModelProperty;

public class StatsTeam {
    @ApiModelProperty(notes = "Team Name", name = "teamName", required = true, value = "team Name")
    private String teamName;
    @ApiModelProperty(notes = "The number of the match", name = "nbrMatch", required = true, value = "Number of match")
    private Integer nbrMatch;

    @ApiModelProperty(notes = "The number of the winner match", name = "nbrWins", required = true, value = "Number of winner match")
    private Integer nbrWins;

    @ApiModelProperty(notes = "The number of the losing match", name = "nbrLoses", required = true, value = "Number of loses match")
    private Integer nbrLoses;

    public StatsTeam(Integer nbrMatch, Integer nbrWins, Integer nbrLoses, String teamName) {
        this.teamName= teamName;
        this.nbrMatch = nbrMatch;
        this.nbrWins = nbrWins;
        this.nbrLoses = nbrLoses;

    }

    public String getTeamName(){
        return  teamName;
    }

    public Integer getNbrMatch() {
        return nbrMatch;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setNbrMatch(Integer nbrMatch) {
        this.nbrMatch = nbrMatch;
    }

    public Integer getNbrWins() {
        return nbrWins;
    }

    public void setNbrWins(Integer nbrWins) {
        this.nbrWins = nbrWins;
    }

    public Integer getNbrLoses() {
        return nbrLoses;
    }

    public void setNbrLoses(Integer nbrLoses) {
        this.nbrLoses = nbrLoses;
    }

    @Override
    public String toString() {
        return "MatchStatistics{" +
                "nbrMatch=" + nbrMatch +
                ", nbrWins=" + nbrWins +
                ", nbrLoses=" + nbrLoses +
                '}';
    }

            }
