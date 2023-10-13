package com.service.statsService.StatsService.model;




import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class Match {

    public Match() {

    }
    public Match(Integer id,Integer teamAId,Integer teamBId,String score,boolean teamAWin,List<Player> playersScore) {
        super();
        this.id = id;
        this.teamAId = teamAId;
        this.teamBId = teamBId;
        this.score = score;
        this.teamAWin = teamAWin;
        this.playersScore = playersScore;
    }

    @ApiModelProperty(notes = "Id of the Match",name="id",required=true,value="1 Id")
    private Integer id;

    @ApiModelProperty(notes = "Team A Id of the Match",name="teamAId",required=true,value="test test")
    private Integer teamAId;

    @ApiModelProperty(notes = "Team B Id of the Match",name="teamBId",required=true,value="test test")
    private Integer teamBId;

    @ApiModelProperty(notes = "score of the Match",name="score",required=true,value="test test")
    private String score;
    @ApiModelProperty(notes = "winner of the Match",name="score",required=true,value="test test")
    private boolean teamAWin;
    @ApiModelProperty(notes = "playersScore of the Match",name="playersScore",required=true,value="[1,2]")
    private List<Player> playersScore;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTeamAId() {
        return teamAId;
    }

    public void setTeamAId(Integer teamAId) {
        this.teamAId = teamAId;
    }

    public Integer getTeamBId() {
        return teamBId;
    }

    public boolean getTeamAWin(){return  teamAWin;}

    public  void setTeamAWin(boolean teamAWin){this.teamAWin = teamAWin; }
    public void setTeamBId(Integer teamBId) {
        this.teamBId = teamBId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<Player> getPlayersScore() {
        return playersScore;
    }

    public void setPlayersScore(List<Player> playersScore) {
        this.playersScore = playersScore;
    }

    public boolean areAllFieldsNotEmpty() {
        return id != null && teamAId != null && teamBId != null && !score.isEmpty() && playersScore != null && !playersScore.isEmpty();
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", teamAId=" + teamAId +
                ", teamBId=" + teamBId +
                ", score='" + score  +
                ", teamAWin='"+teamAWin + '\'' +
                ", playersScore=" + playersScore +
                '}';
    }
}
