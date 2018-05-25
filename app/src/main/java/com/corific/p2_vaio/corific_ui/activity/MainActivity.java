package com.corific.p2_vaio.corific_ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    public static final int WRITE_EXTERNAL_STORAGE = 1;
    public static final int READ_EXTERNAL_STORAGE = 2;
    private static final String TAG = "MainActivity";
    private float collapsedScale;
    private float expandedScale;
    private ImageView photoView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter adapter;
    private String[] urls;
    private int imageIndex = 0;
    private ImageView image;

    private static void scalePhotoImage(ImageView photoView, float scale) {


        Drawable photo = photoView.getDrawable();

        int bitmapWidth = photo.getIntrinsicWidth();
        int bitmapHeight = photo.getIntrinsicHeight();

        float offsetX = (photoView.getWidth() - bitmapWidth) / 2F;
        float offsetY = (photoView.getHeight() - bitmapHeight) / 2F;

        float centerX = photoView.getWidth() / 2F;
        float centerY = photoView.getHeight() / 2F;

        /*Log.d(TAG, "scalePhotoImage: \n" + "offsetX => " + offsetX
                + "\noffsetY => " + offsetY
                + "\ncenterY => " + centerX
                + "\ncenterY => " + centerY
                + "\nScale => " + scale);*/

        Matrix imageMatrix = new Matrix();
        imageMatrix.setScale(scale, scale, centerX, centerY);
        imageMatrix.preTranslate(offsetX, offsetY);

        photoView.setImageMatrix(imageMatrix);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "onActivityResult: WRITE_EXTERNAL_STORAGE "+imageIndex);
                    GetImageFromURL getImageFromURL = new GetImageFromURL();
                    getImageFromURL.execute(urls[imageIndex]);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }


        }
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

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.colorAccent), Color.parseColor("#ffffff"));
//        createTabIcons();

        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.nest_scrollView);
        scrollView.setFillViewport(true);

        urls = getResources().getStringArray(R.array.image_URL);
        image = (ImageView) findViewById(R.id.image);

        Random random = new Random();
        imageIndex = random.nextInt(4);

        getPermissions();

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

                    collapsedScale = (float) photoView.getWidth() / (float) bitmapWidth;
                    expandedScale = (float) photoView.getHeight() / (float) bitmapHeight;

                    scalePhotoImage(photoView, expandedScale);
//                    Log.d(TAG, "onOffsetChanged: if\nscrollPercent => " + scrollPercent + "\n" + collapsedScale + "\n" + expandedScale);


                } else {

                    scalePhotoImage(photoView, (collapsedScale + (expandedScale - collapsedScale) * (scrollPercent)) / 2);
//                    Log.d(TAG, "onOffsetChanged: else\nscrollPercent => " + scrollPercent + "\n" + collapsedScale + "\n" + expandedScale);

                }


            }
        });

    }

    private void getPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            GetImageFromURL getImageFromURL = new GetImageFromURL();
            getImageFromURL.execute(urls[imageIndex]);

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
        }

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
             } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE);
        }
    }

    private void setupViewPager(ViewPager viewPager) {

        //setting up viewPager for swipe view between ALL/MY Listings
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Reviews(), "Reviews");
        adapter.addFragment(new Qualification(), "Qualification");
        adapter.addFragment(new More(), "More");
        viewPager.setAdapter(adapter);
    }

    class GetImageFromURL extends AsyncTask<String, Void, Void> {

        private ImageView image_user;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            image_user = (ImageView) findViewById(R.id.image);
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
                myBitmap = Bitmap.createScaledBitmap(myBitmap, 200, 200, false);
                final Bitmap newBitmap = myBitmap;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Stuff that updates the UI
                        image_user.setImageBitmap(newBitmap);
                    }
                });

                saveImageOnDevice(newBitmap);


            } catch (IOException e) {
                e.printStackTrace();

            }

        }

        private void saveImageOnDevice(Bitmap bitmap) {
            Log.d(TAG, "saveImageOnDevice: In");
            try {
                FileOutputStream outStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/TestImage");
                dir.mkdirs();
                String fileName = "USER_IMAGE.jpg";
                File outFile = new File(dir, fileName);
                outStream = new FileOutputStream(outFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                outStream.flush();
                outStream.close();

                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(outFile));
                sendBroadcast(intent);
                Toast.makeText(getApplicationContext() , "Image Saved Succesfully" ,Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.e("saveToInternalStorage()", e.getMessage());

            }
        }
    }

}

