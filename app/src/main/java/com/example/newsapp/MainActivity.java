package com.example.newsapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    // news api
    final String API_KEY = "0410489401ba45569b93e7a47f4f9237";
    RecyclerView recyclerView;
    Adapter adapter;
    EditText editText;
    Button search;

    List<Articles> articles = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        editText=findViewById(R.id.etQuery);
        search=findViewById(R.id.btnSearch);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final String country = getCountry();
        retrieveJson("",country, API_KEY);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals("")){
                    retrieveJson(editText.getText().toString(),country,API_KEY);
                }
                else {
                    retrieveJson("",country,API_KEY);
                }

            }
        });


    }

    public void retrieveJson(String query,String country, String apiKey) {
        Call<Headlines> call;
        if(!editText.getText().toString().equals("")){
            call = ApiClient.getInstance().getApi().getSpecificData(query, apiKey);

        }
        else {
            call = ApiClient.getInstance().getApi().getHeadlines(country, apiKey);

        }

        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    articles.clear();
                    articles = response.body().getArticles();

                    adapter = new Adapter(MainActivity.this, articles);
                    recyclerView.setAdapter(adapter);
                    Log.d("shiva", "onResponse: ");

                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {

                Toast.makeText(MainActivity.this, "There is An Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }


}
