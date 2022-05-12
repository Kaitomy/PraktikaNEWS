package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final List<NewsModel> news;

    public NewsAdapter(Context context, List<NewsModel> news) {
        this.news = news;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
        NewsModel news = this.news.get(position);
        holder.name.setText(news.name);
        holder.description.setText(news.description);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView name, description;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.txtNewsNameCard);
            description = view.findViewById(R.id.txtNewsDescriptionCard);

            cardView = view.findViewById(R.id.cardView);
        }
        public TextView getName() {
            return name;
        }
        public void setCardView(CardView cardView) {
            this.cardView = cardView;
        }
    }
}