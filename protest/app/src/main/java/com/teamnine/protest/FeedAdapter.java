package com.teamnine.protest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedHolder>{

    ArrayList<ProtestFeed> feedList = new ArrayList<>();

    public static class FeedHolder extends RecyclerView.ViewHolder {
        private final TextView feedDescription;
        private final TextView feedTime;

        public FeedHolder(View view) {
            super(view);
            feedDescription = view.findViewById(R.id.feedDescription);
            feedTime = view.findViewById(R.id.feedTime);
        }
    }

    public FeedAdapter(ArrayList<ProtestFeed> feeds) throws JSONException {
        feedList = feeds;
    }

    @Override
    public FeedAdapter.FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_list_item, parent, false);
        return new FeedHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.FeedHolder holder, int position) {
        holder.feedDescription.setText(feedList.get(position).getDescription());
        holder.feedTime.setText(feedList.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return feedList.size();
    }
}

