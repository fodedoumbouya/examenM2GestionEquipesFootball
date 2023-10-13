package com.service.matchService.MatchService.model;

public class playerTeam {
    private Integer id;
    private String teamName;

    public playerTeam() {
        // Default constructor
    }

    public playerTeam(Integer id, String teamName) {
        this.id = id;
        this.teamName = teamName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean areAllFieldsNotEmpty() {
        return id != null && teamName != null && !teamName.isEmpty();
    }
    @Override
    public String toString() {
        return "PlayerTeam{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
