package com.nickwelna.newsapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class ArticleViewHolder extends RecyclerView.ViewHolder {

    TextView section;
    TextView author;
    TextView date;
    TextView title;
    CardView layoutHolder;

    public ArticleViewHolder(View itemView) {

        super(itemView);
        layoutHolder = (CardView) itemView;
        section = itemView.findViewById(R.id.article_section);
        author = itemView.findViewById(R.id.article_author);
        date = itemView.findViewById(R.id.article_date);
        title = itemView.findViewById(R.id.article_title);

    }

}
