package com.service.statsService.StatsService.model;
import io.swagger.annotations.ApiModelProperty;

public class Player {

    public Player() {

    }
    public Player(Integer id, String name,playerTeam playerTeam) {
        super();
        this.id = id;
        this.name = name;
        this.playerTeam = playerTeam;
    }

    @ApiModelProperty(notes = "Id of the Player",name="id",required=true,value="1 Id")
    private Integer id;
    @ApiModelProperty(notes = "name of the Player",name="name",required=true,value="test test")
    private String name;

    @ApiModelProperty(notes = "playerTeam of the Player",name="playerTeam",required=true,value="test test")
    private playerTeam playerTeam;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public playerTeam getPlayerTeam() {
        return playerTeam;
    }


    public boolean areAllFieldsNotEmpty() {
        return id != null && name != null && !name.isEmpty() && playerTeam != null && playerTeam.areAllFieldsNotEmpty();
    }


    @Override
    public String toString() {
        return "Player [id=" + id + ", name=" + name+ ", PlayerTeam="+playerTeam.toString()+"]";
    }
}
