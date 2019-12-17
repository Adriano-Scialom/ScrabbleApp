package com.example.scrabble;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
//[]

//{}
public class Partie_ordi extends AppCompatActivity {
    final char[][] grid = new char[15][15];
    final char[] deck = new char[7];
    final Dico dico = new Dico();

    TextView score_joueur;
    TextView score_ordinateur;
    final EditText[][] editTexts = new EditText[15][15];
    final EditText[] lettres_deck = new EditText[7];
    Board plateau = new Board(grid);
    Game partie = new Game(dico,plateau);
    Button redemarrer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partie_ordi);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int taillex = size.x;
        int tailley = size.y;

        float taillecase = (taillex-48)/15;
        redemarrer = findViewById(R.id.Redemarrer);
        score_joueur = findViewById(R.id.scorejoueur);
        score_ordinateur = findViewById(R.id.scoreordi);
        redemarrer.setOnClickListener(redemarre);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                grid[i][j] = (char)0;
            }
        }
        grid[7][7] = 'e';


        InputFilter[] filters= new InputFilter[]{new InputFilter.LengthFilter(1)};
        ConstraintLayout constraintLayout = findViewById(R.id.PartieVSAI);
        EditText editText;
        Button button = findViewById(R.id.joue);

        Button retour = findViewById(R.id.retour_ordi);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Partie_ordi.this,Menu.class));
            }
        });
        try{
            InputStream is = getAssets().open("mots.txt");
            dico.charge(is);}
        catch (IOException e){
            Log.v("erreur",e.toString());}


        for (int i=0;i<15;i++){
            for (int j = 0;j<15;j++){
                editText = new EditText(this);
                editText.setText("");
                editText.setMaxLines(1);
                editText.setTextSize(18);
                editText.setTextColor(Color.rgb(206,206,206));
                editText.setWidth((int)taillecase);
                editText.setHeight((int)taillecase);
                editText.setX((float)(24+taillecase*i));
                editText.setY((float)(120+taillecase*j));
                editText.setPadding(0,0,0,5);
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                editText.setFilters(filters);
                editTexts[i][j] = editText;
                constraintLayout.addView(editText);
            }
        }
        editTexts[7][7].setText("E");
        for(int i = 0;i<7;i++){
            editText = new EditText(this);
            editText.setText(String.valueOf(partie.j1.letters[i]).toUpperCase());
            editText.setMaxLines(1);
            editText.setTextSize(15);
            editText.setWidth((int)(taillecase));
            editText.setHeight(18*taillex/400);
            editText.setX((float)(taillex/400*(100+30*i)));
            editText.setY((float)0.7*tailley);
            editText.setPadding(0,0,0,5);
            editText.setGravity(Gravity.CENTER);
            editText.setFilters(filters);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            lettres_deck[i] = editText;
            constraintLayout.addView(editText);
        }


        View.OnClickListener but = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String cas;
                ArrayList<Position> changements = new ArrayList<Position>();
                char[][] gridavant = new char[15][15];
                for (int i=0;i<15;i++){
                    gridavant[i] = grid[i].clone();}

                for (int i = 0;i<15;i++){
                    for (int j = 0 ;j<15;j++){
                        cas = editTexts[i][j].getText().toString();
                        editTexts[i][j].setTextColor(Color.BLACK);
                        if (!cas.equals("")){
                            cas = cas.toLowerCase();
                            grid[i][j]= cas.charAt(0);}
                        else{grid[i][j]=(char)0;}
                        if (grid[i][j]!=gridavant[i][j]){
                            editTexts[i][j].setInputType(InputType.TYPE_NULL);
                            changements.add(new Position(i,j,grid[i][j]));}
                    }
                }
                for (int i = 0;i<7;i++){
                    cas = lettres_deck[i].getText().toString();
                    if (!cas.equals("")){
                        cas = cas.toLowerCase();
                        deck[i]= cas.charAt(0);}
                    else{deck[i]=(char)0;}

                }

                Board plateauavant = new Board(gridavant);


                MotPosable motpose = null;
                try {
                    motpose = Position.motMis(changements,plateauavant);
                } catch (Exception e) {
                    Toast.makeText(Partie_ordi.this,"Veuillez jouer", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!plateauavant.bon_mot(motpose,dico) || !isSubString(motpose.lettres.toCharArray(),partie.j1.letters)){
                    retourTourPrecedent(plateauavant);
                    return;}
                int points  = motpose.points(plateauavant);
                partie.score1+=points;
                score_joueur.setText(String.valueOf(partie.score1));
                Toast.makeText(Partie_ordi.this,"Vous avez fait "+String.valueOf(points)+ " points", Toast.LENGTH_LONG).show();
                for (Position pos : changements) {
                    char lettre = pos.letter;
                    for (int i = 0; i < 7; i++) {
                        if (partie.j1.letters[i]==lettre){
                            partie.j1.letters[i] = (char)0;
                            break;}
                    }
                }
                for (int i = 0; i < 7; i++) {
                    if (partie.j1.letters[i]==(char)0)
                        try {
                            partie.j1.letters[i]= partie.tire();
                            lettres_deck[i].setText(String.valueOf(partie.j1.letters[i]).toUpperCase());
                        }catch (Exception e){
                            finDePartie();
                            Log.e("",e.toString());}
                }

                if (!partie.tour(2))
                    finDePartie();
                score_ordinateur.setText(String.valueOf(partie.score2));
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 15; j++) {
                        if (partie.board.grid[i][j]!=(char)0){
                        editTexts[i][j].setText(String.valueOf(partie.board.grid[i][j]).toUpperCase());}
                    }
                }

            }

        };

        button.setOnClickListener(but);

    }
    void finDePartie(){
        Toast.makeText(Partie_ordi.this,"Vous avez fini la Game", Toast.LENGTH_LONG).show();
    }

    View.OnClickListener redemarre = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    grid[i][j] = (char)0;
                }
            }
            grid[7][7] = 'e';
            for (int i = 0; i < 15; i++) {
                for (int j = 0; j < 15; j++) {
                    editTexts[i][j].setText(String.valueOf(""));
                }
            }
            editTexts[7][7].setText("E");
            score_joueur.setText("0");
            score_ordinateur.setText("0");
            partie = new Game(dico,plateau);
            for(int i = 0;i<7;i++){
                lettres_deck[i].setText(String.valueOf(partie.j1.letters[i]).toUpperCase());}
        }
    };

    static boolean isSubString(char[] a, char[] b){
        int n = a.length;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                if (a[i]==b[i]){
                    b[i]=(char)0;
                    n--;
                    break;
                }
            }
        }
        return n==0;
    }

    void retourTourPrecedent(Board plateau){
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (plateau.grid[i][j]!=(char)0)
                    editTexts[i][j].setText(String.valueOf(plateau.grid[i][j]).toUpperCase());
                else{
                    editTexts[i][j].getText().clear();
                    editTexts[i][j].setInputType(InputType.TYPE_CLASS_TEXT);}
                grid[i][j] = plateau.grid[i][j];
            }
        }
    }
}
