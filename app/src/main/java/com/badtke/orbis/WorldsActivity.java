package com.badtke.orbis;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class WorldsActivity extends AppCompatActivity {


    private ImageView  imageView_back;
    private Button world_eins;
    private Button world_zwei;
    private Button world_drei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worlds);

        initializeVariables();
        try {
            initializeOnClickActions();
        } catch (Exception ignored) {
        }
    }

    public void initializeVariables() {
        imageView_back = (ImageView) findViewById(R.id.imageView_back);
        world_eins = (Button) findViewById(R.id.button1);
        world_zwei = (Button) findViewById(R.id.button2);
        world_drei = (Button) findViewById(R.id.button3);
    }

    public void initializeOnClickActions() {
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        world_eins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PlayActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.none, R.anim.none);
            }
        });
        world_zwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PlayActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.none, R.anim.none);            }
        });
        world_drei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PlayActivity.class);
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
