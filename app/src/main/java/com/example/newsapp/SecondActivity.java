package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.WindowManager;
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
    final String API_KEY="b24e60e9752d4dcdbfe4c7a72a0d35e0";
    Adapter adapter;
    List<Articles> articles=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Avoid automatic keyboard pop up
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        recyclerView=findViewById(R.id.recyclerView);
        swipeRefreshLayout=findViewById(R.id.swipeRefresh);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String country=getCountry();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retriveJson(country,API_KEY);
            }
        });


        retriveJson(country,API_KEY);
 //       retriveJson(country,"abcd");
    }

    public void retriveJson(String country,String apikey){
        swipeRefreshLayout.setRefreshing(true);
        Call<Headlines>call=ApiClient.getInstance().getApi().getHeadlines(country,apikey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles()!=null)
                {   swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles=response.body().getArticles();
                    adapter=new Adapter(SecondActivity.this,articles);
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                  swipeRefreshLayout.setRefreshing(false);

                Toast.makeText(SecondActivity.this,t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }


    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}