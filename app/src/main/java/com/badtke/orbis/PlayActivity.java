package com.badtke.orbis;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

public class PlayActivity extends AppCompatActivity {

        private TextView textView_aufgabe;
        private ImageView imageView_aufgabenbild;
        private ImageView  imageView_back;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_play);

            initializeVariables();
            try {
                initializeOnClickActions();
            } catch (Exception ignored) {
            }
        }

    public void initializeVariables() {
        textView_aufgabe = (TextView) findViewById(R.id.textView_aufgabe);
        imageView_aufgabenbild = (ImageView) findViewById(R.id.imageView_aufgabenbild);
        imageView_back = (ImageView) findViewById(R.id.imageView_back);
    }

    public void initializeOnClickActions() {
        textView_aufgabe.setText("Hallo, das hier ist jetzt deine Aufgabe: !!!!");
        imageView_aufgabenbild.setImageResource(R.drawable.tree_clipart_low_resolution);
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
