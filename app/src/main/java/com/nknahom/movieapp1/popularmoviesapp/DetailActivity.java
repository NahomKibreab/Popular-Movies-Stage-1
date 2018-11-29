package com.nknahom.movieapp1.popularmoviesapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nknahom.movieapp1.popularmoviesapp.Adapter.MovieTrailerAdapter;
import com.nknahom.movieapp1.popularmoviesapp.Adapter.ReviewAdapter;
import com.nknahom.movieapp1.popularmoviesapp.Adapter.ReviewCardAdapter;
import com.nknahom.movieapp1.popularmoviesapp.Model.Movie;
import com.nknahom.movieapp1.popularmoviesapp.Model.Review;
import com.nknahom.movieapp1.popularmoviesapp.Model.Trailer;
import com.nknahom.movieapp1.popularmoviesapp.Utils.CustomListView;
import com.nknahom.movieapp1.popularmoviesapp.Utils.Keys;
import com.nknahom.movieapp1.popularmoviesapp.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ArrayList<Trailer> trailerArrayList;
    private MovieTrailerAdapter trailerAdapter;
    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private String movie_id;
    private RequestQueue mQueue;
    private RequestQueue mQueue2;
    private String trailer_key;
    private String review_url;
    RecyclerView recyclerView;
    ReviewCardAdapter reviewCardAdapter;
    List<Review> reviews_lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView detail_thumbnail = findViewById(R.id.detail_thumbnail);
        TextView original_title = findViewById(R.id.original_title);
        ImageView small_thumbnail = findViewById(R.id.small_thumbnail);
        TextView title = findViewById(R.id.title_en);
        TextView release_date = findViewById(R.id.release_date);
        TextView vote_average = findViewById(R.id.vote_average);
        TextView popularity = findViewById(R.id.popularity);
        TextView original_language = findViewById(R.id.original_language);
        TextView movie_overview = findViewById(R.id.overview);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("Movie");

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        movie_id = Integer.toString(movie.getMovie_id());
        Log.i("movie_(d", movie_id);

        String backdrop_path_url = Keys.BACKDROP_PATH + movie.getBackdrop_path();
        Picasso.get().load(backdrop_path_url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(detail_thumbnail);

        original_title.setText(movie.getOriginal_title());

        String poster_path_url = Keys.POSTER_PATH + movie.getPoster_path();
        Picasso.get().load(poster_path_url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(small_thumbnail);

        title.setText(movie.getTitle());
        release_date.setText(movie.getRelease_date());
        vote_average.setText(movie.getVote_average());
        popularity.setText(movie.getPopularity());
        original_language.setText(movie.getOriginal_language());
        movie_overview.setText(movie.getMovie_overview());

        setTitle(movie.getTitle());

        trailerArrayList = new ArrayList<>();
        reviews_lists = new ArrayList<>();
        // Instantiate the RequestQueue.
        mQueue = Volley.newRequestQueue(this);
        mQueue2 = Volley.newRequestQueue(this);

        getTrailers();
        displayMovie();

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void getTrailers(){
        String url = NetworkUtils.buildTrailer(movie_id).toString();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");

                            for (int i=0 ; i < jsonArray.length(); i++){
                                JSONObject trailer = jsonArray.getJSONObject(i);

                                String trailer_name = trailer.getString("name");
                                trailer_key = trailer.getString("key");
                                Trailer trailers = new Trailer(trailer_name,trailer_key);

                                trailerArrayList.add(trailers);

                            }
                            if(trailerArrayList.size() > 0) {
                                trailerAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(),"No Data Found, Try to refresh it!", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        String url1 = NetworkUtils.buildReview(movie_id).toString();
        Log.i("url1", url1);
        JsonObjectRequest request1 = new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("results");
                            Log.i("Review", "on response");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject review = jsonArray.getJSONObject(i);

                                String author = review.getString("author");
                                Log.i("review_url", author);
                                String content = review.getString("content");
                                review_url = review.getString("url");

                                Review reviews = new Review(author, content, review_url);

                                reviews_lists.add(reviews);

                            }
                            if (reviews_lists.size() > 0) {
                                reviewCardAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(getApplicationContext(), "No Data Found, Try to refresh it!", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        );

        mQueue.add(request);
        mQueue.add(request1);

    }



    private void displayMovie() {
        trailerAdapter = new MovieTrailerAdapter(this, trailerArrayList);
        // Getting List of Movie Trailers

        final CustomListView customListView = findViewById(R.id.movie_trailer);
        customListView.setAdapter(trailerAdapter);
        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.youtube.com/watch?v=" + trailer_key)));
                Log.i("Video", "Video Playing....");
            }
        });

        // Getting List of Movie Reviews

        recyclerView = findViewById(R.id.review_cardview);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(),LinearLayoutManager.HORIZONTAL,false));
        reviewCardAdapter = new ReviewCardAdapter(this,reviews_lists);
        recyclerView.setAdapter(reviewCardAdapter);
    }

}
