package com.het.circleicon;

import android.Manifest;
import android.graphics.Bitmap;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.zou.circleicon.R;

public class MainActivity extends AppCompatActivity {

    //ImageLoader imageLoader;
    // ivTest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ivTest = findViewById(R.id.iv_test);

        String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };


        ActivityCompat.requestPermissions(this,
                permissions, 1);


        String imageUri = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527658273754&di=eb5617ec2c0aaa284e1a4432eafe39be&" +
                "imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F81%2F48%2F15T58PICNcG_1024.jpg";

      //  ivTest.setmImageUri(imageUri);

    }

}
