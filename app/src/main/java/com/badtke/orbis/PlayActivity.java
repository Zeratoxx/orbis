package com.badtke.orbis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {

    private TextView textView_aufgabe;
    private Button  button_done;
    private ImageView  imageView_back;
    private ImageView  imageView_home;
    private ImageView imageView_coins;
    private ImageView imageView_bonusCoins;
    private TextView textView_bonusCoins;
    private TextView textView_score;
    private TextView textView_level;



    //---------- Serialisierung --------------

    Datenmodell     myData = Datenmodell.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        try {
            myData.datenmodellDeserialisieren(getApplicationContext());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        if(myData.handleNewAufgabeOrNot() > 0) {
            textView_score.setText(String.valueOf(myData.getCoins()));
            textView_aufgabe.setText(myData.getAufgabe());
            textView_level.setText(String.valueOf(myData.getCurrentLevel()));
            textView_bonusCoins.setText("+ " + String.valueOf(myData.getAufgabenWert(myData.getCurrentAufgabe())));
        } else {
            Intent myIntent = new Intent(this, PlayedActivity.class);
            startActivity(myIntent);
            overridePendingTransition(R.anim.none, R.anim.none);
        }
    }


    @Override
    public void onPause(){
        super.onPause();
        try {
            myData.datenmodellSerialisieren(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






    //---------- Starting -------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        initializeVariables();
        try {
            initializeOnClickActions();
        } catch (Exception ignored) {
        }
    }

    public void initializeVariables() {
        textView_aufgabe = (TextView) findViewById(R.id.textView_aufgabe);
        imageView_back = (ImageView) findViewById(R.id.imageView_back);
        imageView_home = (ImageView) findViewById(R.id.imageView_home);
        imageView_coins = (ImageView) findViewById(R.id.imageView_coins);
        imageView_bonusCoins = (ImageView) findViewById(R.id.imageView_bonusCoins);
        button_done = (Button) findViewById(R.id.button_done);
        textView_score = (TextView) findViewById(R.id.textView_score);
        textView_level = (TextView) findViewById(R.id.textView_level);
        textView_bonusCoins = (TextView) findViewById(R.id.textView_bonusCoins);

        Glide.with(this).load(R.drawable.comic_coin_edited).into(imageView_coins);
        Glide.with(this).load(R.drawable.comic_coin_edited).into(imageView_bonusCoins);
    }

    public void initializeOnClickActions() {
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imageView_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), MainActivity.class);
                startActivity(myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(R.anim.none, R.anim.none);
            }
        });
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myData.aufgabeGeschafft();
                Intent myIntent = new Intent(view.getContext(), PlayedActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.none, R.anim.none);
            }
        });
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.none, R.anim.none);
    }

}
