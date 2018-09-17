package com.nknahom.movieapp1.popularmoviesapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nknahom.movieapp1.popularmoviesapp.Model.Movie;
import com.nknahom.movieapp1.popularmoviesapp.Utils.Keys;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @SuppressLint("SetTextI18n")
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
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
