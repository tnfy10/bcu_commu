package com.runtimeterror.bcu_commu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder>{
    ArrayList<Schedule> items = new ArrayList<Schedule>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.schedule_item, parent, false);

        return new ViewHolder(itemView);
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

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView schdName;
        TextView schdMemo;
        TextView schdDate;

        public ViewHolder(View itemView){
            super(itemView);

            schdName = itemView.findViewById(R.id.schdName);
            schdMemo = itemView.findViewById(R.id.schdMemo);
            schdDate = itemView.findViewById(R.id.schdDate);
        }

        public void setItem(Schedule item){
            schdName.setText(item.getName());
            schdMemo.setText(item.getMemo());
            schdDate.setText(item.getDate());
        }
    }
}
