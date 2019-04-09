package com.badtke.orbis;

import com.opencsv.CSVReader;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView_earth;
    private Button    button_start;
    private FloatingActionButton fab_profile;
    private FloatingActionButton fab_settings;


    private ArrayList<ArrayList<String>> aufgabenSammlung = new ArrayList<ArrayList<String>>();
    private final Integer fileName = R.raw.aufgaben_sammlung;





    //---------- Serialisierung --------------

    Datenmodell     myData = Datenmodell.getInstance();

    @Override
    protected void onResume() {
        super.onResume();
        try {
            myData.datenmodellDeserialisieren(getApplicationContext());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Deserialisierung");
            builder.setMessage("Fehlgeschlagen!");
            AlertDialog dialog = builder.show();
        }

        /*new HttpRequestAsync().execute("debug=1");
        if(myData.isVolumeMuteState()) {
            slider.setProgress(0);
            aktuelleLautstarke.setText("0");
            imageButtonMute.setImageResource(R.drawable.ic_volume_off);
        } else {
            slider.setProgress(myData.getVolume());
            aktuelleLautstarke.setText(Integer.toString(myData.getVolume()));
            imageButtonMute.setImageResource(R.drawable.ic_volume_up);
        }
        if(myData.getChannelMainPosition() != -1) { buttonSender.setText(myData.getAlleProgrammNamen().get(myData.getChannelMainPosition())); }
        else { buttonSender.setText("Sender"); }
        if(myData.isPause())
        {
            buttonPause.setText(R.string.programm_weiter);
            buttonPause.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_media_play, 0);
        }
        else
        {
            buttonPause.setText(R.string.programm_pausieren);
            buttonPause.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.ic_media_pause, 0);
        }
        if(myData.isZoomState())
        {
            buttonZoom.setChecked(true);
        }
        else
        {
            buttonZoom.setChecked(false);
        }*/
    }


    @Override
    public void onPause(){
        super.onPause();
        try {
            myData.datenmodellSerialisieren(getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Serialisierung");
            builder.setMessage("Fehlgeschlagen!");
            AlertDialog dialog = builder.show();
        }
    }






    //---------- Starting -------------

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










    /** @brief Initial read of all tasks to do.
     * Reads the external file of all tasks and saves it to an array.
     * Adds a field to every Line with the value "unfinished".
     *
     * @return void
     */
    private void aufgabenEinlesen() {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(getResources().openRawResource(fileName)), ';');
            String [] nextLine;
            int line = 0;
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line

                aufgabenSammlung.add(new ArrayList<String>());

                for (String aNextLine : nextLine) {

                    aufgabenSammlung.get(line).add(aNextLine); //one Line of aufgabenSammlung:
                }
                //Log.d("VariableTag", nextLine[0]);
                aufgabenSammlung.get(line).add("unfinished");

                line++;
            }

            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, aufgabenSammlung.get(0).get(0), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "The specified file was not found.", Toast.LENGTH_SHORT).show();
        }

    }



}
