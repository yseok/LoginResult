package com.yuseok.android.loginresult;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class InappActivity extends AppCompatActivity {

    Bitmap bitmap;

    TextView textUser;
    ImageView imageView;

    Button painter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inapp);

        imageView = (ImageView) findViewById(R.id.imageView);

        textUser = (TextView) findViewById(R.id.textUser);

        painter = (Button) findViewById(R.id.btnPainter);

        Intent intent = getIntent();

        String data = intent.getStringExtra("user");
//        Log.i("데이터는 어떻게될까??" , data);

        textUser.setText(data + "");

        try {
            // String으로 받아온 json값에서 id값만 뽑아내기
            String[] first = data.split(":");
//        Log.i("Split====", first[1]);
            String temp = first[1];
//        Log.i("일단 담아두기", temp);
            String[] sec = temp.split(",");
//        Log.i("Second====", sec[0]);
            String id = sec[0];
            String id2 = id.substring(1, id.length() - 1);
//        Log.i("지워졌나??????", id2);


            Glide.with(this).load("https://graph.facebook.com/" + id2 + "/picture?type=large").into(imageView);

//            // 프로필 사진을 가져오는 Url
//            final String pictureUrl = "https://graph.facebook.com/" + id2 + "/picture?type=large";
//            Log.i("URL==========", pictureUrl);
//
//            //imageView에 이미지 세팅
//            Thread mThread = new Thread() {
//
//                public void run() {
//                    try {
//                        URL url = new URL(pictureUrl);
//
//                        // 웹에서 이미지 가져온뒤 이미지뷰에 지정할 Bitmap을 생성함
//
//                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                        conn.setDoInput(true);
//                        conn.connect();
//
//                        InputStream is = conn.getInputStream();
//                        bitmap = BitmapFactory.decodeStream(is);
//                    } catch (IOException e) {
//
//                    }
//                }
//            };
//
//
//            mThread.start();
//            try {
//                mThread.join();
//                imageView.setImageBitmap(bitmap);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        } catch (Exception e) {
            }

            painter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(InappActivity.this, ColorPickerDemoActivity.class);
                    startActivity(intent);
                }
            });

    }
}

