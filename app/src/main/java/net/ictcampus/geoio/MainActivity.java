package net.ictcampus.geoio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private Dialog dialog;
    private String className;
    private Editable numberOfQuestions;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button guessTheFlagButton = (Button) findViewById(R.id.button);
        guessTheFlagButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                className = "GuessTheFlagActivity";
                showPopUp();
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
                className = "GuessTheCapital";
                showPopUp();

            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.languages_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
                if (className.equals("GuessTheFlagActivity")) {
                    Intent intent = new Intent(getApplicationContext(), GuessTheFlagActivity.class);
                    intent.putExtra("numberOfQuestions", "max");
                    intent.putExtra("language", spinner.getSelectedItem().toString());
                    startActivity(intent);
                } else if (className.equals("GuessTheCapital")) {
                    Intent intent = new Intent(getApplicationContext(), GuessTheCapital.class);
                    intent.putExtra("numberOfQuestions", "max");
                    intent.putExtra("language", spinner.getSelectedItem().toString());
                    startActivity(intent);
                }
            }
        });

        Button submit = (Button) popUp.findViewById(R.id.buttonSubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numberOfQuestions = count.getText();

                if (className.equals("GuessTheFlagActivity")) {
                    Intent intent = new Intent(getApplicationContext(), GuessTheFlagActivity.class);
                    intent.putExtra("numberOfQuestions", String.valueOf(numberOfQuestions));
                    intent.putExtra("language", spinner.getSelectedItem().toString());
                    startActivity(intent);
                } else if (className.equals("GuessTheCapital")) {
                    Intent intent = new Intent(getApplicationContext(), GuessTheCapital.class);
                    intent.putExtra("numberOfQuestions", String.valueOf(numberOfQuestions));
                    intent.putExtra("language", spinner.getSelectedItem().toString());
                    startActivity(intent);
                }
            }
        });

    }
}