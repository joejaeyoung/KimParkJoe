package com.example.kimparkjoe;

import com.google.firebase.database.Exclude;

import java.util.Map;
import java.util.TreeMap;

public class WordItemList {
    private String Eng;
    private String Kor;
    private String part;

    public WordItemList() {
        
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

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }
}
