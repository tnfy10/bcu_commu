package com.runtimeterror.bcu_commu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> implements OnSchdItemClickListener{
    ArrayList<Schedule> items = new ArrayList<Schedule>();
    OnSchdItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.schedule_item, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Schedule item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Schedule item){
        items.add(item);
    }

    public void setItems(ArrayList<Schedule> items){
        this.items = items;
    }

    public Schedule getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Schedule item){
        items.set(position, item);
    }

    public void setOnItemClickListener(OnSchdItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView schdName;
        TextView schdMemo;
        TextView schdDate;

        public ViewHolder(View itemView, final OnSchdItemClickListener listener){
            super(itemView);

            schdName = itemView.findViewById(R.id.schdName);
            schdMemo = itemView.findViewById(R.id.schdMemo);
            schdDate = itemView.findViewById(R.id.schdDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(Schedule item){
            schdName.setText(item.getName());
            schdMemo.setText(item.getMemo());
            schdDate.setText(item.getDate());
        }
    }
}
