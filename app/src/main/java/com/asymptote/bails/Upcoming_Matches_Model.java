package com.asymptote.bails;

public class Upcoming_Matches_Model {
    String home_team_name;
    String away_team_name;
    String home_team_url;
    String away_team_url;
    String series_name;
    String time;


    public Upcoming_Matches_Model(String home_team_name, String away_team_name,
                                  String home_team_url, String away_team_url, String series_name, String time) {
        this.home_team_name = home_team_name;
        this.away_team_name = away_team_name;
        this.home_team_url = home_team_url;
        this.away_team_url = away_team_url;
        this.series_name = series_name;
        this.time = time;
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

    public String getHome_team_url() {
        return home_team_url;
    }

    public void setHome_team_url(String home_team_url) {
        this.home_team_url = home_team_url;
    }

    public String getAway_team_url() {
        return away_team_url;
    }

    public void setAway_team_url(String away_team_url) {
        this.away_team_url = away_team_url;
    }

    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
