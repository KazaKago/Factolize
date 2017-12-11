package com.kazakago.factolize.samplejava;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kazakago.factolize.Factory;
import com.kazakago.factolize.FactoryParam;

@Factory
public class SubFragment extends Fragment {

    @FactoryParam
    int intValue;
    @FactoryParam
    String stringValue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SubFragmentFactory.injectArgument(this, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sub, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView intValueTextView = view.findViewById(R.id.intValueTextView);
        intValueTextView.setText("intValue : " + intValue);
        TextView stringValueTextView = view.findViewById(R.id.stringValueTextView);
        stringValueTextView.setText("stringValue" + stringValue);
    }
}
