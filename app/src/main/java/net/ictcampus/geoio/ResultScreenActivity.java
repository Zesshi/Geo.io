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
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class ResultScreenActivity extends AppCompatActivity {

    private static final String TAG = "Result";
    private int skippedQuestions, resultInPercent;
    private float flCorrectAnswers, flNumbOfQuestions, flMaxNumbOfQuestions;
    private TextView result, skipped;
    private Button returnButton;
    private Intent intent;
    private SharedPreferences sharedPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        intent = getIntent();
        skippedQuestions = Integer.parseInt(intent.getStringExtra("skipped"));
        flCorrectAnswers = Integer.parseInt(intent.getStringExtra("correctAnswers"));
        flNumbOfQuestions = Integer.parseInt(intent.getStringExtra("numbOfQuestions"));
        flMaxNumbOfQuestions = Integer.parseInt(intent.getStringExtra("maxNumbOfQuestions"));

        float decimal = flNumbOfQuestions / flMaxNumbOfQuestions;

        resultInPercent = Math.round(flCorrectAnswers / flNumbOfQuestions * 100);

        if (decimal >= 0.9) {
            String key = "highscore" + intent.getStringExtra("continent");
            setDefaults(key, resultInPercent, this, "");
        } else {
            Toast.makeText(this, "The Result only gets saved, if you answered more than 80% of questions!", Toast.LENGTH_SHORT).show();
        }

        returnButton = (Button) findViewById(R.id.returnButton);
        result = (TextView) findViewById(R.id.result);
        skipped = (TextView) findViewById(R.id.skippedQuestions);

        result.setText(Math.round(flCorrectAnswers) + " / " + Math.round(flNumbOfQuestions) + " correct | " + resultInPercent + "%");
        skipped.setText("Skipped Questions: " + skippedQuestions);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });

    }


    public static void setDefaults(String key, int value, Context context, String origin) {

        if (value > getDefaults(key, context) || origin.equals("activity")) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(key, value);
            editor.apply();
        }
    }

    public static int getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
    }
}