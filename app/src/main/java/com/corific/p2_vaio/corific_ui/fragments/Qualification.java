package com.corific.p2_vaio.corific_ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.corific.p2_vaio.corific_ui.R;
import com.corific.p2_vaio.corific_ui.adapter.MoreAdapter;

import java.util.ArrayList;
import java.util.Arrays;


public class Qualification extends Fragment {

    private ArrayList<String> moreList ;
    private String[] moreArray ;
    private RecyclerView recyclerViewMore;
    private MoreAdapter moreAdapter ;


    public Qualification() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        moreArray = getResources().getStringArray(R.array.addToContacts);
        moreList = new ArrayList<>(Arrays.asList(moreArray));

        moreAdapter = new MoreAdapter(getActivity() , moreList);
        recyclerViewMore = (RecyclerView)view.findViewById(R.id.recyclerViewMore);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewMore.setLayoutManager(layoutManager);
        recyclerViewMore.setAdapter(moreAdapter);



        return view ;
    }
}
