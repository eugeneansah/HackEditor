package com.hackathon.hackmsit.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hackathon.hackmsit.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CompileActivity extends AppCompatActivity {

    EditText input;
    Button testButton;

    String code;
    String url = "http://api.hackerrank.com/checker/submission.json";
    JSONObject jsonObject = null;
    EditText codeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        codeContainer = (EditText) findViewById(R.id.inputText);

        setContentView(R.layout.activity_compile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        testButton = (Button) findViewById(R.id.button);
        input = (EditText) findViewById(R.id.inputText);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("aakash", "text is: " + input.getText());
            }
        });
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
                jsonObject = new JSONObject();

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("aakashras","Response: "+response);


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
                        params.put("source", "#include<stdio.h> int main(void) { printf('12341234'); return 0; }");
                        params.put("lang", "1");
                        params.put("testcases", "["+"\""+1+"\""+"]");
                        params.put("api_key", "hackerrank|295035-620|78f4dce9b31d6e8e39110ffd911b7f20e1538084");
                        Log.d("aakash", "params = " + params);
                        return params;
                    }

                };

                jsonObjRequest.setShouldCache(false);
                queue.add(jsonObjRequest);

        }
        return super.onOptionsItemSelected(item);
    }
}
