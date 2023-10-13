package com.service.matchService.MatchService.model;



import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class Match {

    public Match() {

    }
    public Match(Integer id,Integer teamAId,Integer teamBId,String score,List<Integer> playersScore) {
        super();
        this.id = id;
        this.teamAId = teamAId;
        this.teamBId = teamBId;
        this.score = score;
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

    @ApiModelProperty(notes = "playersScore of the Match",name="playersScore",required=true,value="[1,2]")
    private List<Integer> playersScore;


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

    public void setTeamBId(Integer teamBId) {
        this.teamBId = teamBId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<Integer> getPlayersScore() {
        return playersScore;
    }

    public void setPlayersScore(List<Integer> playersScore) {
        this.playersScore = playersScore;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", teamAId=" + teamAId +
                ", teamBId=" + teamBId +
                ", score='" + score + '\'' +
                ", playersScore=" + playersScore +
                '}';
    }
}
