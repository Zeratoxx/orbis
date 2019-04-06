package com.badtke.orbis;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView_earth;
    private Button    button_start;
    private FloatingActionButton fab_profile;
    private FloatingActionButton fab_settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Wenn keine serialisierung, dann Aufgaben neuladen

        initializeVariables();
        try {
            initializeOnClickActions();
        } catch (Exception ignored) {
        }
    }

    public void initializeVariables() {
        fab_profile = (FloatingActionButton) findViewById(R.id.fab_profile);
        imageView_earth = (ImageView) findViewById(R.id.erde);
        button_start = (Button) findViewById(R.id.button_start);
        fab_settings = (FloatingActionButton) findViewById(R.id.fab_settings);
    }

    public void initializeOnClickActions() {
        fab_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), ProfileActivity.class);
                //Bundle data = new Bundle();
                //data.putSerializable("senderliste", newSenderList);
                //data.putString("ip", ipAdress);
                //data.putString("port", port);
                //myIntent.putExtra("myBundle", data);
                startActivity(myIntent);
                overridePendingTransition(R.anim.none, R.anim.none);


                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        fab_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), SettingsActivity.class);
                //Bundle data = new Bundle();
                //data.putSerializable("senderliste", newSenderList);
                //data.putString("ip", ipAdress);
                //data.putString("port", port);
                //myIntent.putExtra("myBundle", data);
                startActivity(myIntent);
                overridePendingTransition(R.anim.none, R.anim.none);


                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
            }
        });

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), WorldsActivity.class);
                //Bundle data = new Bundle();
                //data.putSerializable("senderliste", newSenderList);
                //data.putString("ip", ipAdress);
                //data.putString("port", port);
                //myIntent.putExtra("myBundle", data);
                startActivity(myIntent);
                overridePendingTransition(R.anim.none, R.anim.none);
            }
        });
    }

}
