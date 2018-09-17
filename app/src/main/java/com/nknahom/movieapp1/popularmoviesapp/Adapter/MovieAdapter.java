package com.nknahom.movieapp1.popularmoviesapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nknahom.movieapp1.popularmoviesapp.Model.Movie;
import com.nknahom.movieapp1.popularmoviesapp.R;
import com.nknahom.movieapp1.popularmoviesapp.Utils.Keys;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private static class ViewHolder {
        private final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView textViewName;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
        }

        private static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = rootView.findViewById( R.id.imageView );
            TextView textViewName = rootView.findViewById( R.id.textViewName );
            return new ViewHolder( rootView, imageView, textViewName );
        }
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final ViewHolder vh;
        if ( convertView == null ) {
            View view = mInflater.inflate( R.layout.poster_layout, parent, false );
            vh = ViewHolder.create( (RelativeLayout)view );
            view.setTag( vh );
        } else {
            vh = (ViewHolder)convertView.getTag();
        }

        Movie item = getItem( position );

        // TODOBind your data to the views here
        assert item != null;
        vh.textViewName.setText(item.getTitle());
        String image_url = Keys.POSTER_PATH + item.getPoster_path();

        Picasso.get().load(image_url).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);

        return vh.rootView;
    }

    private LayoutInflater mInflater;

    // Constructors
    public MovieAdapter(Context context, List<Movie> movie) {
        super(context, 0, movie);
        this.mInflater = LayoutInflater.from( context );
    }
}
