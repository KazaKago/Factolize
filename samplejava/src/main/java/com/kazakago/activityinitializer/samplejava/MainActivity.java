package com.kazakago.activityinitializer.samplejava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kazakago.activityinitializer.ActivityFactory;
import com.kazakago.activityinitializer.FactoryParam;

import java.util.ArrayList;

@ActivityFactory
public class MainActivity extends AppCompatActivity {

    @FactoryParam
    int hogehoge;
    @FactoryParam
    String hugahuga;
    @FactoryParam
    Integer mamamaa;
    @FactoryParam
    Integer[] hugah;
    @FactoryParam
    int[] hugah__;
    @FactoryParam
    ArrayList<String> stringArrayList;
    @FactoryParam
    ArrayList<Integer> integerArrayList;
    @FactoryParam
    ArrayList<Float> floatArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = MainActivityFactory.createIntent(this, 0, "hugahuga");
//        intent.putExtra("", mamamaa);
//        intent.putExtra("", hugah);
//        intent.putExtra("", Float.valueOf(90));
//        startActivity(intent);
//        MainFragment fragment = MainFragmentFactory.createInstance(0, "hugahuga");
    }

}
