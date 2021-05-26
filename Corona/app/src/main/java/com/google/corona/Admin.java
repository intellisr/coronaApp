package com.google.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Admin extends AppCompatActivity {

    public int local_new_cases;
    public int local_total_cases;
    public int local_deaths;
    public int local_new_deaths;
    public int local_recovered;
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
        setContentView(R.layout.activity_admin);


        DateAdd = findViewById(R.id.newdate);
        newCases = findViewById(R.id.newCases);
        total = findViewById(R.id.total);
        deaths = findViewById(R.id.deathsTotal);
        newDeaths = findViewById(R.id.deathsNew);
        recovered = findViewById(R.id.recovered);
        DateDelete = findViewById(R.id.date);

        DateAdd.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_NORMAL);
        DateDelete.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_NORMAL);

    }


    public void addReport(View view){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        local_new_cases = Integer.parseInt(newCases.getText().toString());
        local_total_cases = Integer.parseInt(total.getText().toString());
        local_deaths = Integer.parseInt(deaths.getText().toString());
        local_new_deaths = Integer.parseInt(newDeaths.getText().toString());
        local_recovered = Integer.parseInt(recovered.getText().toString());
        try {
            update_date_time = df.parse(DateAdd.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://192.168.8.141:8080/addDailyReport";
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("update_date_time", update_date_time);
            jsonBody.put("local_new_cases", local_new_cases);
            jsonBody.put("local_total_cases", local_total_cases);
            jsonBody.put("local_deaths", local_deaths);
            jsonBody.put("local_new_deaths", local_new_deaths);
            jsonBody.put("local_recovered", local_recovered);
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(Admin.this, response, Toast.LENGTH_LONG).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    300000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Successfully Added", Toast.LENGTH_LONG).show();
    }

    public void deleteReport(View view){
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            update_date_time = df.parse(DateDelete.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.8.141:8080/deleteDailyReport/"+update_date_time.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Admin.this, response, Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

        Toast.makeText(this, "Successfully Deleted", Toast.LENGTH_LONG).show();
    }



}