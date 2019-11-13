package com.example.scrabble;


import java.io.IOException;
import java.util.ArrayList;
//{}
public class plateau {
    char[][] grid;
    plateau(){
        grid = new char[15][15];
    }
    plateau(char[][] plat){
        grid = plat;
    }
    ArrayList<motposable> faisable(ArrayList<motcomplet> liste,dico dictio){
        ArrayList<motposable> res = new ArrayList<motposable>();
        for(int i = 0;i<15;i++)
            for(int j = 0;j<15;j++)
                if ((int)grid[i][j]!=0)
                    res.addAll(rentre(liste,i,j,dictio));
        return res;
    }

    ArrayList<motposable> rentre(ArrayList<motcomplet> liste ,int i,int j,dico dictio) {
        ArrayList<motposable> res = new ArrayList<motposable>();

        for (motcomplet m:liste) {

            if (m.lettres.charAt(m.pos)==grid[i][j]) {
                int n = m.lettres.length();

                boolean passe = true;
                try {
                    for (int a = i-m.pos;a<i+n-m.pos;a++) {
                        if (a!=i && (int)grid[a][j]!=0)
                            passe = false;
                    }
                }
                catch(Exception e){passe = false;}
                if (i-m.pos-1>=0 && (int)grid[i-m.pos-1][j]!=0)
                    passe = false;
                if (i+n-m.pos<15 && (int)grid[i+n-m.pos][j]!=0)
                    passe = false;
                if(passe && verifemplacement(i,j,m,0,dictio))
                    res.add(new motposable(m.lettres,m.pos,i,j,0));

                passe = true;
                try {
                    for (int b = j-m.pos;b<j+n-m.pos;b++) {
                        if (b!=j && (int)grid[i][b]!=0)
                            passe = false;
                    }
                }
                catch(Exception e){passe = false;}
                if (j-m.pos-1>=0 && (int)grid[i][j-m.pos-1]!=0)
                    passe = false;
                if (j+n-m.pos<15 && (int)grid[i][j+n-m.pos]!=0)
                    passe = false;
                if(passe && verifemplacement(i,j,m,1,dictio))
                    res.add(new motposable(m.lettres,m.pos,i,j,1));


            }

        }



        return res;
    }

    public boolean bon_mot(motcomplet m, int i, int j,int dir,dico dico){
        if (m.lettres.charAt(m.pos)==grid[i][j]) {
            int n = m.lettres.length();
            boolean passe = true;
            if(dir==1){
                try {
                    for (int b = j-m.pos;b<j+n-m.pos;b++) {
                        if (b!=j && (int)grid[i][b]!=0)
                            passe = false;
                    }
                }
                catch(Exception e){passe = false;}
                if (j-m.pos-1>=0 && (int)grid[i][j-m.pos-1]!=0)
                    passe = false;
                if (j+n-m.pos<15 && (int)grid[i][j+n-m.pos]!=0)
                    passe = false;
                if(passe && verifemplacement(i,j,m,1,dico))
                    return true;
                else
                    return false;

            }
        else
        {
            try {
            for (int a = i-m.pos;a<i+n-m.pos;a++) {
                if (a!=i && (int)grid[a][j]!=0)
                    passe = false;
            }
        }
        catch(Exception e){passe = false;}
            if (i-m.pos-1>=0 && (int)grid[i-m.pos-1][j]!=0)
                passe = false;
            if (i+n-m.pos<15 && (int)grid[i+n-m.pos][j]!=0)
                passe = false;
            if(passe && verifemplacement(i,j,m,0,dico))
                return true;
            else
                return false;
        }
    }
    else
    return false;}
    public boolean valide(int i,int j,char c,int dir,dico dictio) {
        if(dir==0) {
            //horizontal
            int g = j-1;
            int d = j+1;
            String mot =String.valueOf(c);
            while(g>=0 && (int)grid[i][g]!=0) {
                mot = String.valueOf(grid[i][g])+mot;
                g-=1;}
            while(d<15 && (int)grid[i][d]!=0){
                mot = mot+String.valueOf(grid[i][d]);
                d+=1;}
            if (mot.length()>1)
                if(dictio.dico.containsKey((new mot(mot)).hashCod()))
                    return true;
                else
                    return false;
            else
                return true;

        }

        else
        {
            //vertical
            int b = i-1;
            int h = i+1;
            String mot =String.valueOf(c);
            while(b>=0 && (int)grid[b][j]!=0) {
                mot = String.valueOf(grid[b][j])+mot;
                b-=1;}
            while(h<15 && (int)grid[h][j]!=0){
                mot = mot+String.valueOf(grid[h][j]);
                h+=1;}
            if (mot.length()>1)
                if(dictio.dico.containsKey((new mot(mot)).hashCod()))
                    return true;
                else
                    return false;
            else
                return true;

        }
    }

    public boolean verifemplacement(int i,int j,motcomplet m,int dir,dico dictio){
        int n = m.lettres.length();
        if (dir==0){
            for (int a = i-m.pos;a<i+n-m.pos;a++){
                if(a!=i && !valide(a,j,m.lettres.charAt(a-i+m.pos),0,dictio))
                    return false;
            }
            return true;}
        else{
            for (int b = j-m.pos;b<j+n-m.pos;b++){
                if(b!=j && !valide(i,b,m.lettres.charAt(b-j+m.pos),1,dictio))
                    return false;
            }
            return true;}

    }

    public static int mul(int i,int j){
        if (i>7)
            i = 14-i;
        if (j>7)
            j = 14-j;
        int comb = 10*i+j;
        switch(comb){
            case 0:
                return 3;
            case 70:
                return 3;
            case 07:
                return 3;
            case 11:
                return 2;
            case 22:
                return 2;
            case 33:
                return 2;
            case 44:
                return 2;
        }
        return 1;


    }

    public void imprime(){
        for(int i = 0;i<15;i++){
            for (int j = 0;j<15;j++)
                if (((int)grid[i][j])==0)
                    System.out.print("  ");
                else
                    System.out.print(grid[i][j]+" ");
            System.out.println();}
    }



}
