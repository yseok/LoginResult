package com.yuseok.android.loginresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

public class InappActivity extends AppCompatActivity {

    TextView textUser;

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inapp);

        textUser = (TextView) findViewById(R.id.textUser);

        Intent intent = getIntent();
        String data = intent.getStringExtra("user");

        textUser.setText(data + "");


    }
}

