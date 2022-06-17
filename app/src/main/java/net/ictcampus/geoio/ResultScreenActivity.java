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

import java.util.prefs.Preferences;

public class ResultScreenActivity extends AppCompatActivity {

    private static final String TAG = "Result";
    private int correctAnswers, numbOfQuestions, skippedQuestions;
    private TextView result, skipped;
    private Button returnButton;
    private Intent intent;
    private SharedPreferences sharedPreferences;
    private float resultInPercent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        intent = getIntent();
        correctAnswers = Integer.parseInt(intent.getStringExtra("correctAnswers"));
        numbOfQuestions = Integer.parseInt(intent.getStringExtra("numbOfQuestions"));
        skippedQuestions = Integer.parseInt(intent.getStringExtra("skipped"));

        float tryToCast = correctAnswers;
        float tryToCast2 = numbOfQuestions;


        resultInPercent = tryToCast / tryToCast2 * 100;

        setDefaults("highscore" + intent.getStringExtra("continent"), resultInPercent, this);

        returnButton = (Button) findViewById(R.id.returnButton);

        result = (TextView) findViewById(R.id.result);
        skipped = (TextView) findViewById(R.id.skippedQuestions);

        result.setText(correctAnswers + " / " + numbOfQuestions + " correct | " + resultInPercent + "%");
        skipped.setText("Skipped Questions: " + skippedQuestions);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });


    }

    public static void setDefaults(String key, Float value, Context context) {
        if (value > getDefaults(key, context)) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putFloat(key, value);
            editor.apply(); // or editor.commit() in case you want to write data instantly
        }
    }

    public static Float getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getFloat(key, 0);
    }
}