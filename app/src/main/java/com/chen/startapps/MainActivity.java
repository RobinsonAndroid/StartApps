package com.chen.startapps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout fl_container = (FrameLayout) findViewById(R.id.fl_container);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container,new AppsFragment()).commit();
    }
}
