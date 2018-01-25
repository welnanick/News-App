package com.nickwelna.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleViewHolder> {

    private List<Article> articles;

    public ArticleAdapter(@NonNull List<Article> articles) {

        this.articles = articles;

    }


    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        CardView listItem = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ArticleViewHolder(listItem);

    }

    @Override
    public void onBindViewHolder(final ArticleViewHolder holder, int position) {

        final Article currentArticle = articles.get(position);
        holder.section.setText(currentArticle.getSection());
        holder.author.setText(currentArticle.getAuthor());
        holder.date.setText(currentArticle.getDate());
        holder.title.setText(currentArticle.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Uri articleUri = Uri.parse(currentArticle.getArticleUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                holder.itemView.getContext().startActivity(websiteIntent);

            }

        });

    }

    public void clear() {

        int size = this.articles.size();
        this.articles.clear();
        notifyItemRangeRemoved(0, size);

    }

    @Override
    public int getItemCount() {

        return articles.size();

    }

    public void addAll(List<Article> articles) {

        this.articles = articles;
        notifyItemRangeInserted(0, articles.size());

    }

}