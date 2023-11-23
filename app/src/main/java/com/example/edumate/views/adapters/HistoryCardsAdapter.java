package com.example.edumate.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edumate.R;
import com.example.edumate.models.HistoryCard;

import java.util.List;

public class HistoryCardsAdapter extends RecyclerView.Adapter<HistoryCardsAdapter.CardViewHolder> {
    private List<HistoryCard> cardList;

    private OnHistoryCardClickListener clickListener;

    public interface OnHistoryCardClickListener {
        void onCardClick(int examId);
    }

    public HistoryCardsAdapter(List<HistoryCard> cardList, OnHistoryCardClickListener clickListener) {
        this.cardList = cardList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_history, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        HistoryCard currentCard = cardList.get(position);
        holder.titleTextView.setText(currentCard.getName());
        holder.imageView.setImageResource(R.drawable.exam_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onCardClick(currentCard.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        ImageView imageView;

        public CardViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }

}
