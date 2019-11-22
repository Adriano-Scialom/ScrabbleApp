package com.example.scrabble;

public class MotComplet {

    String lettres;
    int pos;
    MotComplet(String lettres, int pos){
        this.lettres = lettres;
        this.pos = pos;
    }
    public String toString() {
        return lettres;
    }
}