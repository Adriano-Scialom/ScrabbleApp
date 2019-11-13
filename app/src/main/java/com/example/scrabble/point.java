package com.example.scrabble;

import java.util.ArrayList;
//[]
//{}
public class point {
    int i;
    int j;
    point(int i,int j){
        this.i = i;
        this.j = j;
}

public static void position(ArrayList<point> liste,plateau plateau){
        int g,d,h,b;
        int dir;
        String mot="";
        point premier = liste.get(0);
        d = premier.i;
        g = premier.i;
        h = premier.j;
        b = premier.j;
        for (point p : liste){
            if (p.i<g)
                g = p.i;
            if (p.i>d)
                d = p.i;
            if (p.j<h)
                h = p.j;
            if (p.j>b)
                b = p.j;
        }

        if (Math.abs(g-d)>0){
            dir = 0;
            for (int i=g;i<=d;i++){mot+=String.valueOf(plateau.grid[i][h]);}

        }

        else{
            dir = 1;
            for (int j=h;j<=b;j++){mot+=String.valueOf(plateau.grid[g][j]);}
        }
    }
}
