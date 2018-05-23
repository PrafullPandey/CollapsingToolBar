package com.corific.p2_vaio.corific_ui.activity;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.corific.p2_vaio.corific_ui.R;

public class MainActivity extends AppCompatActivity {

    private float collapsedScale;
    private float expandedScale;
    private ImageView photoView ;

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
                                            +"\ncenterY => "+centerY);

        Matrix imageMatrix = new Matrix();
        imageMatrix.setScale(scale, scale, centerX, centerY);
        imageMatrix.preTranslate(offsetX, offsetY);

        photoView.setImageMatrix(imageMatrix);
    }
}