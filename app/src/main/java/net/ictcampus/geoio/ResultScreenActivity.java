package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

        Log.wtf(TAG, String.valueOf(correctAnswers));
        Log.wtf(TAG, String.valueOf(numbOfQuestions));

        float tryToCast  = correctAnswers;
        float tryToCast2  = numbOfQuestions;



        resultInPercent = tryToCast / tryToCast2 * 100;
        Log.wtf("REsult", String.valueOf(resultInPercent));
        PreferenceManager.getDefaultSharedPreferences(this).edit().putFloat("highscoreall", resultInPercent);

        Log.wtf("TAG", String.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getFloat("highscoreall", 0)));

        returnButton = (Button) findViewById(R.id.returnButton);

        result = (TextView) findViewById(R.id.result);
        skipped = (TextView) findViewById(R.id.skippedQuestions);

        result.setText(correctAnswers + " / " + numbOfQuestions + " correct | " + resultInPercent + "%");
        skipped.setText("Skipped Questions: " + skippedQuestions);

        setHighscore();

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });

    }

    @SuppressLint("CommitPrefEdits")
    private void setHighscore() {
        String continent = intent.getStringExtra("continent");
        Log.wtf("Continent", "highscore"+continent);
        float currentSavedHighscore = getHighscore(continent);

        if (continent.equals("all")) {
            if(resultInPercent > currentSavedHighscore){
                //PreferenceManager.getDefaultSharedPreferences(this).edit().putFloat("highscore" +continent, resultInPercent);
                /*
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat(("highscore" + continent), resultInPercent);
                editor.apply();

                 */
                SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                editor.putFloat("value", currentSavedHighscore);
                editor.apply();
            }
        } else if (continent.equals("captial")) {
                if (resultInPercent > currentSavedHighscore){

                }
        } else {
            if (resultInPercent > currentSavedHighscore) {
                PreferenceManager.getDefaultSharedPreferences(this).edit().putFloat("highscore" +continent, resultInPercent);
            }
        }
    }

    private float getHighscore(String continent){
        return PreferenceManager.getDefaultSharedPreferences(this).getFloat("highscore" +continent , 0);
    }
}