package com.example.edumate.views.activitites.mainScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.edumate.R;

public class GenerateFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_generate, container, false);

        rootView.findViewById(R.id.frameBox1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GenerateExamFromPdfActivity.class);
                startActivity(intent);
            }
        });

        rootView.findViewById(R.id.frameBox2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GenerateQuizFromImageActivity.class);
                startActivity(intent);
            }
        });

        rootView.findViewById(R.id.frameBox3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GenerativeQuizFromTextInputActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}
