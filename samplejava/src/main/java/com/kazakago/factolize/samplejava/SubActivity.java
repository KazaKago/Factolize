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
    int intValue;
    @FactoryParam
    String stringValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        SubActivityFactory.injectArgument(this);

        if (savedInstanceState == null) {
            replaceSubFragment();
        }
    }

    private void replaceSubFragment() {
        SubFragment fragment = SubFragmentFactory.createInstance(intValue, stringValue);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
    }

}
