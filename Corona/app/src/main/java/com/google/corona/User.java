package com.google.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User extends AppCompatActivity {

    public Date update_date_time;

    public EditText DateAdd;
    public EditText newCases;
    public EditText total;
    public EditText deaths;
    public EditText newDeaths;
    public EditText recovered;
    public EditText DateDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);


        DateAdd = findViewById(R.id.newdate);
        newCases = findViewById(R.id.newCases);
        total = findViewById(R.id.total);
        deaths = findViewById(R.id.deathsTotal);
        newDeaths = findViewById(R.id.deathsNew);
        recovered = findViewById(R.id.recovered);
        DateDelete = findViewById(R.id.date);

        DateAdd.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_NORMAL);

        Date today=new Date();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://92.168.8.141:8080/getDailyReport/"+today.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Toast.makeText(User.this, "Successfully Searched", Toast.LENGTH_LONG).show();
                            Log.d("VOLLEY", String.valueOf(jsonArray));

                            newCases.setText(jsonArray.getString(0));
                            total.setText(jsonArray.getString(1));
                            deaths.setText(jsonArray.getString(2));
                            newDeaths.setText(jsonArray.getString(3));
                            recovered.setText(jsonArray.getString(4));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("VOLLEY", String.valueOf(error));
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        queue.start();
    }


    public void search(View view){

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            update_date_time = df.parse(DateDelete.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.8.141:8080/getDailyReport/"+update_date_time;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            Toast.makeText(User.this, "Successfully Searched", Toast.LENGTH_LONG).show();
                            Log.d("VOLLEY", String.valueOf(jsonArray));

                            newCases.setText(jsonArray.getString(0));
                            total.setText(jsonArray.getString(1));
                            deaths.setText(jsonArray.getString(2));
                            newDeaths.setText(jsonArray.getString(3));
                            recovered.setText(jsonArray.getString(4));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                300000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}