package com.nknahom.movieapp1.popularmoviesapp.Utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    /**
     * Builds the URL used to query GitHub.
     *
     * @param movie_sort_by The keyword that will be queried for.
     * @return The URL to use to query the Movie API.
     */
    public static URL buildUrl(String movie_sort_by) {
        String movie_url = Keys.MOVIE_BASE_URL + movie_sort_by;
        Uri builtUri = Uri.parse(movie_url).buildUpon()
                .appendQueryParameter(Keys.API, Keys.API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
            Log.i("url", String.valueOf(url));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

}
