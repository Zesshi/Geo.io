package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GuessTheFlagContinentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag_continent);

        Button europe = (Button) findViewById(R.id.europeBtn);
        europe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent guessTheFlagEurope = new Intent(getApplicationContext(), GuessTheFlagEuropeActivity.class);
                startActivity(guessTheFlagEurope);
            }
        });

        Button asia = (Button) findViewById(R.id.asiaBtn);
        asia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent guessTheFlagAsia = new Intent(getApplicationContext(), GuessTheFlagAsiaActivity.class);
                startActivity(guessTheFlagAsia);
            }
        });

        Button america = (Button) findViewById(R.id.americaBtn);
        america.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent guessTheFlagAmerica = new Intent(getApplicationContext(), GuessTheFlagAmericaActivity.class);
                startActivity(guessTheFlagAmerica);
            }
        });

        Button africa = (Button) findViewById(R.id.africaBtn);
        africa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent guessTheFlagAfrica = new Intent(getApplicationContext(), GuessTheFlagAfricaActivity.class);
                startActivity(guessTheFlagAfrica);
            }
        });

        Button oceania = (Button) findViewById(R.id.oceaniaBtn);
        oceania.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent guessTheFlagOceania = new Intent(getApplicationContext(), GuessTheFlagOceaniaActivity.class);
                startActivity(guessTheFlagOceania);
            }
        });
    }



}