package com.example.kimparkjoe;

public class RankingItemList {
    private int score;
    private String name;

    public RankingItemList(){

    }

    public RankingItemList(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){ return name;}

    public void setName(String name) {
        this.name = name;
    }

    public int getScore(){return score;}

    public void setScore(int score) {
        this.score = score;
    }

}
