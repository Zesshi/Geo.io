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
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuessTheFlagEuropeActivity extends AppCompatActivity {

    private static final  String TAG = "FlagsOfEurope";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_flag_europe);

        String url = "https://restcountries.com/v3.1/name/switzerland";

        ImageView flag = (ImageView) findViewById(R.id.europeFlags);
        //Picasso.get().load(schweiz).into(flag);

        parseJson(url);
    }


    public void parseJson(String jsonString) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
            JSONObject flags = jsonObject.getJSONObject("flags");
            Iterator keys = flags.keys();
            while (keys.hasNext()) {
                String key = (String) keys.next();
                JSONObject subobject = flags.getJSONObject(key);
                String flagPNG = subobject.getString("png");
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

    }

}