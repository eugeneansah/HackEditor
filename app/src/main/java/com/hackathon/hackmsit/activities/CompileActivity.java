package com.hackathon.hackmsit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hackathon.hackmsit.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CompileActivity extends AppCompatActivity {

    Date date;
    JSONObject js;
    TextView tv;

    public Date getDate() {
        return date;
    }

    String code, testCase, output;
    String url = "http://api.hackerrank.com/checker/submission.json";
    JSONObject jsonObject = null;
    EditText codeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_compile);
        codeContainer = (EditText) findViewById(R.id.inputText);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle args = getIntent().getExtras();
        code = args.getString("code");
        tv = (TextView) findViewById(R.id.outputView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    /*@Override
    public void onBackPressed() {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_compile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
            case R.id.compile:
                //code = codeContainer.getText().toString();
                //jsonObject = new JSONObject();

                testCase = codeContainer.getText().toString();
                // new runCode().execute();
                js = new JSONObject();
                tv.setText("Compiling...");

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("aakashras", "Response: " + response);
                                try {
                                    JSONObject abc = new JSONObject(response);
                                    js = abc;
                                    //Log.d("abc", String.valueOf(js.getJSONObject("result").getJSONArray("stderr").getBoolean(0)));

                                        if (js.getJSONObject("result").getString("stderr").equals("null") ) {
                                            Log.d("aakash", "inside error null");
                                            output = "Error in Code";
                                        }
                                        else{
                                            output=js.getJSONObject("result").getJSONArray("stdout").get(0).toString();
                                        }

                                    tv.setText(output);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("aakash", "Error: " + error.getMessage());
                        error.printStackTrace();

                    }
                }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/x-www-form-urlencoded;charset=UTF-8";
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("source", code);
                        params.put("lang", "1");
                        params.put("testcases", "[" + "\"" + testCase + "\"" + "]");
                        params.put("api_key", "hackerrank|295035-620|78f4dce9b31d6e8e39110ffd911b7f20e1538084");
                        Log.d("aakash", "params = " + params);
                        return params;
                    }

                };

                jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                queue.add(jsonObjRequest);


        }
        return super.onOptionsItemSelected(item);
    }

   /* private class runCode extends AsyncTask<Object, Object, Object> {


        @Override
        protected Object doInBackground(Object[] params) {


            }

        }*//*
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("aakashras", "Response: " + response);


                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("aakash", "Error: " + error.getMessage());
                    error.printStackTrace();

                }
            }) {

                @Override
                public String getBodyContentType() {
                    return "application/x-www-form-urlencoded;charset=UTF-8";
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("source", code);
                    params.put("lang", "1");
                    params.put("testcases", "[" + "\"" + testCase + "\"" + "]");
                    params.put("api_key", "hackerrank|295035-620|78f4dce9b31d6e8e39110ffd911b7f20e1538084");
                    Log.d("aakash", "params = " + params);
                    return params;
                }

            };

            jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            //jsonObjRequest.setShouldCache(false);
            queue.add(jsonObjRequest);
            return null;
        }*/
}



