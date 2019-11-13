package com.example.scrabble;
//[]

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

//{}
public class Position {
    int x;
    int y;
    char lettre;
    Position(int x,int y,char lettre){
        this.x = x;
        this.y = y;
        this.lettre = lettre;}
    static motposable motMis(ArrayList<Position> changements,plateau plateau) throws Exception{
        int gauche,droite,haut,bas,dir;
        motposable motposable;
        if (changements.size()>1){
        gauche = 14;
        droite = 0;
        bas = 0;
        haut = 14;
        for (Position pos : changements)
        {
            if (pos.x<gauche)
                gauche = pos.x;
            if(pos.x>droite)
                droite = pos.x;
            if (pos.y > bas)
                bas = pos.y;
            if (pos.y < haut)
                haut = pos.y;
        }
        boolean[] present = new boolean[15];
        boolean extremite = false;
        int extrem1,extrem2;
        if (bas-haut>0){
            dir = 1;
            if (changements.size()==bas-haut+1)
                extremite = true;
            extrem1 = haut;
            extrem2 = bas;
             }
        else{
            dir = 0;
            if (changements.size()==droite-gauche+1)
                extremite = true;
            extrem1 = gauche;
            extrem2 = droite;
        }

        //On cherche si il y a un trou ou si le raccrochement est à une extremité
            if (!extremite){
                char[] lettresmot = new char[15];
                for (int i = 0; i < 15; i++) {
                    present[i] = false;
                    lettresmot[i] = 0;
            }
            int manquant = 0;


            for (Position pos : changements){
                if (dir ==1){
                    present[pos.y] = true;
                    lettresmot[pos.y] = pos.lettre;
                }
                else{
                    present[pos.x] = true;
                    lettresmot[pos.x] = pos.lettre;
                }
            }
            for (int i = extrem1; i < extrem2; i++) {
                if (!present[i]){
                    manquant = i;
                    if (dir==1)
                        lettresmot[i] = plateau.grid[gauche][i];
                    else
                        lettresmot[i] = plateau.grid[i][haut];
                    break;}
            }

            if (dir==1)
                motposable = new motposable(String.copyValueOf(Arrays.copyOfRange(lettresmot,haut,bas+1)),manquant-extrem1,gauche,manquant,dir);
            else
                motposable = new motposable(String.copyValueOf(Arrays.copyOfRange(lettresmot,gauche,droite+1)),manquant-extrem1,manquant,haut,dir);
            return motposable;

            }
            else{
                int manquant = 0;
                char[] lettresmot = new char[15];
                for (int i = 0; i < 15; i++) {
                    lettresmot[i] = 0;
                }
                for (Position pos : changements){
                    if (dir ==1){
                        lettresmot[pos.y] = pos.lettre;
                    }
                    else{
                        lettresmot[pos.x] = pos.lettre;
                    }
                }

                if (dir==1){
                    try{
                    if (plateau.grid[gauche][haut-1]!=0){
                        lettresmot[haut-1] = plateau.grid[gauche][haut-1];
                        manquant = haut-1;
                    }}
                    catch (Exception e){}
                    try{
                    if (plateau.grid[gauche][bas+1]!=0){
                        lettresmot[bas+1] = plateau.grid[gauche][bas+1];
                        manquant = bas + 1;
                    }}
                    catch (Exception e){}
                    motposable = new motposable(nettoyer(lettresmot),manquant-haut,gauche,manquant,dir);
                }
                else{
                    try{
                    if (plateau.grid[gauche-1][haut]!=0){
                        lettresmot[gauche-1] = plateau.grid[gauche-1][haut];
                        manquant = gauche - 1;
                    }}
                    catch (Exception e){}
                    try{
                    if (plateau.grid[droite+1][bas]!=0){
                        lettresmot[droite+1] = plateau.grid[droite+1][bas];
                        manquant = droite + 1;
                    }}
                    catch (Exception e){}
                    motposable = new motposable(nettoyer(lettresmot),manquant-gauche,manquant,gauche,dir);

                }
                return motposable;
            }
        }
        else if (changements.size()==1){
            gauche = droite = changements.get(0).x;
            haut = bas = changements.get(0).y;
            if (plateau.grid[gauche-1][haut]!=0)
                motposable = new motposable(String.valueOf(plateau.grid[gauche-1][haut])+String.valueOf(changements.get(0).lettre),0,gauche-1,haut,0);
            else if (plateau.grid[droite+1][haut]!=0)
                motposable = new motposable(String.valueOf(changements.get(0).lettre)+String.valueOf(plateau.grid[droite+1][haut]),1,droite+1,haut,0);
            else if (plateau.grid[gauche][haut-1]!=0)
                motposable = new motposable(String.valueOf(plateau.grid[gauche][haut-1])+String.valueOf(changements.get(0).lettre),0,gauche,haut-1,0);
            else
                motposable = new motposable(String.valueOf(changements.get(0).lettre)+String.valueOf(plateau.grid[droite][haut+1]),1,droite,haut+1,0);

            return motposable;
        }
        else
            throw new Exception();
    }

    static String nettoyer (char[] lettres){
        String res = "";
        for (char lettre : lettres) {
            if (!(lettre==(char)0)){res+=String.valueOf(lettre);}
        }
    return res;
    }
}
//{}
//[]