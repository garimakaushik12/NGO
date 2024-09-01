package com.garima.sukhmayfoundation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    TextView tv1,tv2,tv3;
    Animation anim,anim1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      imageView=findViewById(R.id.imageView);
       tv1=findViewById(R.id.textview);
        tv2=findViewById(R.id.textview1);
        tv3=findViewById(R.id.textview2);


     anim= AnimationUtils.loadAnimation(this,R.anim.fadein);
   //  anim1=AnimationUtils.loadAnimation(this,R.anim.move);
        imageView.setAnimation(anim);
        tv1.setAnimation(anim);
        tv2.setAnimation(anim);
        tv3.setAnimation(anim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}