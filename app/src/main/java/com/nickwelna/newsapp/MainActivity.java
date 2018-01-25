package com.nickwelna.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private static final int ARTICLE_LOADER_ID = 1;
    private static final String QUERY_URL = "https://content.guardianapis.com/search?q=minnesota&show-tags=contributor&api-key=test";

    private ArticleAdapter articleAdapter;
    private TextView emptyView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView articleRecyclerView = findViewById(R.id.article_recycler_view);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        articleRecyclerView.setLayoutManager(mLayoutManager);

        progressBar = findViewById(R.id.progress_bar);
        emptyView = findViewById(R.id.empty_view);

        articleAdapter = new ArticleAdapter(new ArrayList<Article>());
        articleRecyclerView.setAdapter(articleAdapter);


        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected) {

            progressBar.setVisibility(View.VISIBLE);
            getLoaderManager().initLoader(ARTICLE_LOADER_ID, null, MainActivity.this);

        } else {

            progressBar.setVisibility(View.GONE);
            emptyView.setText(R.string.no_connection);

        }

    }

    @Override
    public Loader<List<Article>> onCreateLoader(int id, Bundle args) {

        return new ArticleLoader(this, QUERY_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> data) {

        progressBar.setVisibility(View.GONE);
        if (data == null || data.size() == 0) {

            emptyView.setText(R.string.no_article_text);

        }

        updateUi(data);

    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {

        articleAdapter.clear();

    }

    private void updateUi(final List<Article> articles) {

        articleAdapter.clear();

        if (articles != null && !articles.isEmpty()) {

            articleAdapter.addAll(articles);

        }

    }

}
