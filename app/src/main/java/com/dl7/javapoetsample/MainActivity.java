package com.dl7.javapoetsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.annotation.MyAnnotation;

public class MainActivity extends AppCompatActivity {

    @MyAnnotation("Hello")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
