package com.example.scrabble;
//[]

//{}
public class traite {
    String[][] tab;
    traite(String[][] strings){
        tab = strings;
    }
    String ajout(){
        String res = "";
        for (int i = 0;i<tab.length;i++){
            res+=tab[i][0];
        }
        return res;
    }
}
