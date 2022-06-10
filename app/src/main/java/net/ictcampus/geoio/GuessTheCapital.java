package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuessTheCapital extends AppCompatActivity {

    private static final String TAG = "Capitals";
    private TextView capitals, textView_cap2;
    private Button button_cap1, button_cap2, button_cap3, button_cap4, button_cap5, button_cap6, correctButton, next_cap;
    private int questionNumber, realQuestionNumber, rightAnswer, skippedQuestion, isClickAllowed;
    private final String BASEURL = "https://restcountries.com/v3.1/all";

    List<JSONArray> response = new ArrayList<>();

    private ArrayList<String> countries = new ArrayList<String>();
    private ArrayList<String> Caps = new ArrayList<String>();
    private ArrayList<String> countrys = new ArrayList<>();
    private ArrayList<Button> buttons = new ArrayList<Button>();

    private String country;
    private TextView correct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_capital);

        countrys = getIntent().getStringArrayListExtra("countrys");
        ImageView returnImg = (ImageView) findViewById(R.id.returnImg_cap);

        button_cap1 = (Button) findViewById(R.id.button_cap1);
        button_cap2 = (Button) findViewById(R.id.button_cap2);
        button_cap3 = (Button) findViewById(R.id.button_cap3);
        button_cap4 = (Button) findViewById(R.id.button_cap4);
        button_cap5 = (Button) findViewById(R.id.button_cap5);
        button_cap6 = (Button) findViewById(R.id.button_cap6);
        next_cap = (Button) findViewById(R.id.next_cap);


        textView_cap2 = (TextView) findViewById(R.id.textView_cap2);
        correct = (TextView) findViewById(R.id.correct1);
        capitals = (TextView) findViewById(R.id.capital);


        buttons.add(button_cap1);
        buttons.add(button_cap2);
        buttons.add(button_cap3);
        buttons.add(button_cap4);
        buttons.add(button_cap5);
        buttons.add(button_cap6);


        questionNumber = 1;
        rightAnswer = 0;
        skippedQuestion = 0;
        realQuestionNumber = 0;
        isClickAllowed = 0;

        getCapital(BASEURL);


        returnImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReturnScreen.class);
                intent.putExtra("class", getLocalClassName());
                intent.putExtra("Namelist", countries);
                //  intent.putExtra("Flaglist",  response);
                intent.putExtra("questionNumber", questionNumber);
                intent.putExtra("realQuestionNumber", realQuestionNumber);
                intent.putExtra("rightAnswer", rightAnswer);
                intent.putExtra("skippedQuestions", skippedQuestion);
                finish();
                startActivity(intent);
            }
        });


        next_cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                renderGame();
            }
        });

        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isClickAllowed == 0) {
                        isClickAllowed = 1;
                        if (button.getText().equals(country)) {
                            rightAnswer += 1;
                        }
                        questionNumber += 1;
                        realQuestionNumber += 1;
                        textView_cap2.setText("Question " + questionNumber + "/" + countries.size());
                        correct.setText("Correct: " + rightAnswer);
                        showSolution(button);
                    }
                }
            });
        }
    }

    private void showSolution(Button button) {
        if (button.getText().equals(country)) {
            button.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            button.setBackgroundColor(getResources().getColor(R.color.red));
            correctButton.setBackgroundColor(getResources().getColor(R.color.green));
        }
    }

    private void renderGame() {


        for (Button button : buttons) {
            button.setBackgroundColor(getResources().getColor(R.color.light_grey));
        }

        isClickAllowed = 0;


        if (questionNumber == (countries.size() + 1)) {
            Intent intent = new Intent(getApplicationContext(), ResultScreenActivity.class);
            intent.putExtra("correctAnswers", String.valueOf(rightAnswer));
            intent.putExtra("skipped", String.valueOf(skippedQuestion));
            intent.putExtra("numbOfQuestions", String.valueOf(realQuestionNumber));
            finish();
            startActivity(intent);
        }

        ArrayList<String> answers = new ArrayList<String>();
        for (int i = 0; i < 6; i++) {
            answers.add("x");
        }


        Integer index = new Random().nextInt(response.size());
        JSONArray randomURL = response.get(new Random().nextInt(response.size()));
        response.remove(response.get(index));
        capitals.setText((randomURL).toString().replace("[", "").replace("]", "").replace("\"", "").trim());

        country = countries.get(index);
        countries.remove(countries.get(index));
        answers.set(0, country);


        for (int i = 1; i < 6; i++) {
            Integer random = new Random().nextInt(countries.size());
            answers.set(i, countries.get(random));
        }
        countries.add(country);


        for (Button button : buttons) {
            Integer random = new Random().nextInt(answers.size());
            if (answers.get(random).equals(country)) {
                correctButton = button;
            }

            button.setText(answers.get(random));
            answers.remove(answers.get(random));

        }

    }

    private void getCapital(String urlParam) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                StringBuilder msg = new StringBuilder();
                HttpURLConnection urlConnection;
                try {
                    URL url = new URL(urlParam);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        msg.append(line);
                    }
                } catch (Exception e) {
                    Log.v(TAG, e.toString());
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        parseJson(msg.toString());
                        Log.v("countrys", String.valueOf(countries));

                    }
                });
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void parseJson(String jsonString) {
        List<HashMap<String, String>> responseName = new ArrayList<>();
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(jsonString);


            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray capl = jsonObject.getJSONArray("capital");
                JSONObject name = jsonObject.getJSONObject("name");
                //Log.e(TAG, String.valueOf(jsonObject));

                Log.e("CAPITALS", String.valueOf(capl));
                response.add(capl);

                Log.e("numberOfLoopThroughs", String.valueOf(jsonArray.length()));


                Iterator<?> iteratorName = name.keys();
                HashMap<String, String> mapName = new HashMap<>();


                while (iteratorName.hasNext()) {
                    Object key = iteratorName.next();
                    Object value = name.get(key.toString());
                    mapName.put(key.toString(), value.toString());
                }

                responseName.add(mapName);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        for (HashMap<String, String> _map2 : responseName) {
            for (Map.Entry pair : _map2.entrySet()) {
                if (pair.getKey().equals("common")) {
                    countries.add(String.valueOf(pair.getValue()));
                }
            }
        }
        Log.e("countrys", String.valueOf(countries));

        //Log.e(TAG, String.valueOf(countries));
        //Log.e(TAG, String.valueOf(response));
        textView_cap2.setText("Question " + questionNumber + "/" + countries.size());
        correct.setText("Correct: " + rightAnswer);
        renderGame();
    }
}

