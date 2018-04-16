package com.example.student2.anew;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    String service = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170425T145422Z.50255e37fd0f3be2.193e26f527cdd0bbf1653c5005ab887b18eb05ec" +
            "&lang=en-ru" +
             "&text=";

    public void get(View view) {
        EditText editText = (EditText)findViewById(R.id.word);
        String word = editText.getText().toString();
        new MyTask().execute(word);}

    class MyTask extends AsyncTask<String, Void, String>{
String res = "";
        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(service + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                Scanner sc = new Scanner(connection.getInputStream());
                while (sc.hasNextLine()){
                    res+=sc.nextLine();
                }
            } catch (Exception e) {
              e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject all = new JSONObject(s);
                JSONArray text = all.getJSONArray("text");
                TextView answer = (TextView) findViewById(R.id.answer);
                answer.setText((String)text.get(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
