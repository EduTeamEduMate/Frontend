package com.example.edumate.views.activitites.mainScreen;

import android.content.Intent;

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
import com.example.edumate.models.SUserData;
import com.example.edumate.network.ApiService;
import com.example.edumate.network.RetrofitClient;
import com.example.edumate.views.activitites.QuizInfoActivity;
import com.example.edumate.views.adapters.HistoryCardsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryFragment extends Fragment
        implements HistoryCardsAdapter.OnHistoryCardClickListener {

    private RecyclerView recyclerView;
    private List<HistoryCard> cardList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        recyclerView = view.findViewById(R.id.history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cardList = new ArrayList<>();
        HistoryCardsAdapter adapter = new HistoryCardsAdapter(cardList,this);
        recyclerView.setAdapter(adapter);

        fetchExamHistory();

        return view;
    }

    @Override
    public void onCardClick(int examId) {
        Intent intent = new Intent(getActivity(), QuizInfoActivity.class);
        intent.putExtra("EXAM_ID", examId);
        startActivity(intent);
    }

    private void fetchExamHistory() {
        int userId = SUserData.getInstance().getId();
        ApiService apiService = RetrofitClient.getApiService();

        Call<List<HistoryCard>> call = apiService.getExamHistory(userId);
        call.enqueue(new Callback<List<HistoryCard>>() {
            @Override
            public void onResponse(Call<List<HistoryCard>> call, Response<List<HistoryCard>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cardList.clear();
                    cardList.addAll(response.body());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                // Consider handling the case where response.body() is null
            }

            @Override
            public void onFailure(Call<List<HistoryCard>> call, Throwable t) {
                // Handle failure
            }
        });
    }

}

