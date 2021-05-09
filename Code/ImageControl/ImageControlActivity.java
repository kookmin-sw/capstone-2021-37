package com.example.control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

public class ImageControlActivity extends AppCompatActivity {

    ImageView imageView;
    ZoomControls zoomControls;

    int nBefore = 0;
    float prevX, prevY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.image_view);
        zoomControls = findViewById(R.id.zoom_controls);

        Button btnLeft = (Button)findViewById(R.id.btn_left);
        Button btnRight = (Button)findViewById(R.id.btn_right);

        imageView = (ImageView)findViewById(R.id.image_view);


        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                zoomControls.show();
                return false;
            }
        });

        zoomControls.setOnZoomInClickListener(new View.OnClickListener() {   //줌 인
            @Override
            public void onClick(View v) {
                float x = imageView.getScaleX();
                float y = imageView.getScaleY();
                imageView.setScaleX((float) (x+0.1));
                imageView.setScaleY((float) (y+0.1));
            }
        });

        zoomControls.setOnZoomOutClickListener(new View.OnClickListener() {  //줌 아웃
            @Override
            public void onClick(View v) {
                float x = imageView.getScaleX();
                float y = imageView.getScaleY();
                imageView.setScaleX((float) (x-0.1));
                imageView.setScaleY((float) (y-0.1));
            }
        });

        zoomControls.setOnClickListener(new View.OnClickListener() {     //줌에 대한 설정
            @Override
            public void onClick(View v) {
                float x = imageView.getScaleX();
                float y = imageView.getScaleY();
                if (x==1 && y==1) {
                    imageView.setScaleX(x);
                    imageView.setScaleY(y);
                }else {
                    imageView.setScaleX((float) (x-1));
                    imageView.setScaleY((float) (y-1));
                }
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {          //왼쪽 버튼 클릭(회전)
            @Override
            public void onClick(View v) {
                testRotation(nBefore - 10);
                Toast.makeText(ImageControlActivity.this, "Left", Toast.LENGTH_SHORT).show();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {          //오른쪽 버튼 클릭(회전)
            @Override
            public void onClick(View v) {
                testRotation(nBefore + 10);
                Toast.makeText(ImageControlActivity.this, "Right", Toast.LENGTH_SHORT).show();
            }
        });

        imageView.setOnTouchListener(new View.OnTouchListener() {
            private static final String TAG ="Drag";

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:   //뷰를 누름
                        prevX = event.getX();
                        prevY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:   //뷰 이동
                        float dx = event.getX() - prevX;
                        float dy = event.getY() - prevY;
                        Log.v(TAG, "dx : " + dx + " dy :: " + dy);
                        v.setX(v.getX() + dx);
                        v.setY(v.getY() + dy);
                        break;
                    case MotionEvent.ACTION_CANCEL:  //뷰 취소
                    case MotionEvent.ACTION_UP:  //뷰에서 손을 뗌
                        break;
                }
                return true;
            }
        });
    }

    public void testRotation(int i){                    //회전 설정
        RotateAnimation ra = new RotateAnimation(
                nBefore,
                i,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        ra.setDuration(250);
        ra.setFillAfter(true);
        imageView.startAnimation(ra);
        nBefore = i;
    }
}