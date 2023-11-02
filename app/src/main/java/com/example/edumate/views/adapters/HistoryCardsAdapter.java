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

    public HistoryCardsAdapter(List<HistoryCard> cardList) {
        this.cardList = cardList;
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
        holder.titleTextView.setText(currentCard.getTitle());
        holder.descriptionTextView.setText(currentCard.getDescription());
        holder.imageView.setImageResource(R.drawable.exam_icon);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    static class CardViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView imageView;

        public CardViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            imageView = itemView.findViewById(R.id.imageView); // Initialize the ImageView
        }
    }

}
