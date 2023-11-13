package com.example.edumate.views.activitites.mainScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edumate.R;
import com.example.edumate.models.HistoryCard;
import com.example.edumate.views.adapters.HistoryCardsAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<HistoryCard> cardList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cardList = new ArrayList<>();
        // Add some dummy data
        for (int i = 0; i < 10; i++) {
            cardList.add(new HistoryCard("Title " + i, "Description " + i));
        }

        HistoryCardsAdapter adapter = new HistoryCardsAdapter(cardList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

