package com.nknahom.movieapp1.popularmoviesapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.nknahom.movieapp1.popularmoviesapp.Adapter.MovieAdapter;
import com.nknahom.movieapp1.popularmoviesapp.Model.Movie;
import com.nknahom.movieapp1.popularmoviesapp.Parser.JsonParser;
import com.nknahom.movieapp1.popularmoviesapp.Utils.InternetConnection;
import com.nknahom.movieapp1.popularmoviesapp.Utils.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private ArrayList<Movie> list;
    private MovieAdapter adapter;
    private String sortBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create shared preference to allow user to change sort order via a setting

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sortBy = sharedPreferences.getString(Keys.SORT_BY,Keys.SORT_BY_POPULAR);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        if(savedInstanceState == null || !savedInstanceState.containsKey("MovieList")) {

            // Array List for Binding Data from JSON to this List

            list = new ArrayList<>();
            checkInternet(sortBy);
        }
        else {

            list = savedInstanceState.getParcelableArrayList("MovieList");
        }

        displayMovie();
    }

    private void displayMovie() {

        // Binding that List to Adapter

        adapter = new MovieAdapter(this, list);



        // Getting List and SettingsActivity List Adapter

        GridView listView = findViewById(R.id.list_item);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                launchDetailActivity(position);
            }
        });

        movieSortBy(sortBy);
    }

    // This method helps to display the App title base on the sort order
    private void movieSortBy(String key){
        switch (key){
            case Keys.SORT_BY_POPULAR:
                setTitle("Popular Movies");
                break;
            case Keys.SORT_BY_TOP_RATED:
                setTitle("Top Rated Movies");
                break;
            default:
                setTitle("Popular Movies");
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    private void launchDetailActivity(int position) {
        Movie item = list.get(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        intent.putExtra("Movie", item);
        startActivity(intent);
    }

    //This method check if there is internet access
    private void checkInternet(String key){

        // Checking Internet Connection

        if (InternetConnection.checkConnection(getApplicationContext())) {
            new GetDataTask().execute(key);
        } else {
            Toast.makeText(this,"Internet Not Available", Toast.LENGTH_LONG).show();
        }
    }

    //This method helps to refresh the Activity from the menu
    private void refreshInternet(){
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals(Keys.SORT_BY)){
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            sortBy = sharedPreferences.getString(Keys.SORT_BY,Keys.SORT_BY_POPULAR);

            list = new ArrayList<>();

            checkInternet(sortBy);
            displayMovie();
        }
    }


    // Creating Get Data Task for Getting Data From Web

    @SuppressLint("StaticFieldLeak")
    class GetDataTask extends AsyncTask<String, Void, Void> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Progress Dialog for User Interaction

            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait...");
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {


            // Getting JSON Object from Web Using okHttp

            String sharedKey = params[0];
            JSONObject jsonObject = JsonParser.getDataFromWeb(sharedKey);

            try {

                // Check Whether Its NULL???

                if (jsonObject != null) {

                    // Check Length

                    if(jsonObject.length() > 0) {

                        // Getting Array named "results" From Movie Json Object

                        JSONArray array = jsonObject.getJSONArray(Keys.MOVIE_RESULTS);

                        //Check Length of Array...

                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for(int jIndex = 0; jIndex < lenArray; jIndex++) {

                                JSONObject innerObject = array.getJSONObject(jIndex);
                                int movie_id = innerObject.getInt(Keys.MOVIE_ID);
                                String backdrop_path = innerObject.getString(Keys.MOVIE_BACKDROP_PATH);
                                String original_title = innerObject.getString(Keys.MOVIE_ORIGINAL_TITLE);
                                String poster_path = innerObject.getString(Keys.MOVIE_POSTER_PATH);
                                String title = innerObject.getString(Keys.MOVIE_TITLE);
                                String release_date = " " + innerObject.getString(Keys.MOVIE_RELEASE_DATE) + " ";
                                String vote_average = " " + Double.toString(innerObject.getDouble(Keys.MOVIE_VOTE_AVERAGE));
                                String popularity = " " + Double.toString(innerObject.getDouble(Keys.MOVIE_POPULARITY));
                                String original_language = " " + innerObject.getString(Keys.MOVIE_ORIGINAL_LANGUAGE);
                                String movie_overview = innerObject.getString(Keys.MOVIE_OVERVIEW);

                                Movie model = new Movie(movie_id, backdrop_path, original_title, poster_path, title, release_date, vote_average, popularity, original_language, movie_overview);
                                // Adding name and phone concatenation in List

                                list.add(model);
                            }
                        }
                    }
                }
            } catch (JSONException je) {
                Log.i(JsonParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

            // Checking if List size if more than zero then update ListView

            if(list.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(),"No Data Found, Try to refresh it!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("MovieList", list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.checkInternet:
                refreshInternet();
                return true;
            case R.id.settings:
                Intent startSettingsActivity = new Intent(this,
                        SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
