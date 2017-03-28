package com.yuseok.android.loginresult;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class InappActivity extends AppCompatActivity {

    Bitmap bitmap;

    TextView textUser;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inapp);

        imageView = (ImageView) findViewById(R.id.imageView);

        textUser = (TextView) findViewById(R.id.textUser);

        Intent intent = getIntent();

        String data = intent.getStringExtra("user");
        Log.i("데이터는 어떻게될까??" , data);

        textUser.setText(data + "");

        // String으로 받아온 json값에서 id값만 뽑아내기
        String[] first = data.split(":");
        Log.i("Split====", first[1]);
        String temp = first[1];
        Log.i("일단 담아두기", temp);
        String[] sec = temp.split(",");
        Log.i("Second====", sec[0]);
        String id = sec[0];
        String id2 = id.substring(1, id.length()-1);
        Log.i("지워졌나??????", id2);


        // 프로필 사진을 가져오는 Url
        final String pictureUrl = "https://graph.facebook.com/"+id2+ "/picture?type=large";
        Log.i("URL==========", pictureUrl);

        //imageView에 이미지 세팅
        Thread mThread = new Thread(){

            public void run() {
                try {
                    URL url = new URL(pictureUrl);

                    // 웹에서 이미지 가져온뒤 이미지뷰에 지정할 Bitmap을 생성함

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (IOException e) {

                }
            }
        };

        mThread.start();
        try {
            mThread.join();
            imageView.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
  }
}

