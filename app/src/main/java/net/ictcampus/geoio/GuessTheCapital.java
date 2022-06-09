package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    ArrayList<String> answers = new ArrayList<String>();
    private static final String TAG = "Capitals";
    private ArrayList<String> countries = new ArrayList<String>();
    private TextView capitals, textView_cap2;
    private ArrayList<String> Caps = new ArrayList<String>();
    private ArrayList<String> countrys = new ArrayList<>();
    private final String BASEURL = "https://restcountries.com/v3.1/all";
    private Button button_cap1, button_cap2, button_cap3, button_cap4, button_cap5, button_cap6;
    private ArrayList<Button> buttons = new ArrayList<Button>();
    private String country;
    private TextView correct;
    List<JSONArray> response = new ArrayList<>();
    private int questionNumber, realQuestionNumber, rightAnswer, skippedQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_capital);

        capitals = (TextView) findViewById(R.id.capital);
        countrys = getIntent().getStringArrayListExtra("countrys");
        button_cap1 = (Button) findViewById(R.id.button_cap1);
        button_cap2 = (Button) findViewById(R.id.button_cap2);
        button_cap3 = (Button) findViewById(R.id.button_cap3);
        button_cap4 = (Button) findViewById(R.id.button_cap4);
        button_cap5 = (Button) findViewById(R.id.button_cap5);
        button_cap6 = (Button) findViewById(R.id.button_cap6);
        textView_cap2 = (TextView) findViewById(R.id.textView_cap2);
        correct = (TextView) findViewById(R.id.correct1);
        getCapital(BASEURL);
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


        button_cap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button_cap1.getText().equals(country)) {
                    rightAnswer += 1;
                }
                questionNumber += 1;
                textView_cap2.setText("Question " + questionNumber + "/" + countries.size());
                correct.setText("Correct: " + rightAnswer);
                renderGame();
            }
        });

        for (Button button: buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (button.getText().equals(country)) {
                        rightAnswer += 1;
                    }
                    questionNumber += 1;
                    realQuestionNumber += 1;
                    textView_cap2.setText("Question " + questionNumber + "/" + countries.size());
                    correct.setText("Correct: " + rightAnswer);
                    renderGame();
                }
            });
        }
    }

    private void renderGame() {
        if (questionNumber == (countries.size() + 1)) {
            Intent intent = new Intent(getApplicationContext(), ResultScreenActivity.class);
            intent.putExtra("correctAnswers", String.valueOf(rightAnswer));
            intent.putExtra("numbOfQuestions", String.valueOf(countries.size()));
            finish();
            startActivity(intent);
        }

        for (int i = 0; i < 6; i++) {
            answers.add("placeholder");
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

        putCapToButton(button_cap1);
        putCapToButton(button_cap2);
        putCapToButton(button_cap3);
        putCapToButton(button_cap4);
        putCapToButton(button_cap5);
        putCapToButton(button_cap6);

    }

    private void putCapToButton(Button button_cap1) {
        Integer random = new Random().nextInt(answers.size());
        button_cap1.setText(answers.get(random));
        answers.remove(answers.get(random));
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
                        Log.e("MSG", msg.toString());
                        parseJson(msg.toString());
                    }
                });
            }
        });
    }

    public void parseJson(String jsonString) {
        List<HashMap<String, String>> responseName = new ArrayList<>();

        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
            //Log.e(TAG, String.valueOf(jsonArray));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray capl = jsonObject.getJSONArray("capital");
                JSONObject name = jsonObject.getJSONObject("name");

                //Log.e(TAG, String.valueOf(jsonObject));
                response.add(capl);
                //Log.e("ARRAY", String.valueOf(response));


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

        //Log.e(TAG, String.valueOf(countries));
        //Log.e(TAG, String.valueOf(response));
        textView_cap2.setText("Question " + questionNumber + "/" + countries.size());
        correct.setText("Correct: " + rightAnswer);
        renderGame();
    }
}

