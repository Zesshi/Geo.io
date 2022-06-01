package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuessTheFlagActivity extends AppCompatActivity {

    private static final String TAG = "Flag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag);

        getFlag("https://restcountries.com/v3.1/all");
    }

    private void getFlag(String urlParam) {
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

                    while((line = reader.readLine()) != null) {
                        msg.append(line);
                    }
                } catch ( Exception e) {
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

    public void parseJson(String jsonstring) {
        ArrayAdapter temps = new ArrayAdapter<String>(this, android.R.layout)
    }
}

























