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

public class GuessTheFlagContinentActivity extends AppCompatActivity {

    private static final String TAG = "FlagsOfEurope";
    private ImageView flag;
    private ImageView returnArr;
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    private Button answer5;
    private Button answer6;
    private String correctAnswer;
    private TextView correctTxt;
    private TextView contName;
    private TextView question;
    private int questionnumber;
    private int numberOfCorrect;
    private boolean checkGenerate;
    private ArrayList<String> pngURL = new ArrayList<String>();
    private ArrayList<String> nameArray = new ArrayList<>();
    private ArrayList<String> regions = new ArrayList<>();
    private ArrayList<String> answers = new ArrayList<>();
    private final String BASEURL = "https://restcountries.com/v3.1/region/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag_continent);

        flag = (ImageView) findViewById(R.id.europeFlags);
        regions = getIntent().getStringArrayListExtra("regions");

        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);
        answer5 = (Button) findViewById(R.id.answer5);
        answer6 = (Button) findViewById(R.id.answer6);

        correctTxt = (TextView) findViewById(R.id.correctTxt);

        questionnumber = 1;
        numberOfCorrect = 0;

        checkGenerate = true;

        question = (TextView) findViewById(R.id.question);

        returnArr = (ImageView) findViewById(R.id.returnArr);

        contName = (TextView) findViewById(R.id.continentName);
        contName.setText("Guess the Flag - " + regions.get(0));



        returnArr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ReturnScreen.class);
                intent.putExtra("class", getLocalClassName());
                startActivity(intent);
            }
        });

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer1.getText().equals(correctAnswer)) {
                    numberOfCorrect += 1;
                    answer1.setBackgroundColor(getResources().getColor(R.color.green));
                }
                if (checkGenerate == true) {
                    generateNewQuestion();
                }

//                questionnumber +=1;
//                question.setText("Question "+questionnumber+"/"+nameArray.size());
//                correctTxt.setText("Correct: " + numberOfCorrect);
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                renderImage();
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer2.getText().equals(correctAnswer)) {
                    numberOfCorrect += 1;
                    answer2.setBackgroundColor(getResources().getColor(R.color.green));
                }
                if (checkGenerate == true) {
                    generateNewQuestion();
                }
//                questionnumber +=1;
//                question.setText("Question "+questionnumber+"/"+nameArray.size());
//                correctTxt.setText("Correct: " + numberOfCorrect);
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                renderImage();
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer3.getText().equals(correctAnswer)) {
                    numberOfCorrect += 1;
                    answer3.setBackgroundColor(getResources().getColor(R.color.green));
                }
                if (checkGenerate == true) {
                    generateNewQuestion();
                }
//                questionnumber +=1;
//                question.setText("Question "+questionnumber+"/"+nameArray.size());
//                correctTxt.setText("Correct: " + numberOfCorrect);
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                renderImage();
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer4.getText().equals(correctAnswer)) {
                    numberOfCorrect += 1;
                    answer4.setBackgroundColor(getResources().getColor(R.color.green));
                }

                if (checkGenerate == true) {
                    generateNewQuestion();
                }
//                questionnumber +=1;
//                question.setText("Question "+questionnumber+"/"+nameArray.size());
//                correctTxt.setText("Correct: " + numberOfCorrect);
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                renderImage();
            }
        });

        answer5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer5.getText().equals(correctAnswer)) {
                    numberOfCorrect += 1;
                    answer5.setBackgroundColor(getResources().getColor(R.color.green));
                }
                if (checkGenerate == true) {
                    generateNewQuestion();
                }
//                questionnumber +=1;
//                question.setText("Question "+questionnumber+"/"+nameArray.size());
//                correctTxt.setText("Correct: " + numberOfCorrect);
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                renderImage();
            }
        });

        answer6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answer6.getText().equals(correctAnswer)) {
                    numberOfCorrect += 1;
                    answer6.setBackgroundColor(getResources().getColor(R.color.green));
                }
                if (checkGenerate == true) {
                    generateNewQuestion();
                }
//                questionnumber +=1;
//                question.setText("Question "+questionnumber+"/"+nameArray.size());
//                correctTxt.setText("Correct: " + numberOfCorrect);
//                try {
//                    TimeUnit.SECONDS.sleep(2);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                renderImage();

            }
        });

        for (String region : regions) {
            getFlags(BASEURL + region);
        }


    }

    private void getFlags(String urlParam) {
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


        for (HashMap<String, String> _mapNames : nameResponse) {
            for (Map.Entry pair : _mapNames.entrySet()) {
                if (pair.getKey().equals("common")) {
                    nameArray.add(String.valueOf(pair.getValue()));
                }
            }
        }
        question.setText("Question " + questionnumber + "/" + nameArray.size());
        correctTxt.setText("Correct: " + numberOfCorrect);
        //Log.e(TAG, String.valueOf(nameArray));
        renderImage();
    }

    private void renderImage() {

        for (int i = 0; i < 6; i++) {
            answers.add("placeholder");
        }
        Integer randNr = new Random().nextInt(pngURL.size());
        String randomURL = pngURL.get(randNr);
        pngURL.remove(randomURL);
        Picasso.get().load(randomURL).into(flag);
        correctAnswer = nameArray.get(randNr);
        nameArray.remove(nameArray.get(randNr));
        answers.set(0, correctAnswer);
        for (int f = 1; f < 6; f++) {
            answers.set(f, nameArray.get(new Random().nextInt(nameArray.size())));
        }
        nameArray.add(correctAnswer);
        setButtonText();

        //Log.e(TAG, String.valueOf(answers));
    }

    private void setButtonText() {
        Integer randNr = new Random().nextInt(answers.size());
        answer1.setText(answers.get(randNr));
        answers.remove(answer1.getText());
        randNr = new Random().nextInt(answers.size());
        answer2.setText(answers.get(randNr));
        answers.remove(answer2.getText());
        randNr = new Random().nextInt(answers.size());
        answer3.setText(answers.get(randNr));
        answers.remove(answer3.getText());

        randNr = new Random().nextInt(answers.size());
        answer4.setText(answers.get(randNr));
        answers.remove(answer4.getText());
        randNr = new Random().nextInt(answers.size());
        answer5.setText(answers.get(randNr));
        answers.remove(answer5.getText());
        randNr = new Random().nextInt(answers.size());
        answer6.setText(answers.get(randNr));
        answers.remove(answer6.getText());

    }

    private void generateNewQuestion() {
        questionnumber += 1;
        question.setText("Question " + questionnumber + "/" + nameArray.size());
        correctTxt.setText("Correct: " + numberOfCorrect);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        renderImage();
    }

    


}