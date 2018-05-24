package com.corific.p2_vaio.corific_ui.activity;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.corific.p2_vaio.corific_ui.R;
import com.corific.p2_vaio.corific_ui.adapter.ViewPagerAdapter;
import com.corific.p2_vaio.corific_ui.fragments.More;
import com.corific.p2_vaio.corific_ui.fragments.Qualification;
import com.corific.p2_vaio.corific_ui.fragments.Reviews;

public class MainActivity extends AppCompatActivity {

    private float collapsedScale;
    private float expandedScale;
    private ImageView photoView ;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");

        photoView = (ImageView) findViewById(R.id.image);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorAccent), Color.parseColor("#ffffff"));
//        createTabIcons();

        NestedScrollView scrollView = (NestedScrollView) findViewById (R.id.nest_scrollView);
        scrollView.setFillViewport (true);


        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        mAppBarLayout.setExpanded(false);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int maxScroll = appBarLayout.getTotalScrollRange();

                float scrollPercent = (float) Math.abs(verticalOffset) / (float) maxScroll;

                if (collapsedScale == 0) {

                    Drawable photo = photoView.getDrawable();

                    int bitmapWidth = photo.getIntrinsicWidth();
                    int bitmapHeight = photo.getIntrinsicHeight();

                    collapsedScale = (float)photoView.getWidth()/(float)bitmapWidth;
                    expandedScale = (float)photoView.getHeight()/(float)bitmapHeight;

                    scalePhotoImage(photoView, expandedScale);
                    Log.d(TAG, "onOffsetChanged: if\nscrollPercent => "+scrollPercent+"\n"+collapsedScale+"\n"+expandedScale);


                } else {

                    scalePhotoImage(photoView, (collapsedScale + (expandedScale - collapsedScale) * (scrollPercent))/2);
                    Log.d(TAG, "onOffsetChanged: else\nscrollPercent => "+scrollPercent+"\n"+collapsedScale+"\n"+expandedScale);

                }


            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {

            //setting up viewPager for swipe view between ALL/MY Listings
            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new Reviews(), "Reviews");
            adapter.addFragment(new Qualification(), "Qualification");
            adapter.addFragment(new More(), "More");
            viewPager.setAdapter(adapter);
        }



    private static void scalePhotoImage(ImageView photoView, float scale) {


        Drawable photo = photoView.getDrawable();

        int bitmapWidth = photo.getIntrinsicWidth();
        int bitmapHeight = photo.getIntrinsicHeight();

        float offsetX = (photoView.getWidth() - bitmapWidth) / 2F;
        float offsetY = (photoView.getHeight() - bitmapHeight) / 2F;

        float centerX = photoView.getWidth() / 2F;
        float centerY = photoView.getHeight() / 2F;

        Log.d(TAG, "scalePhotoImage: \n"+"offsetX => "+offsetX
                                             +"\noffsetY => "+offsetY
                                            +"\ncenterY => "+centerX
                                            +"\ncenterY => "+centerY
                                            +"\nScale => "+scale);

        Matrix imageMatrix = new Matrix();
        imageMatrix.setScale(scale, scale, centerX, centerY);
        imageMatrix.preTranslate(offsetX, offsetY);

        photoView.setImageMatrix(imageMatrix);
    }
    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Reviews");
//        tabOne.setBackground(getResources().getDrawable(R.drawable.rounded_rect_bg));
//        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.analytics, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Qualification");
//        tabOne.setBackground(getResources().getDrawable(R.drawable.rounded_rect_bg));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("More");
//        tabOne.setBackground(getResources().getDrawable(R.drawable.rounded_rect_bg));
        tabLayout.getTabAt(2).setCustomView(tabThree);
    }




}