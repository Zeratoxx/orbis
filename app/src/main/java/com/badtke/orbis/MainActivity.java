package com.badtke.orbis;

import com.opencsv.CSVReader;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView_earth;
    private Button    button_start;
    private FloatingActionButton fab_profile;
    private FloatingActionButton fab_settings;


    private ArrayList<ArrayList<String>> aufgaben_sammlung = new ArrayList<ArrayList<String>>();
    private Integer file = R.raw.aufgaben_sammlung;

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

    @Override
    protected void onStart() {
        super.onStart();
        aufgabenEinlesen();
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



    private void aufgabenEinlesen() {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource(file)), ';');//Specify asset file name
            String [] nextLine;
            int line = 0;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line

                aufgaben_sammlung.add(new ArrayList<String>());

                for (String aNextLine : nextLine) {

                    aufgaben_sammlung.get(line).add(aNextLine);
                }
                //Log.d("VariableTag", nextLine[0]);

                line++;
            }
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, aufgaben_sammlung.get(0).get(0), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "The specified file was not found.", Toast.LENGTH_SHORT).show();
        }

    }

}
