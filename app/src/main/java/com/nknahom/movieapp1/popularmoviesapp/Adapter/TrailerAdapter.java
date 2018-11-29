package com.nknahom.movieapp1.popularmoviesapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nknahom.movieapp1.popularmoviesapp.Model.Trailer;
import com.nknahom.movieapp1.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHOlder>{

    Context context;
    List<Trailer> list;

    public TrailerAdapter(Context context, List<Trailer> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TrailerViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trailer_layout,parent);
        return new TrailerViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHOlder holder, int position) {

        Trailer trailer = list.get(position);

        holder.textView.setText(trailer.getTrailer_name());

        Picasso.get().load(R.drawable.refresh).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TrailerViewHOlder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        public TrailerViewHOlder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView2);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
