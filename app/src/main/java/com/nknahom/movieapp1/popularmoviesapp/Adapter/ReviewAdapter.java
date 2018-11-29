package com.nknahom.movieapp1.popularmoviesapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nknahom.movieapp1.popularmoviesapp.Model.Review;
import com.nknahom.movieapp1.popularmoviesapp.R;

import java.util.List;

public class ReviewAdapter extends ArrayAdapter<Review> {

    /**
     * ViewHolder class for layout.<br />
     * <br />
     * Auto-created on 2018-11-15 02:41:54 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private static class ViewHolder {
        public final android.support.constraint.ConstraintLayout rootView;
        public final TextView textView2;
        public final TextView textView3;

        private ViewHolder(android.support.constraint.ConstraintLayout rootView, TextView textView2, TextView textView3) {
            this.rootView = rootView;
            this.textView2 = textView2;
            this.textView3 = textView3;
        }

        public static ViewHolder create(android.support.constraint.ConstraintLayout rootView) {
            TextView textView2 = (TextView)rootView.findViewById( R.id.textView2 );
            TextView textView3 = (TextView)rootView.findViewById( R.id.textView3 );
            return new ViewHolder( rootView, textView2, textView3 );
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if ( convertView == null ) {
            View view = mInflater.inflate( R.layout.review_layout, parent, false );
            vh = ViewHolder.create( (android.support.constraint.ConstraintLayout)view );
            view.setTag( vh );
        } else {
            vh = (ViewHolder)convertView.getTag();
        }

        Review item = getItem( position );

        // TODOBind your data to the views here
        vh.textView2.setText(item.getReview_content());
        vh.textView3.setText(item.getReview_author());


        return vh.rootView;
    }

    private LayoutInflater mInflater;

    // Constructors
    public ReviewAdapter(Context context, List<Review> objects) {
        super(context, 0, objects);
        this.mInflater = LayoutInflater.from( context );
    }
    public ReviewAdapter(Context context, Review[] objects) {
        super(context, 0, objects);
        this.mInflater = LayoutInflater.from( context );
    }
}

