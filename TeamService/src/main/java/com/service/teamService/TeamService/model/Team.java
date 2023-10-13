package com.service.teamService.TeamService.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class Team {

    public Team() {

    }
    public Team(Integer id, String name,List<Player> players) {
        super();
        this.id = id;
        this.name = name;
        this.players = players;
    }

    @ApiModelProperty(notes = "Id of the Team",name="id",required=true,value="1 Id")
    private Integer id;
    @ApiModelProperty(notes = "name of the Team",name="name",required=true,value="test test")

    private String name;

    @ApiModelProperty(notes = "List of player in the Team",name="players",required=true,value="id = 1, name = name")

    private List<Player> players;

    public List<Player> getPlayers(){
        return  players;
    }

    public void setPlayers(List<Player> players){
         this.players=players;
    }

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
    public boolean areAllFieldsNotEmpty() {
        return id != null && name != null && !name.isEmpty() && players != null && !players.isEmpty();
    }

    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name+ " players =[" +players.toString() +"]\n] ";
    }
}
