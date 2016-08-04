package com.dl7.javapoetsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.annotation.BindInt;
import com.example.annotation.ControlFlow;
import com.example.annotation.FormatExam;
import com.example.annotation.HelloWorld;
import com.example.annotation.SpecExam;

import java.lang.reflect.Method;

@SpecExam
@FormatExam
@ControlFlow
public class MainActivity extends AppCompatActivity {

    @BindInt(123)
    int testInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testHelloWorld();
    }

    @HelloWorld("Hello Android")
    private void testHelloWorld() {
        Class<?> cls = getClass();
        Method[] methods = cls.getDeclaredMethods();
        for (Method method : methods) {
            HelloWorld methodInfo = method.getAnnotation(HelloWorld.class);
            if (methodInfo != null) {
                Log.e("MainActivity", methodInfo.value());
            }
        }
    }

}
