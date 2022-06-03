package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuessTheFlagContinentActivity extends AppCompatActivity {

    private static final String TAG = "FlagsOfEurope";
    private ImageView flag;
    private ArrayList<String> pngURL = new ArrayList<String>();
    private ArrayList<String> regions = new ArrayList<>();
    private final String BASEURL = "https://restcountries.com/v3.1/region/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag_europe);

        flag = (ImageView) findViewById(R.id.europeFlags);
        regions = getIntent().getStringArrayListExtra("regions");

        for (String region : regions){
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
        //List<HashMap<String, String>> flagresp = new ArrayList<>();
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONArray cont = jsonObject.getJSONArray("continents");
                JSONObject flg = jsonObject.getJSONObject("flags");
                Log.e(TAG, String.valueOf(jsonObject));



                Iterator<?> iterator = flg.keys();

                HashMap<String, String> map = new HashMap<>();

                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    Object value = flg.get(key.toString());
                    map.put(key.toString(), value.toString());
                    Log.e(TAG, String.valueOf(map));
                }

                response.add(map);


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

        renderImage();
    }

    private void renderImage() {
        String randomURL = pngURL.get(new Random().nextInt(pngURL.size()));
        Picasso.get().load(randomURL).into(flag);
    }

}