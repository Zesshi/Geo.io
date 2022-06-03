package net.ictcampus.geoio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class GuessTheCapital extends AppCompatActivity {
    private static final String TAG = "";
    Button button_cap1, button_cap2, button_cap3, button_cap4, button_cap5, button_cap6;
    TextView textView_cap1, textView_cap2, textView_cap3;

    //asigning class variables to xml values
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_the_capital);

        button_cap1 = findViewById(R.id.button_cap1);
        button_cap2 = findViewById(R.id.button_cap2);
        button_cap3 = findViewById(R.id.button_cap3);
        button_cap4 = findViewById(R.id.button_cap4);
        button_cap5 = findViewById(R.id.button_cap5);
        button_cap6 = findViewById(R.id.button_cap6);

        //textView_cap1=findViewById(R.id.textView_cap1);
        textView_cap2 = findViewById(R.id.textView_cap2);
        textView_cap3 = findViewById(R.id.textView_cap3);

        //click listener for each button
        button_cap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(GuessTheCapital.this);
                String url = "https://restcountries.com/v3.1/name/switzerland";

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        String name="";
                        try {
                            JSONObject capital = response.getJSONObject(0);
                             name= capital.getString("name");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Toast.makeText(GuessTheCapital.this,"name= "+name ,Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuessTheCapital.this,"something wrong",Toast.LENGTH_SHORT).show();

                    }
                });
                queue.add(request);

                /*
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(GuessTheCapital.this, response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GuessTheCapital.this, "Error iz da", Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the request to the RequestQueue.

                 */
              //  Toast.makeText(GuessTheCapital.this, "you clicked me1", Toast.LENGTH_SHORT).show();
            }


        });








        button_cap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GuessTheCapital.this, "you clicked me2", Toast.LENGTH_SHORT).show();
            }
        });

        button_cap3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GuessTheCapital.this, "you clicked me3", Toast.LENGTH_SHORT).show();
            }
        });

        button_cap4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GuessTheCapital.this, "you clicked me4", Toast.LENGTH_SHORT).show();
            }
        });

        button_cap5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GuessTheCapital.this, "you clicked me5", Toast.LENGTH_SHORT).show();
            }
        });

        button_cap6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GuessTheCapital.this, "you clicked me6", Toast.LENGTH_SHORT).show();
            }
        });

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
            }
        });


    }


}