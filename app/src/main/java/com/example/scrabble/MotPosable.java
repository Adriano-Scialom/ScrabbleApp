package com.example.scrabble;

public class MotPosable extends MotComplet {
    int i;
    int j;
    int dir;
    MotPosable(String letters, int pos, int i, int j, int dir) {
        super(letters, pos);
        this.i = i;
        this.j = j;
        this.dir = dir;
    }
    public String toString() {
        return lettres+" "+String.valueOf(i)+" "+String.valueOf(j)+" "+String.valueOf(dir)+ " "+String.valueOf(pos) ;
    }
    //Used in points
    int comptemotancien(Board plat, int dir, int i, int j) {
        int mult = Board.mul(i,j);
        boolean motReel = false;
        int valeur =val(i,j,lettres.charAt(pos),true);

        if(dir==1) {
            //horizontal
            int g = j-1;
            int d = j+1;
            while(g>=0 && (int)plat.grid[i][g]!=0) {
                valeur+=val(i,g,plat.grid[i][g],false);
                g-=1;
                motReel = true;}
            while(d<15 && (int)plat.grid[i][d]!=0){
                valeur+=val(i,d,plat.grid[i][d],false);
                d+=1;
                motReel = true;}

        }

        else
        {
            //vertical
            int b = i-1;
            int h = i+1;

            while(b>=0 && (int)plat.grid[b][j]!=0) {
                valeur+=val(b,j,plat.grid[b][j],false);
                b-=1;
                motReel = true;}
            while(h<15 && (int)plat.grid[h][j]!=0){
                valeur+=val(h,j,plat.grid[h][j],false);
                h+=1;
                motReel = true;}
        }

        if (motReel)
            return valeur*mult;
        else
            return 0;


    }
    //Used in points
    int comptemotnouveau() {
        int mult = 1;
        int valeur = 0;
        if(dir==1) {
            //horizontal
            for (int a = -pos+j; a<j-pos+lettres.length();a++){
                valeur+=val(i,a,lettres.charAt(a+pos-j),true);
                mult*= Board.mul(i, a);
            }
        }
        else
        {
            //vertical
            for (int b = -pos+i; b<i-pos+lettres.length();b++){
                valeur+=val(b,j,lettres.charAt(b+pos-i),true);
                mult*= Board.mul(b, j);
            }
        }

        return valeur*mult;
    }
    //Gives the value of the letter c posed at the coordinates (i,j) on the board (accounts for multipliers)
    int val(int i, int j, char c, boolean nouveau){
        int cn = ((int)c)-96;
        int valeur = 0;
        switch(cn){
            case 1:
                valeur = 1;
                break;
            case 2:
                valeur = 3;
                break;
            case 3:
                valeur = 3;
                break;
            case 4:
                valeur = 2;
                break;
            case 5:
                valeur = 1;
                break;
            case 6:
                valeur = 4;
                break;
            case 7:
                valeur = 2;
                break;
            case 8:
                valeur = 4;
                break;
            case 9:
                valeur = 1;
                break;
            case 10:
                valeur = 8;
                break;
            case 11:
                valeur = 10;
                break;
            case 12:
                valeur = 1;
                break;
            case 13:
                valeur = 2;
                break;
            case 14:
                valeur = 1;
                break;
            case 15:
                valeur = 1;
                break;
            case 16:
                valeur = 3;
                break;
            case 17:
                valeur = 8;
                break;
            case 18:
                valeur = 1;
                break;
            case 19:
                valeur = 1;
                break;
            case 20:
                valeur = 1;
                break;
            case 21:
                valeur = 1;
                break;
            case 22:
                valeur = 4;
                break;
            case 23:
                valeur = 10;
                break;
            case 24:
                valeur = 10;
                break;
            case 25:
                valeur = 10;
                break;
            case 26:
                valeur = 10;
                break;
        }
        int mult = 1;
        if (i>7)
            i = 14-i;
        if (j>7)
            j = 14-j;
        int comb = 10*i+j;
        switch(comb){
            case 30:
                mult = 2;
                break;
            case 3:
                mult = 2;
                break;
            case 62:
                mult = 2;
                break;
            case 26:
                mult = 2;
                break;
            case 73:
                mult = 2;
                break;
            case 66:
                mult = 2;
                break;
            case 51:
                mult = 3;
                break;
            case 15:
                mult = 3;
                break;
            case 55:
                mult = 3;
                break;
        }

        if (nouveau)
            return valeur*mult;
        else
            return valeur;



    }
    //{}

    //Counts the number of points made by playing the word MotPosable on the board
    int points(Board board){
        int res = 0;
        if (lettres.length()==8)
            res = 50;
        if(dir==1){
            res+=comptemotnouveau();
            for (int a = -pos+j; a<j-pos+lettres.length();a++){
                res+= comptemotancien(board,0,i,a);
            }
        }
        else
        {
            res+=comptemotnouveau();
            for (int b = -pos+i; b<i-pos+lettres.length();b++){
                res+= comptemotancien(board,1,b,j);
            }
        }
        return res;

    }

}
