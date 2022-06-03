package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button guessTheFlagButton = (Button) findViewById(R.id.button);
        guessTheFlagButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GuessTheFlagActivity.class);
                startActivity(intent);
            }
        });

        Button continentBtn = (Button) findViewById(R.id.continentBtn);
        continentBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent continentIntent = new Intent(getApplicationContext(), GuessTheFlagContinentHostActivity.class);
                startActivity(continentIntent);
            }
        });


        Button guessTheCapitalButton = (Button) findViewById(R.id.button4);
        guessTheCapitalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent capitalintent = new Intent(getApplicationContext(), GuessTheCapital.class);
                startActivity(capitalintent);
            }
        });
    }
}