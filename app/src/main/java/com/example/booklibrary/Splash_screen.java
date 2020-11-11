package com.example.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash_screen extends AppCompatActivity {

    ImageView logo , mainTitle , subTitle;
    Animation topAni , bottonAni;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_splash_screen );

        getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN );

        logo = findViewById ( R.id.logo );
        mainTitle = findViewById ( R.id.mainTitle );
        subTitle = findViewById ( R.id.subTitle );

        topAni = AnimationUtils.loadAnimation ( Splash_screen.this , R.anim.top_animation_splash );
        bottonAni = AnimationUtils.loadAnimation ( Splash_screen.this , R.anim.bottom_animation_splash );


        logo.setAnimation ( topAni );
        mainTitle.setAnimation ( bottonAni );
        subTitle.setAnimation ( bottonAni );

        Handler handler = new Handler ();
        handler.postDelayed ( new Runnable () {
            @Override
            public void run() {

                startActivity ( new Intent (Splash_screen.this , MainActivity.class) );
                finish ();
            }
        },2500 );



    }
}