package com.corific.p2_vaio.corific_ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.corific.p2_vaio.corific_ui.R;
import com.corific.p2_vaio.corific_ui.activity.MainActivity;
import com.corific.p2_vaio.corific_ui.adapter.MoreAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static android.support.animation.SpringForce.STIFFNESS_LOW;
import static android.support.animation.SpringForce.STIFFNESS_VERY_LOW;

public class More extends Fragment {

    private static final String TAG = "More";
    private ArrayList<String> moreList ;
    private String[] moreArray ;
    private RecyclerView recyclerViewMore;
    private MoreAdapter moreAdapter ;
    private SpringAnimation springAnim ;
    private RelativeLayout relativeLayout;
    private int flag = 1;


    public More() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        moreArray = getResources().getStringArray(R.array.addToContacts);
        moreList = new ArrayList<>(Arrays.asList(moreArray));

        moreAdapter = new MoreAdapter(getActivity() , moreList);
        recyclerViewMore = (RecyclerView)view.findViewById(R.id.recyclerViewMore);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewMore.setLayoutManager(layoutManager);
        recyclerViewMore.setAdapter(moreAdapter);

        relativeLayout = (RelativeLayout)view.findViewById(R.id.containerRelativeLayout);


        recyclerViewMore.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(-1)&&flag==1)
                {
                    Log.d(TAG, "onScrollStateChanged: in scroll Listener");
                    createAnim();
                    MainActivity.createAnim();
                    springAnim.start();
                    MainActivity.springAnim.start();
                    flag=0;

                }





            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });



        return view ;
    }

    private void createAnim() {
        AppBarLayout mAppBarLayout = (AppBarLayout)getActivity().findViewById(R.id.appBar);
        springAnim = new SpringAnimation(relativeLayout, DynamicAnimation.TRANSLATION_Y, 0);

        //Setting the damping ratio to create a low bouncing effect.
        springAnim.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY);

        //Setting the spring with a low stiffness.
        springAnim.getSpring().setStiffness(STIFFNESS_VERY_LOW);

        springAnim.getSpring().setFinalPosition(MainActivity.appBarY);
        springAnim.setStartVelocity(2000f);

        springAnim.addEndListener(new DynamicAnimation.OnAnimationEndListener() {
            @Override
            public void onAnimationEnd(DynamicAnimation animation, boolean canceled, float value, float velocity) {
                flag=1;
            }
        });
    }


}
