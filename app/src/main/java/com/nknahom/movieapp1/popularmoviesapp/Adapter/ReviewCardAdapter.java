package com.nknahom.movieapp1.popularmoviesapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nknahom.movieapp1.popularmoviesapp.Model.Review;
import com.nknahom.movieapp1.popularmoviesapp.R;

import java.util.List;

public class ReviewCardAdapter extends RecyclerView.Adapter<ReviewCardAdapter.ReviewCardViewHolder>{

    private Context context;
    private List<Review> list;

    public ReviewCardAdapter(Context context, List<Review> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReviewCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_cardview, null);
        return new ReviewCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewCardViewHolder holder, int position) {

        Review review = list.get(position);

        holder.review_body.setText(review.getReview_content());
        holder.review_author.setText(review.getReview_author());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ReviewCardViewHolder extends RecyclerView.ViewHolder{

        TextView review_body,review_author;

        public ReviewCardViewHolder(View itemView) {
            super(itemView);

            review_author = itemView.findViewById(R.id.author_name);
            review_body = itemView.findViewById(R.id.review_body);
        }
    }
}
