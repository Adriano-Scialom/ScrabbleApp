package com.example.scrabble;
//[]

import java.util.ArrayList;
import java.util.Arrays;

//{}
public class Position {
    int x;
    int y;
    char letter;
    Position(int x,int y,char letter){
        this.x = x;
        this.y = y;
        this.letter = letter;}

    //Finds the word put in place by the player by taking as argument the modifications in the board
    static MotPosable motMis(ArrayList<Position> changements, Board board) throws Exception{
        int left,right,up,down,dir;
        MotPosable motposable;
        if (changements.size()>1){
        left = 14;
        right = 0;
        down = 0;
        up = 14;
        for (Position pos : changements)
        {
            if (pos.x<left)
                left = pos.x;
            if(pos.x>right)
                right = pos.x;
            if (pos.y > down)
                down = pos.y;
            if (pos.y < up)
                up = pos.y;
        }
        boolean[] present = new boolean[15];
        boolean extremite = false;
        int extrem1,extrem2;
        if (down-up>0){
            dir = 1;
            if (changements.size()==down-up+1)
                extremite = true;
            extrem1 = up;
            extrem2 = down;
             }
        else{
            dir = 0;
            if (changements.size()==right-left+1)
                extremite = true;
            extrem1 = left;
            extrem2 = right;
        }

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
                    lettresmot[pos.y] = pos.letter;
                }
                else{
                    present[pos.x] = true;
                    lettresmot[pos.x] = pos.letter;
                }
            }
            for (int i = extrem1; i < extrem2; i++) {
                if (!present[i]){
                    manquant = i;
                    if (dir==1)
                        lettresmot[i] = board.grid[left][i];
                    else
                        lettresmot[i] = board.grid[i][up];
                    break;}
            }

            if (dir==1)
                motposable = new MotPosable(String.copyValueOf(Arrays.copyOfRange(lettresmot,up,down+1)),manquant-extrem1,left,manquant,dir);
            else
                motposable = new MotPosable(String.copyValueOf(Arrays.copyOfRange(lettresmot,left,right+1)),manquant-extrem1,manquant,up,dir);
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
                        lettresmot[pos.y] = pos.letter;
                    }
                    else{
                        lettresmot[pos.x] = pos.letter;
                    }
                }

                if (dir==1){
                    try{
                    if (board.grid[left][up-1]!=0){
                        lettresmot[up-1] = board.grid[left][up-1];
                        manquant = up-1;
                    }}
                    catch (Exception e){}
                    try{
                    if (board.grid[left][down+1]!=0){
                        lettresmot[down+1] = board.grid[left][down+1];
                        manquant = down + 1;
                    }}
                    catch (Exception e){}
                    motposable = new MotPosable(nettoyer(lettresmot),manquant-up,left,manquant,dir);
                }
                else{
                    try{
                    if (board.grid[left-1][up]!=0){
                        lettresmot[left-1] = board.grid[left-1][up];
                        manquant = left - 1;
                    }}
                    catch (Exception e){}
                    try{
                    if (board.grid[right+1][down]!=0){
                        lettresmot[right+1] = board.grid[right+1][down];
                        manquant = right + 1;
                    }}
                    catch (Exception e){}
                    motposable = new MotPosable(nettoyer(lettresmot),manquant-left,manquant,left,dir);

                }
                return motposable;
            }
        }
        else if (changements.size()==1){
            left = right = changements.get(0).x;
            up = down = changements.get(0).y;
            if (board.grid[left-1][up]!=0)
                motposable = new MotPosable(String.valueOf(board.grid[left-1][up])+String.valueOf(changements.get(0).letter),0,left-1,up,0);
            else if (board.grid[right+1][up]!=0)
                motposable = new MotPosable(String.valueOf(changements.get(0).letter)+String.valueOf(board.grid[right+1][up]),1,right+1,up,0);
            else if (board.grid[left][up-1]!=0)
                motposable = new MotPosable(String.valueOf(board.grid[left][up-1])+String.valueOf(changements.get(0).letter),0,left,up-1,0);
            else
                motposable = new MotPosable(String.valueOf(changements.get(0).letter)+String.valueOf(board.grid[right][up+1]),1,right,up+1,0);

            return motposable;
        }
        else
            throw new Exception();
    }
    //Return a String from the char array letters removing the chars equals to 0
    static String nettoyer (char[] letters){
        String res = "";
        for (char letter : letters) {
            if (!(letter==(char)0)){res+=String.valueOf(letter);}
        }
    return res;
    }
}
//{}
//[]