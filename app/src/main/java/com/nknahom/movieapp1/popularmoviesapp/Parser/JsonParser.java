package com.nknahom.movieapp1.popularmoviesapp.Parser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.nknahom.movieapp1.popularmoviesapp.Utils.Keys;
import com.nknahom.movieapp1.popularmoviesapp.Utils.NetworkUtils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class JsonParser {

    /**
     * TAGs Defined Here...
     */
    public static final String TAG = "TAG";

    /**
     * Get Table Booking Charge
     *
     * @return JSON Object
     */
    public static JSONObject getDataFromWeb(String sortBy) {
        try {
            OkHttpClient client = new OkHttpClient();
            URL getURL = NetworkUtils.buildUrl(sortBy);

            Request request = new Request.Builder()
                    .url(getURL)
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

}
