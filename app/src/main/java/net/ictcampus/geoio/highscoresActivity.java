package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class highscoresActivity extends AppCompatActivity {

    private ArrayList<TextView> mode = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        mode.add((TextView) findViewById(R.id.asia));
        mode.add((TextView) findViewById(R.id.europe));
        mode.add((TextView) findViewById(R.id.africa));
        mode.add((TextView) findViewById(R.id.oceania));
        mode.add((TextView) findViewById(R.id.america));
        mode.add((TextView) findViewById(R.id.all));
        mode.add((TextView) findViewById(R.id.capital));
        for (TextView textView: mode) {
            SharedPreferences sharedPreferences = getSharedPreferences("save", MODE_PRIVATE);

            textView.setText("Highscore " + textView.getContentDescription()+ ": " + (sharedPreferences.getFloat(("highscore"+textView.getContentDescription()), 0)));
        }

    }
}