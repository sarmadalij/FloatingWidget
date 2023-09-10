package com.sarmadali.floatingwidget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startFloatingWidgetService();
    }
    private void startFloatingWidgetService() {
        Intent intent = new Intent(this, FloatingWidgetService.class);
        startService(intent);
    }
}