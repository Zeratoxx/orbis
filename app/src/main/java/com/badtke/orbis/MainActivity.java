package com.badtke.orbis;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {


    private ImageView imageView_earth;
    private Button    button_start;
    private FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
        try {
            initializeOnClickActions();
        } catch (Exception ignored) {
        }
    }

    public void initializeVariables() {
        fab = findViewById(R.id.fab);
        imageView_earth = (ImageView) this.findViewById(R.id.erde);
        imageView_earth.setImageResource(R.drawable.comic_world_croped);
        button_start = (Button) findViewById(R.id.button_start);
    }

    public void initializeOnClickActions() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), PlayActivity.class);
                //Bundle data = new Bundle();
                //data.putSerializable("senderliste", newSenderList);
                //data.putString("ip", ipAdress);
                //data.putString("port", port);
                //myIntent.putExtra("myBundle", data);
                startActivity(myIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

}
