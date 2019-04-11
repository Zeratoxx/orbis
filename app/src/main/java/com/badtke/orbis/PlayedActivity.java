package com.badtke.orbis;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Objects;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PlayedActivity extends AppCompatActivity {

    private TextView textView_done;
    private ImageView  imageView_back;
    private ImageView  imageView_home;
    private ImageView imageView_coins;
    private TextView textView_score;
    private TextView textView_level;



    //---------- Serialisierung --------------

    Datenmodell     myData = Datenmodell.getInstance();

    @Override
    protected void onResume() {
        super.onResume();
        try {
            myData.datenmodellDeserialisieren(getApplicationContext());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }




        textView_score.setText(String.valueOf(myData.getCoins()));
        textView_level.setText(String.valueOf(myData.getCurrentLevel()));
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
        setContentView(R.layout.activity_played);

        initializeVariables();
        try {
            initializeOnClickActions();
        } catch (Exception ignored) {
        }
    }

    public void initializeVariables() {
        textView_done = (TextView) findViewById(R.id.textView_done);
        imageView_back = (ImageView) findViewById(R.id.imageView_back);
        imageView_home = (ImageView) findViewById(R.id.imageView_home);
        imageView_coins = (ImageView) findViewById(R.id.imageView_coins);
        textView_score = (TextView) findViewById(R.id.textView_score);
        textView_level = (TextView) findViewById(R.id.textView_level);

        textView_done.setText("Glückwunsch! Du hast die Welt sauberer gemacht!");
        Glide.with(this).load(R.drawable.comic_coin_edited).into(imageView_coins);

    }

    public void initializeOnClickActions() {
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), WorldsActivity.class);
                startActivity(myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(R.anim.none, R.anim.none);
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
    }

    @Override
    public void onBackPressed(){
        Intent myIntent = new Intent(this.getBaseContext(), WorldsActivity.class);
        startActivity(myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        overridePendingTransition(R.anim.none, R.anim.none);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.none, R.anim.none);
    }

}
