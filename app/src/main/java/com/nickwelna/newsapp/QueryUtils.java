package com.nickwelna.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public final class QueryUtils {

    public static final String LOG_TAG = "QueryUtils";

    private QueryUtils() {
    }

    public static ArrayList<Article> extractArticles(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {

            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {

            Log.e(LOG_TAG, "Problem making http request", e);

        }

        ArrayList<Article> articles = new ArrayList<>();

        try {

            if (!jsonResponse.equals("")) {

                JSONObject response = new JSONObject(jsonResponse);
                JSONObject responseObject = response.getJSONObject("response");
                JSONArray items = responseObject.getJSONArray("results");

                for (int i = 0; i < items.length(); i++) {

                    JSONObject articleJson = items.getJSONObject(i);

                    String section = articleJson.getString("sectionName");

                    // I couldn't find any results that didn't have a date, even though the project
                    // rubric said that was a possibility.
                    String publishTime = articleJson.getString("webPublicationDate");
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                    Date date = dateFormatter.parse(publishTime);
                    SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy", Locale.US);
                    String formattedDate = formatter.format(date);
                    String title = articleJson.getString("webTitle");
                    String articleUrl = articleJson.getString("webUrl");

                    String author = "";

                    JSONArray tags = articleJson.optJSONArray("tags");

                    JSONObject authorJson = tags.getJSONObject(0);
                    author = authorJson.getString("webTitle");

                    Article article = new Article(section, title, author, formattedDate, articleUrl);
                    articles.add(article);

                }

            }

        } catch (JSONException e) {

            Log.e(LOG_TAG, "Problem parsing the article JSON results", e);

        } catch (ParseException e) {

            Log.e(LOG_TAG, "Problem parsing the date", e);

        }

        return articles;

    }

    private static URL createUrl(String stringUrl) {

        URL url = null;
        try {

            url = new URL(stringUrl);

        } catch (MalformedURLException e) {

            Log.e(LOG_TAG, "Error with creating URL ", e);

        }
        return url;

    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {

            return jsonResponse;

        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {

                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {

                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());

            }
        } catch (IOException e) {

            Log.e(LOG_TAG, "Problem retrieving the article JSON results.", e);

        } finally {

            if (urlConnection != null) {

                urlConnection.disconnect();

            }
            if (inputStream != null) {

                inputStream.close();

            }

        }
        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {

                output.append(line);
                line = reader.readLine();

            }

        }
        return output.toString();

    }

}