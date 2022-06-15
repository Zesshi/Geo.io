package net.ictcampus.geoio;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuessTheFlagContinentActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "FlagsOfEurope";
    private ImageView flag, returnArr;
    private Button answer1, answer2, answer3, answer4, answer5, answer6, correctBtn, nextQ;
    private String correctAnswer, combined;
    private TextView correctTxt, contName, question;
    private SensorManager sensorManager;
    private Sensor sensor;
    private int questionnumber;
    private int numberOfCorrect;
    private int skippedQuestions;
    private int answeredQuestions;
    private int numbOfQuestions;
    private String numberOfQuestions;
    private float currentX, currentY, currentZ, lastX, lastY, lastZ, xDifference, yDifference, zDiffernece, shakeThreshold = 12f;
    private ArrayList<String> pngURL = new ArrayList<String>();
    private ArrayList<String> nameArray = new ArrayList<>();
    private ArrayList<String> regions = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();
    private ArrayList<String> tempAnswers = new ArrayList<>();
    private ArrayList<Button> buttons = new ArrayList<>();
     private ArrayList<String> json = new ArrayList<>();
    private boolean isAccelerometerAvailable, notFirstTime = false, clickAllowed = true;
    private final String BASEURL = "https://restcountries.com/v3.1/region/";

    private AlertDialog.Builder dialogBuilder;
    private Dialog dialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag_continent);

        Toast.makeText(getApplicationContext(), "Shake phone to skip question!", Toast.LENGTH_SHORT).show();

        flag = (ImageView) findViewById(R.id.europeFlags);
        regions = getIntent().getStringArrayListExtra("regions");

        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);
        answer5 = (Button) findViewById(R.id.answer5);
        answer6 = (Button) findViewById(R.id.answer6);

        nextQ = (Button) findViewById(R.id.nextQuest);

        buttons.add(answer1);
        buttons.add(answer2);
        buttons.add(answer3);
        buttons.add(answer4);
        buttons.add(answer5);
        buttons.add(answer6);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        correctTxt = (TextView) findViewById(R.id.correctTxt);

        questionnumber = 1;
        numberOfCorrect = 0;
        skippedQuestions = 0;
        answeredQuestions = 0;

        intent = getIntent();
        numberOfQuestions = intent.getStringExtra("numberOfQuestions");

        question = (TextView) findViewById(R.id.question);

        returnArr = (ImageView) findViewById(R.id.returnArr);

        contName = (TextView) findViewById(R.id.continentName);
        contName.setText("Guess the Flag - " + regions.get(0));

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            isAccelerometerAvailable = true;
        } else {
            isAccelerometerAvailable = false;
        }


        returnArr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showPupUp();
            }
        });

        for (String region : regions) {
            getAntarctica();
            getFlags(BASEURL + region);
        }




        /*
        if (regions.size() >= 2) {
            getFlags(regions);
        } else {
            getFlags(regions.get(0));
        }*/

        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickAllowed) {
                        clickAllowed = false;
                        if (button.getText().equals(correctAnswer)) {
                            numberOfCorrect += 1;
                            button.setBackgroundColor(getResources().getColor(R.color.green));

                        } else {
                            button.setBackgroundColor(getResources().getColor(R.color.red));
                            correctBtn.setBackgroundColor(getResources().getColor(R.color.green));

                        }
                        answeredQuestions += 1;
                        correctTxt.setText("Correct: " + numberOfCorrect);


                    }

                }
            });
        }


        nextQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clickAllowed) {
                    questionnumber += 1;
                    question.setText("Question " + questionnumber + "/" + numbOfQuestions);
                    renderImage();
                }


                for (Button button : buttons) {
                    button.setBackgroundColor(getResources().getColor(R.color.light_grey));
                }


            }
        });


    }

    private void getAntarctica() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                StringBuilder msg = new StringBuilder();
                HttpURLConnection urlConnection;
                try {
                    URL url = new URL(BASEURL+"antarctic");
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
                combined = msg.toString();

            }
        });
    }

    private void getFlags(String urlParam) {
        Log.e("sdfgh", String.valueOf(regions.get(0)));



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
                        if (regions.get(0).equals("oceania")) {
                            String yeye = (combined.substring(0,combined.length()-1 ))+"," + msg.toString().substring(1) ;
                            Log.e(TAG, yeye);
                            parseJson(yeye);
                        } else {
                            parseJson(msg.toString());
                        }


                    }
                });
            }
        });
    }


    /*
    private void getMultipleCont(ArrayList<String> urlParams) {

        Log.e(TAG, String.valueOf(urlParams));
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        for (String urlParam : urlParams) {
            Log.e("YEYEY", String.valueOf(urlParam));
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    StringBuilder msg = new StringBuilder();
                    HttpURLConnection urlConnection;
                    try {
                        URL url = new URL(BASEURL + urlParam);
                        Log.e("Here is the URL", String.valueOf(url));
                        urlConnection = (HttpURLConnection) url.openConnection();
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = reader.readLine())!= null) {
                            msg.append(line);
                            Log.e(TAG, String.valueOf(msg));
                        }
                    } catch (Exception e) {
                        Log.v(TAG, e.toString());
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            json.add(msg.toString());
                            Log.e("JSON HERE", String.valueOf(json));
                        }
                    });
                }
            });

        }
    } */




    public void parseJson(String jsonString) {

        List<HashMap<String, String>> response = new ArrayList<>();
        List<HashMap<String, String>> nameResponse = new ArrayList<>();
        //List<HashMap<String, String>> flagresp = new ArrayList<>();
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject flg = jsonObject.getJSONObject("flags");
                JSONObject names = jsonObject.getJSONObject("name");
                //Log.e(TAG, String.valueOf(jsonObject));
                Iterator<?> iterator = flg.keys();
                Iterator<?> nameIterator = names.keys();


                HashMap<String, String> mapNames = new HashMap<>();
                HashMap<String, String> map = new HashMap<>();

                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    Object value = flg.get(key.toString());
                    map.put(key.toString(), value.toString());
                    //Log.e(TAG, String.valueOf(map));

                }

                //Log.v(TAG, String.valueOf(map));

                while (nameIterator.hasNext()) {
                    Object key = nameIterator.next();
                    Object value = names.get(key.toString());
                    mapNames.put(key.toString(), value.toString());
                }
                response.add(map);
                nameResponse.add(mapNames);
                //Log.e(TAG, String.valueOf(nameResponse));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        for (HashMap<String, String> _map : response) {
            for (Map.Entry pair : _map.entrySet()) {
                if (pair.getKey().equals("png")) {
                    pngURL.add(String.valueOf(pair.getValue()));
                }
            }
        }

        Log.e(TAG, String.valueOf(pngURL.size()));

        for (HashMap<String, String> _mapNames : nameResponse) {
            for (Map.Entry pair : _mapNames.entrySet()) {
                if (pair.getKey().equals("common")) {
                    nameArray.add(String.valueOf(pair.getValue()));
                }
            }
        }

        if (numberOfQuestions.equals("max")) {
            numbOfQuestions = nameArray.size();
        } else {
            numbOfQuestions = Integer.valueOf(numberOfQuestions);
        }

        question.setText("Question " + questionnumber + "/" + numbOfQuestions);
        correctTxt.setText("Correct: " + numberOfCorrect);
        Log.e(TAG, String.valueOf(nameArray));
        renderImage();
    }

    private void renderImage() {

        Log.e("SIIIZE", String.valueOf(nameArray.size() + 1));
        Log.e("NR", String.valueOf(questionnumber));
        if (questionnumber == (numbOfQuestions +1)) {
            Intent resultScreen = new Intent(getApplicationContext(), ResultScreenActivity.class);
            resultScreen.putExtra("correctAnswers", String.valueOf(numberOfCorrect));
            resultScreen.putExtra("skipped", String.valueOf(skippedQuestions));
            resultScreen.putExtra("numbOfQuestions", String.valueOf(answeredQuestions));
            finish();
            startActivity(resultScreen);
        }
        for (int i = 0; i < 6; i++) {
            answers.add("placeholder");
        }

        clickAllowed = true;


        if (pngURL.size() != 0) {
            Integer randNr = new Random().nextInt(pngURL.size());

            String randomURL = pngURL.get(randNr);
            pngURL.remove(randomURL);
            Picasso.get().load(randomURL).into(flag);
            correctAnswer = nameArray.get(randNr);
            nameArray.remove(nameArray.get(randNr));
            for (int g = 0; g < nameArray.size(); g++) {
                tempAnswers.add(nameArray.get(g));
            }

            answers.set(0, correctAnswer);


            for (int f = 1; f < 6; f++) {
                Integer rand = new Random().nextInt(tempAnswers.size());
                answers.set(f, tempAnswers.get(rand));
                //Log.e(TAG, String.valueOf(tempAnswers));
                tempAnswers.remove(tempAnswers.get(rand));
                //Log.v(TAG, String.valueOf(tempAnswers));
            }

            for (int h = 0; h < tempAnswers.size(); h = 0) {
                tempAnswers.remove(tempAnswers.get(h));
            }

            nameArray.add(correctAnswer);
            setButtonText();
        }
        //Log.e(TAG, String.valueOf(answers));
    }

    private void setButtonText() {

        for (Button button : buttons) {
            button.setBackgroundColor(getResources().getColor(R.color.light_grey));
            Integer randNr = new Random().nextInt(answers.size());
            if (answers.get(randNr).equals(correctAnswer)) {
                correctBtn = button;
            }
            button.setText(answers.get(randNr));
            answers.remove(answers.get(randNr));


        }
        question.setText("Question " + questionnumber + "/" + numbOfQuestions);
        correctTxt.setText("Correct: " + numberOfCorrect);


    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        currentX = sensorEvent.values[0];
        currentY = sensorEvent.values[1];
        currentZ = sensorEvent.values[2];

        if (notFirstTime) {
            xDifference = Math.abs(lastX - currentX);
            yDifference = Math.abs(lastY - currentY);
            zDiffernece = Math.abs(lastZ - currentZ);

            if ((xDifference > shakeThreshold  && yDifference > shakeThreshold ) ||
                    (xDifference > shakeThreshold  && zDiffernece > shakeThreshold ) ||
                    (yDifference > shakeThreshold && zDiffernece > shakeThreshold )) {
                if (clickAllowed) {
                    clickAllowed = false;
                    Toast.makeText(getApplicationContext(), "Skipped Question", Toast.LENGTH_SHORT).show();
                    correctBtn.setBackgroundColor(getResources().getColor(R.color.green));
                    skippedQuestions += 1;
                    //questionnumber += 1;
                }


            }
        }
        lastX = currentX;
        lastY = currentY;
        lastZ = currentZ;
        notFirstTime = true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isAccelerometerAvailable) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isAccelerometerAvailable) {
            sensorManager.unregisterListener(this);
        }
    }

    private void showPupUp() {
        dialogBuilder = new AlertDialog.Builder(this);
        final View popUp = getLayoutInflater().inflate(R.layout.activity_return_screen, null);
        dialogBuilder.setView(popUp);
        dialog = dialogBuilder.create();
        dialog.show();

        Button keepbutton = (Button) popUp.findViewById(R.id.keepButton);
        keepbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button quitButton = (Button) popUp.findViewById(R.id.quitButton);
        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(menu);
            }
        });
    }





}