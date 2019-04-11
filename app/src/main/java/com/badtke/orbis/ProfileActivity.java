package com.badtke.orbis;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private ImageView  imageView_back;
    private TextView textView_score;
    private TextView textView_name;
    private TextView textView_doneDone;
    private TextView textView_notDone;
    private Button button_changeName;


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
        textView_name.setText(myData.getUserName());
        textView_doneDone.setText(String.valueOf(myData.getCurrentLevel() - 1)); // minus 1 bc current level isnt done yet ???
        textView_notDone.setText(String.valueOf(myData.getAmountOfAufgaben() - (myData.getCurrentLevel() - 1))); // minus 1 bc current level isnt done yet ????
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
        button_changeName = (Button) findViewById(R.id.button_changeName);

    }

    public void initializeOnClickActions() {
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        button_changeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Benutzername");
                builder.setMessage("Bitte geben Sie Ihren Benutzernamen ein: ");
                    // Set up the input
                final EditText input = new EditText(view.getContext());
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
                                    textView_name.setText(myData.getUserName());
                                    dialog.dismiss();

                                } else
                                    Toast.makeText(v.getContext(), "Bitte geben Sie etwas ein!", Toast.LENGTH_SHORT).show();
                                //else dialog stays open. Make sure you have an obvious way to
                                // close the dialog especially if you set cancellable to false.
                            }
                        }
                );
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.none, R.anim.none);
    }
}
