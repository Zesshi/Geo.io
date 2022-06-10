package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class GuessTheFlagActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "Flag";

    private ImageView flagImg;

    private TextView textView2, correct;

    private SensorManager sensorManager;
    private Sensor sensor;

    private Button button1, button2, button3, button4, button5, button6, nextButton, correctButton;

    private ArrayList<String> countries = new ArrayList<String>();
    private ArrayList<String> pngURL = new ArrayList<String>();
    private ArrayList<Button> buttons = new ArrayList<Button>();

    private int questionNumber, realQuestionNumber, rightAnswer, skippedQuestion, isClickAllowed;

    private float currentX, currentY, currentZ, lastX, lastY, lastZ, xDifference, yDifference, zDifference, shakeThreshold = 12f;

    private String country;

    private boolean isAccelerometerSensorAvailable, notFirstTime = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag);

        flagImg = (ImageView) findViewById(R.id.flagImg);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        nextButton = (Button) findViewById(R.id.next);
        buttons.add(button1);
        buttons.add(button2);
        buttons.add(button3);
        buttons.add(button4);
        buttons.add(button5);
        buttons.add(button6);
        ImageView returnImg = (ImageView) findViewById(R.id.returnImg);
        textView2 = (TextView) findViewById(R.id.textView2);
        correct = (TextView) findViewById(R.id.correct);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        questionNumber = 1;
        rightAnswer = 0;
        skippedQuestion = 0;
        realQuestionNumber = 0;
        isClickAllowed = 0;

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerSensorAvailable = true;
        } else {
            isAccelerometerSensorAvailable = false;
        }

        returnImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReturnScreen.class);
                intent.putExtra("class", getLocalClassName());
                intent.putExtra("Flaglist", pngURL);
                intent.putExtra("Namelist", countries);
                intent.putExtra("questionNumber", questionNumber);
                intent.putExtra("realQuestionNumber", realQuestionNumber);
                intent.putExtra("rightAnswer", rightAnswer);
                intent.putExtra("skippedQuestions", skippedQuestion);
                finish();
                startActivity(intent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
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
                        textView2.setText("Question " + questionNumber + "/" + countries.size());
                        correct.setText("Correct: " + rightAnswer);
                        showSolution(button);
                    }
                }
            });
        }

        getJson("https://restcountries.com/v3.1/all");
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
            answers.add("placeholder");
        }
        Integer index = new Random().nextInt(pngURL.size());
        String randomURL = pngURL.get(index);
        pngURL.remove(pngURL.get(index));
        Picasso.get().load(randomURL).into(flagImg);

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

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currentX = sensorEvent.values[0];
        currentY = sensorEvent.values[1];
        currentZ = sensorEvent.values[2];

        if (notFirstTime) {
            xDifference = Math.abs(lastX - currentX);
            yDifference = Math.abs(lastY - currentY);
            zDifference = Math.abs(lastZ - currentZ);

            if ((xDifference > shakeThreshold && yDifference > shakeThreshold) ||
                    (xDifference > shakeThreshold && zDifference > shakeThreshold) ||
                    (yDifference > shakeThreshold && zDifference > shakeThreshold)) {
                skippedQuestion += 1;
                renderGame();
                questionNumber += 1;
                Toast.makeText(getApplicationContext(), "Skipped Question", Toast.LENGTH_SHORT).show();
            }
        }

        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;
        notFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isAccelerometerSensorAvailable) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isAccelerometerSensorAvailable) {
            sensorManager.unregisterListener(this);
        }
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

    //@SuppressLint("SetTextI18n")
    public void parseJson(String jsonString) {
        List<HashMap<String, String>> responseImg = new ArrayList<>();
        List<HashMap<String, String>> responseName = new ArrayList<>();
        JSONArray jsonArray = null;


        try {
            jsonArray = new JSONArray(jsonString);
            Log.e("msg", String.valueOf(jsonArray));
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
        Log.e("countrys", String.valueOf(countries));

        textView2.setText("Question " + questionNumber + "/" + countries.size());
        correct.setText("Correct: " + rightAnswer);
        renderGame();
    }
}

























