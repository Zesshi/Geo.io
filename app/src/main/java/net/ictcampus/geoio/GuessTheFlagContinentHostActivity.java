package net.ictcampus.geoio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GuessTheFlagContinentHostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag_continent_host);

        Button europe = (Button) findViewById(R.id.europeBtn);
        europe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent guessTheFlag = new Intent(getApplicationContext(), GuessTheFlagContinentActivity.class);

                ArrayList<String> regions = new ArrayList<>();
                regions.add("europe");

                guessTheFlag.putStringArrayListExtra("regions", regions);

                startActivity(guessTheFlag);
            }
        });

        Button asia = (Button) findViewById(R.id.asiaBtn);
        asia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent guessTheFlag = new Intent(getApplicationContext(), GuessTheFlagContinentActivity.class);

                ArrayList<String> regions = new ArrayList<>();
                regions.add("asia");

                guessTheFlag.putStringArrayListExtra("regions", regions);

                startActivity(guessTheFlag);
            }
        });

        Button america = (Button) findViewById(R.id.americaBtn);
        america.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent guessTheFlag = new Intent(getApplicationContext(), GuessTheFlagContinentActivity.class);

                ArrayList<String> regions = new ArrayList<>();
                regions.add("america");

                guessTheFlag.putStringArrayListExtra("regions", regions);

                startActivity(guessTheFlag);
            }
        });

        Button africa = (Button) findViewById(R.id.africaBtn);
        africa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent guessTheFlag = new Intent(getApplicationContext(), GuessTheFlagContinentActivity.class);


                ArrayList<String> regions = new ArrayList<>();
                regions.add("africa");

                guessTheFlag.putStringArrayListExtra("regions", regions);

                startActivity(guessTheFlag);
            }
        });

        Button oceania = (Button) findViewById(R.id.oceaniaBtn);
        oceania.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent guessTheFlag = new Intent(getApplicationContext(), GuessTheFlagContinentActivity.class);

                ArrayList<String> regions = new ArrayList<>();
                regions.add("oceania");
                regions.add("arctic");
                regions.add("antarctic");

                guessTheFlag.putStringArrayListExtra("regions", regions);


                startActivity(guessTheFlag);
            }
        });
    }



}