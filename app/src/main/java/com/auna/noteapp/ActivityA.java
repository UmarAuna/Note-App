package com.auna.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        Button btn_b = findViewById(R.id.btn_b);

        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent newIntent = new Intent(ActivityA.this, ActivityB.class);
                startActivity(newIntent);*/

               startActivity(new Intent(ActivityA.this, ActivityB.class));
            }
        });
    }
}
