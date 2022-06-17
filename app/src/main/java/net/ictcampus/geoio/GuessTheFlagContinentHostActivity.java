package net.ictcampus.geoio;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GuessTheFlagContinentHostActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private Dialog dialog;
    private String classname;
    private int numOfCountires;
    private String numberOfQuestions;
    private ImageView returnarr;
    private ArrayList regions = new ArrayList();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag_continent_host);

        returnarr = (ImageView) findViewById(R.id.returnArr);

        returnarr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homescreen = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(homescreen);
            }
        });

        Button europe = (Button) findViewById(R.id.europeBtn);
        europe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                regions.add("europe");
                numOfCountires = 53;
                showPopUp();

            }
        });

        Button asia = (Button) findViewById(R.id.asiaBtn);
        asia.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                regions.add("asia");
                numOfCountires = 50;
                showPopUp();
            }
        });

        Button america = (Button) findViewById(R.id.americaBtn);
        america.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                regions.add("america");
                numOfCountires = 56;
                showPopUp();
            }
        });

        Button africa = (Button) findViewById(R.id.africaBtn);
        africa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                regions.add("africa");
                numOfCountires = 59;
                showPopUp();


                /*
                Intent guessTheFlag = new Intent(getApplicationContext(), GuessTheFlagContinentActivity.class);
                ArrayList<String> regions = new ArrayList<>();
                regions.add("africa");
                guessTheFlag.putStringArrayListExtra("regions", regions);
                startActivity(guessTheFlag);*/
            }
        });

        Button oceania = (Button) findViewById(R.id.oceaniaBtn);
        oceania.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                regions.add("oceania");
                numOfCountires = 32;
                //regions.add("arctic");
                //regions.add("antarctic");
                showPopUp();


            }
        });
    }

    private void showPopUp() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popUp = getLayoutInflater().inflate(R.layout.activity_ask_for_question, null);
        dialogBuilder.setView(popUp);
        dialog = dialogBuilder.create();
        dialog.show();

        EditText count = (EditText) popUp.findViewById(R.id.inputNumber);

        Button all = (Button) popUp.findViewById(R.id.buttonAll);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent guessTheFlag = new Intent(getApplicationContext(), GuessTheFlagContinentActivity.class);
                guessTheFlag.putStringArrayListExtra("regions", regions);
                guessTheFlag.putExtra("numberOfQuestions", "max");
                startActivity(guessTheFlag);

                /*
                Intent intent = new Intent(getApplicationContext(), GuessTheFlagContinentActivity.class);
                intent.putExtra("numberOfQuestions", "max");
                startActivity(intent);*/

            }
        });



        Button submit = (Button) popUp.findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfQuestions = count.getText().toString();
                Log.e("dfghjk", numberOfQuestions);
                if ((Integer.parseInt(numberOfQuestions) < 1) || (numberOfQuestions.equals("")) || (Integer.parseInt(numberOfQuestions) > numOfCountires) ) {
                    Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                } else {
                    Intent guessTheFlag = new Intent(getApplicationContext(), GuessTheFlagContinentActivity.class);
                    guessTheFlag.putStringArrayListExtra("regions", regions);
                    guessTheFlag.putExtra("numberOfQuestions", String.valueOf(numberOfQuestions));
                    startActivity(guessTheFlag);
                }

            }
        });
    }


}