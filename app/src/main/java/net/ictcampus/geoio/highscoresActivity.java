package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import javax.xml.transform.Result;

public class highscoresActivity extends AppCompatActivity {

    private ArrayList<TextView> mode = new ArrayList<>();
    private Button resetHighscores, returnImg;

    Context context;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        context = this;
        resetHighscores = (Button) findViewById(R.id.reset);

        mode.add((TextView) findViewById(R.id.asia));
        mode.add((TextView) findViewById(R.id.europe));
        mode.add((TextView) findViewById(R.id.africa));
        mode.add((TextView) findViewById(R.id.oceania));
        mode.add((TextView) findViewById(R.id.america));
        mode.add((TextView) findViewById(R.id.all));
        mode.add((TextView) findViewById(R.id.capital));

        for (TextView textView: mode) {
            String key = "highscore"+ String.valueOf(textView.getContentDescription());
            int highscore = ResultScreenActivity.getDefaults(key, this);
            textView.setText("Highscore " + String.valueOf(textView.getContentDescription()) + ": " + highscore +"%");
        }

        resetHighscores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (TextView textView: mode) {
                    String key = "highscore" + String.valueOf(textView.getContentDescription());
                    int value = 0;
                    ResultScreenActivity.setDefaults(key, value, context, "activity");
                    finish();
                    startActivity(getIntent());
                }
            }
        });

        ImageView returnImg = (ImageView) findViewById(R.id.returnImage);
        returnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}