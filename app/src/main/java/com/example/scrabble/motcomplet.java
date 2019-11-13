package com.example.scrabble;

public class motcomplet {

    String lettres;
    int pos;
    motcomplet(String lettres,int pos){
        this.lettres = lettres;
        this.pos = pos;
    }
    public String toString() {
        return lettres;
    }
}