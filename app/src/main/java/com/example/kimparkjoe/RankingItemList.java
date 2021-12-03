package com.example.kimparkjoe;

public class RankingItemList {
    private String name;
    private int score;
    private int isFriend; //1이면 친구 0이면 ㄴㄴ

    public RankingItemList(){

    }

    public RankingItemList(String name, int score, int isFriend){
        this.name = name;
        this.score = score;
        this.isFriend = isFriend;
    }

    public String getName(){ return name;}
    public int getScore(){return score;}
    public int getIsFriend(){return isFriend;}
}
