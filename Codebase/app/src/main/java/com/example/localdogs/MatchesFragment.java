package com.example.localdogs;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.localdogs.data.MatchesData;
import com.example.localdogs.data.awsinterface.Authentication;
import com.example.localdogs.dummy.DummyContent;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class MatchesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MatchesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MatchesFragment newInstance(int columnCount) {
        MatchesFragment fragment = new MatchesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.matches_fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            ArrayList<MatchesData> matchesList = Authentication.getInstance(context.getApplicationContext()).getCurrentSessionUser().getMatchData();
            recyclerView.setAdapter(new MyMatchesRecyclerViewAdapter(matchesList));
        }
        return view;
    }
}