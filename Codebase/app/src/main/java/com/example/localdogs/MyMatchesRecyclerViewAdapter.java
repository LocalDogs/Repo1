package com.example.localdogs;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.localdogs.data.MatchesData;
import com.example.localdogs.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyMatchesRecyclerViewAdapter extends RecyclerView.Adapter<MyMatchesRecyclerViewAdapter.ViewHolder> {

    private final List<MatchesData> mValues;

    public MyMatchesRecyclerViewAdapter(List<MatchesData> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.matches_fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        int position2 = position + 1;
        holder.mItem = mValues.get(position);
        holder.mMatchView.setText("Match " + position2);
        holder.mIdView.setText("Owner: " + mValues.get(position).getMatchedFirstName() + " / Dog: " + mValues.get(position).getMatchedDogName());
        holder.mContentView.setText(mValues.get(position).getMatchedContactInfo());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public MatchesData mItem;

        public final TextView mMatchView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMatchView = (TextView) view.findViewById(R.id.match_number);
            mIdView = (TextView) view.findViewById(R.id.item_number);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}