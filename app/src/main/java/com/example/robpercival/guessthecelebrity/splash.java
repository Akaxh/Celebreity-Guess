package com.example.robpercival.guessthecelebrity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
               startActivity(new Intent(splash.this,MainActivity.class));
                    finish();
            }
        },500);
    }
}
