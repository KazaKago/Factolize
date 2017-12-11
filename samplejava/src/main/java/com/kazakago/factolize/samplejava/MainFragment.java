package com.kazakago.factolize.samplejava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.kazakago.factolize.Factory;

@Factory
public class MainFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainFragmentFactory.injectArgument(this, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText intEditText = view.findViewById(R.id.intEditText);
        final EditText stringEditText = view.findViewById(R.id.stringEditText);

        Button goToSubActivityButton = view.findViewById(R.id.goToSubActivityButton);
        goToSubActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = SubActivityFactory.createIntent(getActivity(), Integer.valueOf(intEditText.getText().toString()), stringEditText.getText().toString());
                startActivity(intent);
            }
        });
    }
}
