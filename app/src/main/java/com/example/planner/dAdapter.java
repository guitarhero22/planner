package com.example.planner;

import android.content.Context;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.TimeoutException;

import static androidx.core.content.ContextCompat.startActivity;

public class dAdapter extends RecyclerView.Adapter<dAdapter.ViewHolder> {

    private List<ListItem> cardList;
    private Context context;
    public ViewHolder expandedPos;

    public dAdapter(List<ListItem> cardList, Context context) {
        this.cardList = cardList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview1, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final ListItem card = cardList.get(position);

        holder.textViewTit.setText(card.getTitle());
        holder.textViewDate.setText(card.getDate());
        holder.hide.setText(card.getChildren());
        holder.linearLayout.setOnClickListener(new DoubleClickListener(400) {
            @Override
            public void onDoubleClick() {
            }

            @Override
            public void onSingleClick() {
                if(expandedPos == holder){
                    holder.hide.setVisibility(View.GONE);
                    expandedPos = null;
                }else{
                    if(expandedPos == null){
                        holder.hide.setVisibility(View.VISIBLE);
                        expandedPos = holder;}
                    else{
                        expandedPos.hide.setVisibility(View.GONE);
                        holder.hide.setVisibility(View.VISIBLE);
                        expandedPos = holder;
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTit;
        public TextView textViewDate;
        public LinearLayout linearLayout;
        public TextView hide;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTit = (TextView) itemView.findViewById(R.id.CardTitle);
            textViewDate = (TextView) itemView.findViewById(R.id.CardDate);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.CardClick);
            hide = (TextView) itemView.findViewById(R.id.children);

        }
    }

}
