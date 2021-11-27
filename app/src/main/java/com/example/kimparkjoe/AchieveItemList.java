package com.example.kimparkjoe;

public class AchieveItemList {
    private String achievement;
    private Boolean check;

    public AchieveItemList() {

    }

    public AchieveItemList(String achievement, Boolean check) {
        this.achievement = achievement;
        this.check = check;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public Boolean getCheck() {
        return check;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }
}
