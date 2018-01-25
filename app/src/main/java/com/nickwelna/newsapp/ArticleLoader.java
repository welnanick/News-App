package com.nickwelna.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

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
