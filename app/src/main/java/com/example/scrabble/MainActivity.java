package com.example.scrabble;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.graphics.Color;
import android.graphics.Point;
import android.icu.text.DisplayContext;
import android.os.Bundle;
import android.os.Environment;
import android.renderscript.ScriptGroup;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
//[]

    //{}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final char[][] grid = new char[15][15];
        final char[] deck = new char[7];
        InputFilter[] filters= new InputFilter[]{new InputFilter.LengthFilter(1) };
        ConstraintLayout constraintLayout = findViewById(R.id.Layout);
        EditText editText;
        Button button = findViewById(R.id.button);
        final dico dico = new dico();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int taillex = size.x;
        int tailley = size.y;
        float taillecase = (taillex-48)/15;
        try{
            InputStream is = getAssets().open("mots.txt");
            dico.charge(is);}
        catch (IOException e){Log.v("erreur",e.toString());}

        final TextView text  = findViewById(R.id.textView);
        text.setTextSize(20);
        final EditText[][] editTexts = new EditText[15][15];
        final EditText[] lettres_deck = new EditText[7];
        View.OnTouchListener textclicked = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                EditText edit = (EditText)view;
                edit.setSelection(edit.getText().length());
                return false;
            }
        };
        for (int i=0;i<15;i++){
            for (int j = 0;j<15;j++){
                editText = new EditText(this);
                editText.setText("");
                editText.setMaxLines(1);
                editText.setTextSize(15);
                editText.setTextSize(18);
                editText.setWidth((int)taillecase);
                editText.setHeight((int)taillecase);
                editText.setX((float)(24+taillecase*i));
                editText.setY((float)(120+taillecase*j));
                editText.setPadding(5,0,5,0);
                editText.setGravity(Gravity.CENTER);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                editText.setFilters(filters);
                //editText.setOnTouchListener(textclicked);
                editTexts[i][j] = editText;
                constraintLayout.addView(editText);
            }
        }
        for(int i = 0;i<7;i++){
            editText = new EditText(this);
            editText.setText("");
            editText.setMaxLines(1);
            editText.setTextSize(15);
            editText.setWidth(20*taillex/400);
            editText.setHeight(18*taillex/400);
            editText.setX((float)taillex/400*(110+30*i));
            editText.setY((float)0.7*tailley);
            editText.setPadding(5,0,5,0);
            editText.setGravity(Gravity.CENTER);
            editText.setFilters(filters);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
            //editText.setOnTouchListener(textclicked);
            lettres_deck[i] = editText;
            constraintLayout.addView(editText);
}
        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        };

        View.OnClickListener but = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String cas;

                for (int i = 0;i<15;i++){
                    for (int j = 0 ;j<15;j++){
                        cas = editTexts[i][j].getText().toString();
                        editTexts[i][j].setTextColor(Color.BLACK);
                        if (!cas.equals("")){
                            cas = cas.toLowerCase();
                            grid[i][j]= cas.charAt(0);}
                        else{grid[i][j]=(char)0;}
                    }
                }
                for (int i = 0;i<7;i++){
                        cas = lettres_deck[i].getText().toString();
                        if (!cas.equals("")){
                            cas = cas.toLowerCase();
                            deck[i]= cas.charAt(0);}
                        else{deck[i]=(char)0;}

                }
                deck joueur = new deck(deck);
                plateau plat = new plateau(grid);
                motposable resultat = joueur.ajouer(dico,plat);
                if (resultat.dir==1){
                    for (int j = resultat.j-resultat.pos;j<resultat.j-resultat.pos+resultat.lettres.length();j++){
                        editTexts[resultat.i][j].setText(resultat.lettres.substring(j-resultat.j+resultat.pos,j-resultat.j+resultat.pos+1).toUpperCase());
                        editTexts[resultat.i][j].setTextColor(Color.RED);
                    }
                }
                else{
                    for (int i = resultat.i-resultat.pos;i<resultat.i-resultat.pos+resultat.lettres.length();i++){
                        editTexts[i][resultat.j].setText(resultat.lettres.substring(i-resultat.i+resultat.pos,i-resultat.i+resultat.pos+1).toUpperCase());
                        editTexts[i][resultat.j].setTextColor(Color.RED);
                    }
                }


                text.setText("Point : "+resultat.points(plat));
            }
        };
        for (int i=0;i<15;i++){
            for (int j = 0;j<15;j++){
                editTexts[i][j].setOnTouchListener(onTouchListener);

                }
        }


        button.setOnClickListener(but);
        Button retour = findViewById(R.id.retour);
        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Menu.class));
            }
        });
    }
}
