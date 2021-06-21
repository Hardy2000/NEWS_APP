package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.ObjectModel.Articles;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<Articles> articles;

    public Adapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Articles a = articles.get(position);
        String imageUrl = a.getUrlToImage();
        String url = a.getUrl();

        Picasso.get().load(imageUrl).into(holder.imageView);

        holder.t_Title.setText(a.getTitle());
        holder.t_Source.setText(a.getSource().getName());
        holder.t_Date.setText(dateTime(a.getPublishedAt()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detailed_Activity.class);
                intent.putExtra("title", a.getTitle());
                intent.putExtra("source", a.getSource().getName());
                intent.putExtra("time", dateTime(a.getPublishedAt()));
                intent.putExtra("desc", a.getDescription());
                intent.putExtra("imageUrl", a.getUrlToImage());
                intent.putExtra("url", a.getUrl());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView t_Title, t_Source, t_Date;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            t_Title = itemView.findViewById(R.id.title_text);
            t_Source = itemView.findViewById(R.id.source_text);
            t_Date = itemView.findViewById(R.id.date_text);


            imageView = itemView.findViewById(R.id.image_view);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }


    public String dateTime(String dataDate) {
        String convTime = null;

        String prefix = "";
        String suffix = "Ago";

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date pasTime = dateFormat.parse(dataDate);

            Date nowTime = new Date();

            long dateDiff = nowTime.getTime() - pasTime.getTime();

            long second = TimeUnit.MILLISECONDS.toSeconds(dateDiff);
            long minute = TimeUnit.MILLISECONDS.toMinutes(dateDiff);
            long hour = TimeUnit.MILLISECONDS.toHours(dateDiff);
            long day = TimeUnit.MILLISECONDS.toDays(dateDiff);

            if (second < 60) {
                convTime = second + " Seconds " + suffix;
            } else if (minute < 60) {
                convTime = minute + " Minutes " + suffix;
            } else if (hour < 24) {
                convTime = hour + " Hours " + suffix;
            } else if (day >= 7) {
                if (day > 360) {
                    convTime = (day / 360) + " Years " + suffix;
                } else if (day > 30) {
                    convTime = (day / 30) + " Months " + suffix;
                } else {
                    convTime = (day / 7) + " Week " + suffix;
                }
            } else if (day < 7) {
                convTime = day + " Days " + suffix;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ConvTimeE", e.getMessage());
        }

        return convTime;
    }

//    public static String getCountry() {
//        Locale locale = Locale.getDefault();
//        String country = String.valueOf(locale.getCountry());
//        return country.toLowerCase();
//    }
//
//    public static String getLanguage() {
//        Locale locale = Locale.getDefault();
//        String country = String.valueOf(locale.getLanguage());
//        return country.toLowerCase();
//    }
}
