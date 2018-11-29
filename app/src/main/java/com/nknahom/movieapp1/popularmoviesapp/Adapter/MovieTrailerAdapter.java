package com.nknahom.movieapp1.popularmoviesapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nknahom.movieapp1.popularmoviesapp.Model.Trailer;
import com.nknahom.movieapp1.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieTrailerAdapter extends ArrayAdapter<Trailer> {

    /**
     * ViewHolder class for layout.<br />
     * <br />
     * Auto-created on 2018-10-27 21:53:24 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private static class ViewHolder {
        public final android.support.constraint.ConstraintLayout rootView;
        public final ImageView imageView2;
        public final TextView textView;

        private ViewHolder(android.support.constraint.ConstraintLayout rootView, ImageView imageView2, TextView textView) {
            this.rootView = rootView;
            this.imageView2 = imageView2;
            this.textView = textView;
        }

        public static ViewHolder create(android.support.constraint.ConstraintLayout rootView) {
            ImageView imageView2 = (ImageView)rootView.findViewById( R.id.imageView2 );
            TextView textView = (TextView)rootView.findViewById( R.id.textView );
            return new ViewHolder( rootView, imageView2, textView );
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if ( convertView == null ) {
            View view = mInflater.inflate( R.layout.trailer_layout, parent, false );
            vh = ViewHolder.create( (android.support.constraint.ConstraintLayout)view );
            view.setTag( vh );
        } else {
            vh = (ViewHolder)convertView.getTag();
        }

        Trailer item = getItem( position );

        // TODOBind your data to the views here
        assert item != null;
        vh.textView.setText(item.getTrailer_name());
        Picasso.get().load(R.drawable.play_video).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView2);

        return vh.rootView;
    }

    private LayoutInflater mInflater;

    // Constructors
    public MovieTrailerAdapter(Context context, List<Trailer> objects) {
        super(context, 0, objects);
        this.mInflater = LayoutInflater.from( context );
    }
    public MovieTrailerAdapter(Context context, Trailer[] objects) {
        super(context, 0, objects);
        this.mInflater = LayoutInflater.from( context );
    }
}


