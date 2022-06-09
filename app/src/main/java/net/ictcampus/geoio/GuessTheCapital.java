package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

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
    private TextView capitals;
    private ArrayList<String> Caps = new ArrayList<String>();
    private ArrayList<String> countrys = new ArrayList<>();
    private final String BASEURL = "https://restcountries.com/v3.1/all";
    private TextView button_cap1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_capital);

        capitals = (TextView) findViewById(R.id.capital);
        countrys = getIntent().getStringArrayListExtra("countrys");
        button_cap1 = (TextView) findViewById(R.id.button_cap1);

        getCapital(BASEURL);
        Log.wtf(TAG, String.valueOf(Caps));

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
                    }
                });
            }
        });
    }

    public void parseJson(String jsonString) {
        List<JSONArray> response = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray capl = jsonObject.getJSONArray("capital");
                Log.e(TAG, String.valueOf(capl));
                // Log.e(TAG, String.valueOf(jsonObject));

                response.add(capl);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray randomURL = response.get(new Random().nextInt(response.size()));
        capitals.setText((randomURL).toString().replace("[", "") .replace("]", "").replace("\"", "") .trim());

    }
}