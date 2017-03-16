package com.kazakago.activityinitializer.samplejava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kazakago.activityinitializer.Initializable;

@Initializable
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = MainActivityBuilder.createIntent(this);
        startActivity(intent);
        MainFragment fragment = MainFragmentBuilder.createInstance();
    }
}
