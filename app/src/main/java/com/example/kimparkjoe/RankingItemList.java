package com.example.kimparkjoe;

import java.util.Comparator;

public class RankingItemList implements Comparable<RankingItemList>{
    public String name;
    public int score;

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

    @Override
    public int compareTo(RankingItemList rankingItemList) {
        if (rankingItemList.getScore() < score) {
            return 1;
        } else if (rankingItemList.getScore() > score) {
            return -1;
        }
        return 0;
    }
}
