package com.corific.p2_vaio.corific_ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private float collapsedScale;
    private float expandedScale;
    private ImageView photoView ;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private String[] urls ;
    private int imageIndex=0;
    private ImageView image;

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


        urls = getResources().getStringArray(R.array.image_URL);
        image = (ImageView)findViewById(R.id.image);

        Random random = new Random();
        imageIndex = random.nextInt(3);

        GetImageFromURL getImageFromURL = new GetImageFromURL();
        getImageFromURL.execute(urls[imageIndex]);



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

    class GetImageFromURL extends AsyncTask<String ,Void ,Void>{

        private ImageView image_user;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            image_user = (ImageView)findViewById(R.id.image);
        }

        @Override
        protected Void doInBackground(String... strings) {

            getImageFromURL(strings[0]);
            return null;
        }

        private void getImageFromURL(String URL) {
            try {
                URL url = new URL(URL);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                myBitmap = Bitmap.createScaledBitmap(myBitmap ,200 ,200 ,false);
                final Bitmap newBitmap = myBitmap;
                saveImageOnDevice(newBitmap);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Stuff that updates the UI
                        image_user.setImageBitmap(newBitmap);
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();

            }

        }

        private void saveImageOnDevice(Bitmap newBitmap) {

            try {
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/DCIM");

                if (!myDir.exists()) {
                    myDir.mkdirs();
                }

                String name = "UserImage.jpg";
                myDir = new File(myDir, name);
                FileOutputStream out = new FileOutputStream(myDir);
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

                out.flush();
                out.close();

            } catch(Exception e){
                // some action
                e.printStackTrace();
            }
        }
    }

}

