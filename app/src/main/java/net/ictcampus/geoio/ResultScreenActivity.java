package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultScreenActivity extends AppCompatActivity {

    private static final String TAG = "Result";
    private int correctAnswers;
    private int numbOfQuestions;
    private TextView result;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        Intent intent = getIntent();
        correctAnswers = Integer.parseInt(intent.getStringExtra("correctAnswers"));
        numbOfQuestions = Integer.parseInt(intent.getStringExtra("numbOfQuestions"));

        Log.wtf(TAG, String.valueOf(correctAnswers));
        Log.wtf(TAG, String.valueOf(numbOfQuestions));

        float tryToCast  = correctAnswers;
        float tryToCast2  = numbOfQuestions;

        float resultInPercent = tryToCast / tryToCast2 * 100;

        returnButton = (Button) findViewById(R.id.returnButton);

        result = (TextView) findViewById(R.id.result);

        result.setText(correctAnswers + " / " + numbOfQuestions  + " correct | " + resultInPercent + "%");

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });

    }
}