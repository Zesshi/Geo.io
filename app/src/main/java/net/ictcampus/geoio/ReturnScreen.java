package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ReturnScreen extends AppCompatActivity {

    String extra;
    private static final String TAG = "Flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_screen);
        Intent intent = getIntent();

        extra = intent.getStringExtra("class");

        Button keepButton = (Button) findViewById(R.id.keepButton);
        keepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (extra.equals("GuessTheFlagActivity")) {
                    Intent game = new Intent(getApplicationContext(), GuessTheFlagActivity.class);
                    startActivity(game);
                } else if (extra.equals("GuessTheCapital")) {
                    Intent game = new Intent(getApplicationContext(), GuessTheCapital.class);
                    startActivity(game);
                } else if (extra.equals("GuessTheFlagContinentActivity")) {
                    Intent game = new Intent(getApplicationContext(), GuessTheFlagContinentActivity.class);
                    startActivity(game);
                }
            }
        });

        Button quitButton = (Button) findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(menu);
            }
        });
    }
}