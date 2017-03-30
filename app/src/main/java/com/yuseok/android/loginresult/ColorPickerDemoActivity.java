package com.yuseok.android.loginresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.larswerkman.holocolorpicker.ColorPicker;

public class ColorPickerDemoActivity extends AppCompatActivity  {
    private final static int COLOR_ACTIVITY = 1;

        int color;
        Button colorBtn;
        Intent intent;
        ColorPicker picker;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_paint);

            colorBtn = (Button)findViewById(R.id.color_btn);
        }

        public void startColorActivity(View view){

            intent = new Intent(ColorPickerDemoActivity.this, ColorActivity.class);
            if(color != 0){
                intent.putExtra("oldColor",color);
            }
            startActivityForResult(intent, COLOR_ACTIVITY);
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(resultCode == RESULT_OK){

                if(requestCode == COLOR_ACTIVITY){
                    color = data.getIntExtra("color", 0);
                    colorBtn.setBackgroundColor(color);
                }
            }
        }

}

