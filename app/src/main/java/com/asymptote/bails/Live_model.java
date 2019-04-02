package com.asymptote.bails;

public class Live_model {

    String match_name;
    String team_batting;
    String home_score;
    String away_score;
    String home_team_name;
    String away_team_name;

    public Live_model(String match_name, String team_batting, String home_score,
                      String away_score, String home_team_name, String away_team_name) {
        this.match_name = match_name;
        this.team_batting = team_batting;
        this.home_score = home_score;
        this.away_score = away_score;
        this.home_team_name = home_team_name;
        this.away_team_name = away_team_name;
    }

    public String getMatch_name() {
        return match_name;
    }

    public void setMatch_name(String match_name) {
        this.match_name = match_name;
    }

    public String getTeam_batting() {
        return team_batting;
    }

    public void setTeam_batting(String team_batting) {
        this.team_batting = team_batting;
    }

    public String getHome_score() {
        return home_score;
    }

    public void setHome_score(String home_score) {
        this.home_score = home_score;
    }

    public String getAway_score() {
        return away_score;
    }

    public void setAway_score(String away_score) {
        this.away_score = away_score;
    }

    public String getHome_team_name() {
        return home_team_name;
    }

    public void setHome_team_name(String home_team_name) {
        this.home_team_name = home_team_name;
    }

    public String getAway_team_name() {
        return away_team_name;
    }

    public void setAway_team_name(String away_team_name) {
        this.away_team_name = away_team_name;
    }
}
