package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.newsapp.ObjectModel.Articles;
import com.example.newsapp.ObjectModel.Headlines;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    final String API_KEY = "b24e60e9752d4dcdbfe4c7a72a0d35e0";
    Adapter adapter;
    EditText s_Query;
    Button s_Search;
    List<Articles> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Avoid automatic keyboard pop up
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        s_Query = findViewById(R.id.search_txt);
        s_Search = findViewById(R.id.btnSearch);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String country = getCountry();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retriveJson("",country, API_KEY);
            }
        });


        retriveJson("",country, API_KEY);
        //       retriveJson(country,"abcd");

        s_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!s_Query.getText().toString().trim().equals("")){
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retriveJson(s_Query.getText().toString().trim(),country,API_KEY);
                        }
                    });
                    retriveJson(s_Query.getText().toString().trim(),country,API_KEY);
                }else{
                    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            retriveJson("",country,API_KEY);
                        }
                    });
                    retriveJson("",country,API_KEY);
                }
            }
        });


    }

    public void retriveJson(String query,String country, String apikey) {

        swipeRefreshLayout.setRefreshing(true);
        Call<Headlines> call;
        if (!s_Query.getText().toString().trim().equals("")) {
            call = ApiClient.getInstance().getApi().getSpecificData(query, apikey);
        }else {
            call = ApiClient.getInstance().getApi().getHeadlines(country, apikey);
        }
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(SecondActivity.this, articles);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

                Toast.makeText(SecondActivity.this, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}