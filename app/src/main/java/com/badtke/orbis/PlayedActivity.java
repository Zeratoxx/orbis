package com.badtke.orbis;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class PlayedActivity extends AppCompatActivity {

    private TextView textView_done;
    private ImageView  imageView_back;
    private ImageView  imageView_home;

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


        textView_done.setText("Glückwunsch! Du hast die Welt ein sauberer gemacht!");
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
