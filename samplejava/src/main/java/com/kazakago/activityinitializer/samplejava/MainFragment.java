package com.kazakago.activityinitializer.samplejava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kazakago.activityinitializer.FactoryParam;
import com.kazakago.activityinitializer.FragmentFactory;

/**
 * Created by tamura_k on 2017/03/16.
 */
@FragmentFactory
public class MainFragment extends Fragment {

    @FactoryParam
    int hogehoge;
    @FactoryParam
    String hugahuga;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

}
