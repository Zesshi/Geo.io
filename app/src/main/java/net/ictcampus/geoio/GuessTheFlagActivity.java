package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.util.concurrent.TimeUnit;

public class GuessTheFlagActivity extends AppCompatActivity {

    private static final String TAG = "Flag";
    private ImageView flagImg;
    private TextView textView2;
    private TextView correct;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private Button button6;
    private ArrayList<String> countries = new ArrayList<String>();
    private ArrayList<String> pngURL = new ArrayList<String>();
    private int questionNumber;
    private int rightAnswer;
    private String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.wtf(TAG, "23");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag);
        flagImg = (ImageView) findViewById(R.id.flagImg);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        ImageView returnImg = (ImageView) findViewById(R.id.returnImg);
        textView2 = (TextView) findViewById(R.id.textView2);
        correct = (TextView) findViewById(R.id.correct);
        questionNumber = 1;
        rightAnswer = 0;


        returnImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReturnScreen.class);
                intent.putExtra("class", getLocalClassName());
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button1.getText().equals(country)) {
                    rightAnswer += 1;
                }
                questionNumber += 1;
                textView2.setText("Question " + questionNumber + "/" + countries.size());
                correct.setText("Correct: " + rightAnswer);
                renderGame();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button2.getText().equals(country)) {
                    rightAnswer += 1;
                }
                questionNumber += 1;
                textView2.setText("Question " + questionNumber + "/" + countries.size());
                correct.setText("Correct: " + rightAnswer);
                renderGame();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button3.getText().equals(country)) {
                    rightAnswer += 1;
                }
                questionNumber += 1;
                textView2.setText("Question " + questionNumber + "/" + countries.size());
                correct.setText("Correct: " + rightAnswer);
                renderGame();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button4.getText().equals(country)) {
                    rightAnswer += 1;
                }
                questionNumber += 1;
                textView2.setText("Question " + questionNumber + "/" + countries.size());
                correct.setText("Correct: " + rightAnswer);
                renderGame();
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button5.getText().equals(country)) {
                    rightAnswer += 1;
                }
                questionNumber += 1;
                textView2.setText("Question " + questionNumber + "/" + countries.size());
                correct.setText("Correct: " + rightAnswer);
                renderGame();
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button6.getText().equals(country)) {
                    rightAnswer += 1;
                }
                questionNumber += 1;
                textView2.setText("Question " + questionNumber + "/" + countries.size());
                correct.setText("Correct: " + rightAnswer);
                renderGame();
            }
        });

        getJson("https://restcountries.com/v3.1/all");

    }

    private void renderGame() {
        if ( questionNumber == (countries.size() + 1) ){
            Intent intent = new Intent(getApplicationContext(), ResultScreenActivity.class);
            intent.putExtra("correctAnswers", String.valueOf(rightAnswer));
            intent.putExtra("numbOfQuestions", String.valueOf(countries.size()));
            finish();
            startActivity(intent);
        }
        ArrayList<String> answers = new ArrayList<String>();
        for (int i = 0; i < 6; i++) {
            answers.add("placeholder");
        }
        Integer index = new Random().nextInt(pngURL.size());

        String randomURL = pngURL.get(index);
        pngURL.remove(pngURL.get(index));
        Picasso.get().load(randomURL).into(flagImg);

        country = countries.get(index);
        countries.remove(countries.get(index));
        answers.set(0, country);

        for (int i = 1; i < 6; i ++) {
            Integer random = new Random().nextInt(countries.size());
            answers.set(i, countries.get(random));
        }
        countries.add(country);

        Integer random = new Random().nextInt(answers.size());
        button1.setText(answers.get(random));
        answers.remove(answers.get(random));

        random = new Random().nextInt(answers.size());
        button2.setText(answers.get(random));
        answers.remove(answers.get(random));

        random = new Random().nextInt(answers.size());
        button3.setText(answers.get(random));
        answers.remove(answers.get(random));

        random = new Random().nextInt(answers.size());
        button4.setText(answers.get(random));
        answers.remove(answers.get(random));

        random = new Random().nextInt(answers.size());
        button5.setText(answers.get(random));
        answers.remove(answers.get(random));

        random = new Random().nextInt(answers.size());
        button6.setText(answers.get(random));
        answers.remove(answers.get(random));
    }

    private void getJson(String urlParam) {
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
                    }
                });
            }
        });
    }

    public void parseJson(String jsonString) {
        List<HashMap<String, String>> responseImg = new ArrayList<>();
        List<HashMap<String, String>> responseName = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject flg = jsonObject.getJSONObject("flags");
                JSONObject name = jsonObject.getJSONObject("name");
                Iterator<?> iteratorFlg = flg.keys();
                Iterator<?> iteratorName = name.keys();
                HashMap<String, String> mapFlg = new HashMap<>();
                HashMap<String, String> mapName = new HashMap<>();

                while (iteratorFlg.hasNext()) {
                    Object key = iteratorFlg.next();
                    Object value = flg.get(key.toString());
                    mapFlg.put(key.toString(), value.toString());
                }

                while (iteratorName.hasNext()) {
                    Object key = iteratorName.next();
                    Object value = name.get(key.toString());
                    mapName.put(key.toString(), value.toString());
                }

                responseImg.add(mapFlg);
                responseName.add(mapName);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (HashMap<String, String> _map : responseImg) {
            for (Map.Entry pair : _map.entrySet()) {
                if (pair.getKey().equals("png")) {
                    pngURL.add(String.valueOf(pair.getValue()));
                }
            }
        }

        for (HashMap<String, String> _map2 : responseName) {
            for (Map.Entry pair : _map2.entrySet()) {
                if (pair.getKey().equals("common")) {
                    countries.add(String.valueOf(pair.getValue()));
                }
            }
        }
        textView2.setText("Question " + questionNumber + "/" + countries.size());
        correct.setText("Correct: " + rightAnswer);
        renderGame();
    }
}

























