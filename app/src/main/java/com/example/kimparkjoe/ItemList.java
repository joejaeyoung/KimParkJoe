package com.example.kimparkjoe;

import com.google.firebase.database.Exclude;

import java.util.Map;
import java.util.TreeMap;

public class ItemList {
    private String Eng;
    private String Kor;

    public ItemList() {
        
    }

    public String getEng() {
        return Eng;
    }

    public void setEng(String eng) {
        Eng = eng;
    }

    public String getKor() {
        return Kor;
    }

    public void setKor(String kor) {
        Kor = kor;
    }

    @Exclude
    public Map<String, String> makeMap() {
        TreeMap<String, String> wordMap = new TreeMap<>();

        wordMap.put(Eng, Kor);

        return wordMap;
    }
}
