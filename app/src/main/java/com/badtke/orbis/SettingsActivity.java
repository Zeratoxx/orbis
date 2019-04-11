package com.badtke.orbis;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

public class SettingsActivity extends AppCompatActivity {


    private ImageView  imageView_back;
    private Switch switch_mute;




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
