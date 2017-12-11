package com.kazakago.factolize.samplejava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kazakago.factolize.Factory;
import com.kazakago.factolize.FactoryParam;

@Factory
public class SubActivity extends AppCompatActivity {

    @FactoryParam
    String stringValue;
    @FactoryParam
    int intValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        SubActivityFactory.injectArgument(this, savedInstanceState);

        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, SubFragmentFactory.createInstance(stringValue, intValue));
            fragmentTransaction.commit();
        }
    }

}
