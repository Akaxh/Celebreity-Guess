package com.example.robpercival.guessthecelebrity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class Score extends AppCompatActivity {



    TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);


        score=findViewById(R.id.score1);
        Random random = new Random();
        int abc = random.nextInt((14 - 4) + 1) + 4;
        score.setText(String.valueOf(abc));
    }

    public void replay(View view){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
