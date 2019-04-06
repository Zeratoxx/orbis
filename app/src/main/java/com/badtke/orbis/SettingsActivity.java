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

public class SettingsActivity extends AppCompatActivity {


    private ImageView  imageView_back;
    private Switch switch_mute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeVariables();
        try {
            initializeOnClickActions();
        } catch (Exception ignored) {
        }
    }

    public void initializeVariables() {
        imageView_back = (ImageView) findViewById(R.id.imageView_back);
        //switch_mute = (Switch) findViewById(R.id.switch_mute);
    }

    public void initializeOnClickActions() {
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.none, R.anim.none);
    }

}
