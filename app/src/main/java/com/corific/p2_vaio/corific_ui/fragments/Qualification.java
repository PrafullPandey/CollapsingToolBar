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
import com.corific.p2_vaio.corific_ui.adapter.QualificationAdapter;
import com.corific.p2_vaio.corific_ui.modals.Qualification_Model;

import java.util.ArrayList;
import java.util.Arrays;


public class Qualification extends Fragment {

    private ArrayList<Qualification_Model> qualificationList = new ArrayList<>() ;
    private Qualification_Model qualificationModel;
    private String[] qualificationCourseArray ;
    private String[] qualificationCollegeArray ;
    private String[] qualificationDurationArray ;
    private RecyclerView recyclerViewMore;
    private QualificationAdapter qualificationAdapter ;


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
        View view = inflater.inflate(R.layout.fragment_qualification, container, false);

        qualificationCourseArray = getResources().getStringArray(R.array.qualificationCourse);
        qualificationCollegeArray = getResources().getStringArray(R.array.qualificationCollege);
        qualificationDurationArray = getResources().getStringArray(R.array.qualificationDuration);

        for(int i =0 ; i<qualificationCollegeArray.length ;i++){

            qualificationModel = new Qualification_Model();
            qualificationModel.setCourse(qualificationCourseArray[i]);
            qualificationModel.setCollege(qualificationCollegeArray[i]);
            qualificationModel.setDuration(qualificationDurationArray[i]);

            qualificationList.add(qualificationModel);
        }


        qualificationAdapter = new QualificationAdapter(getActivity() , qualificationList);
        recyclerViewMore = (RecyclerView)view.findViewById(R.id.recyclerViewQualification);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewMore.setLayoutManager(layoutManager);
        recyclerViewMore.setAdapter(qualificationAdapter);



        return view ;
    }
}
