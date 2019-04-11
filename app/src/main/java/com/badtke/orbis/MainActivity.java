package com.badtke.orbis;

import com.opencsv.CSVReader;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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






        if(myData.isFirstBoot()){
//*

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Benutzername");
            builder.setMessage("Bitte geben Sie Ihren Benutzernamen ein: ");
// Set up the input
            final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            final Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            ((AlertDialog) dialog).getButton(Dialog.BUTTON_POSITIVE).setOnClickListener(
                    new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {
                            //Do stuff, possibly set wantToCloseDialog to true then...
                            if(!(input.getText().toString().equals(""))) {
                                myData.setUserName(input.getText().toString());
                                myData.noMoreFirstBoot();
                                dialog.dismiss();
                            } else
                                Toast.makeText(v.getContext(), "Bitte geben Sie etwas ein!", Toast.LENGTH_SHORT).show();
                            //else dialog stays open. Make sure you have an obvious way to
                            // close the dialog especially if you set cancellable to false.
                        }
                    }
            );
//*/
/*
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            builder2.setMessage("Test for preventing dialog close");
            builder2.setPositiveButton("Test",
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            //Do nothing here because we override this button later to change the close behaviour.
                            //However, we still need this because on older versions of Android unless we
                            //pass a handler the button doesn't get instantiated
                        }
                    });
            final AlertDialog dialog = builder2.create();
            dialog.show();
//Overriding the handler immediately after show is probably a better approach than OnShowListener as described below
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Boolean wantToCloseDialog = false;
                    //Do stuff, possibly set wantToCloseDialog to true then...
                    if(wantToCloseDialog)
                        dialog.dismiss();
                    //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
                }
            });

*/


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
       /* // Make us non-modal, so that others can receive touch events.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        // ...but notify us that it happened.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        // Note that flag changes must happen *before* the content view is set.*/
        setContentView(R.layout.activity_main);


        //Wenn keine serialisierung, dann Aufgaben neuladen

        initializeVariables();
        try {
            initializeOnClickActions();
        } catch (Exception ignored) {
        }
    }

    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        // If we've received a touch notification that the user has touched
        // outside the app, finish the activity.
        if (MotionEvent.ACTION_OUTSIDE == event.getAction()) {
            finish();
            return true;
        }

        // Delegate everything else to Activity.
        return super.onTouchEvent(event);
    }*/

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








}
