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
import com.corific.p2_vaio.corific_ui.adapter.QualificationAdapter;
import com.corific.p2_vaio.corific_ui.adapter.ReviewAdapter;
import com.corific.p2_vaio.corific_ui.modals.Qualification_Model;
import com.corific.p2_vaio.corific_ui.modals.Review_Model;

import java.util.ArrayList;


public class Reviews extends Fragment {

    private ArrayList<Review_Model> reviewList = new ArrayList<>() ;
    private Review_Model reviewModel;
    private String[] reviewNameArray ;
    private String[] reviewMessageArray ;
    private int[] reviewRatingArray ;
    private RecyclerView recyclerViewMore;
    private ReviewAdapter reviewAdapter ;


    public Reviews() {
        // Required empty public constructor
    }
    

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        reviewNameArray = getResources().getStringArray(R.array.reviewName);
        reviewMessageArray = getResources().getStringArray(R.array.reviewMessage);
        reviewRatingArray = getResources().getIntArray(R.array.reviewRating);

        for(int i =0 ; i< reviewNameArray.length ;i++){

            reviewModel = new Review_Model();
            reviewModel.setName(reviewNameArray[i]);
            reviewModel.setMessage(reviewMessageArray[i]);
            reviewModel.setRating(reviewRatingArray[i]);

            reviewList.add(reviewModel);
        }


        reviewAdapter = new ReviewAdapter(getActivity() , reviewList);
        recyclerViewMore = (RecyclerView)view.findViewById(R.id.recyclerViewReview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewMore.setLayoutManager(layoutManager);
        recyclerViewMore.setAdapter(reviewAdapter);



        return view ;
    }




}
