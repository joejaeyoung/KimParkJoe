package com.example.kimparkjoe;

public class ANSItemList {
    private String Eng;
    private String Kor;

    public ANSItemList() {
        
    }

    public ANSItemList(String eng, String kor) {
        Eng = eng;
        Kor = kor;
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
}
