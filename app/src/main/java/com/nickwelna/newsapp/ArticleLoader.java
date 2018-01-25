package com.nickwelna.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    private String url;

    public ArticleLoader(Context context, String url) {

        super(context);
        this.url = url;

    }


    @Override
    public List<Article> loadInBackground() {

        if (url == null) {

            return null;

        }

        return QueryUtils.extractArticles(url);

    }


    @Override
    protected void onStartLoading() {

        forceLoad();

    }

}
