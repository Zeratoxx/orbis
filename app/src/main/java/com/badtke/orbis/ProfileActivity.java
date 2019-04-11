package com.badtke.orbis;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private ImageView  imageView_back;
    private TextView textView_score;
    private TextView textView_name;
    private TextView textView_doneDone;
    private TextView textView_notDone;



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


        textView_score.setText(String.valueOf(myData.getCoins()));
        textView_name.setText(myData.getUserName());
        textView_doneDone.setText(String.valueOf(myData.getCurrentLevel()-1)); // minus 1 bc current level isnt done yet
        textView_notDone.setText(String.valueOf(myData.getAmountOfAufgaben() - (myData.getCurrentLevel() - 1))); // minus 1 bc current level isnt done yet
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
        setContentView(R.layout.activity_profile);

        initializeVariables();
        try {
            initializeOnClickActions();
        } catch (Exception ignored) {
        }
    }

    public void initializeVariables() {
        imageView_back = (ImageView) findViewById(R.id.imageView_back);
        textView_score = (TextView) findViewById(R.id.textView_score);
        textView_name = (TextView) findViewById(R.id.textView_name);
        textView_doneDone = (TextView) findViewById(R.id.textView_doneDone);
        textView_notDone = (TextView) findViewById(R.id.textView_notDone);

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
