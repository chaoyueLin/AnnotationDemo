package com.example.annotationdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.complier.CharlesAnnotation;
import com.example.javapoetcomplier.HelloAnnotation;
import com.example.kcomplier.HelloKotlin;

@HelloKotlin
@HelloAnnotation
@CharlesAnnotation(
        name = "other",
        text = "new"
)
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
