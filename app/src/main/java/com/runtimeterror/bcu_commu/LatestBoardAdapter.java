package com.runtimeterror.bcu_commu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LatestBoardAdapter extends RecyclerView.Adapter<LatestBoardAdapter.ViewHolder> implements OnLatestBoardClickListener {
    ArrayList<LatestBoard> items = new ArrayList<LatestBoard>();
    OnLatestBoardClickListener listener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle;
        TextView txtPost1;
        TextView txtPost2;
        TextView txtPost3;

        public ViewHolder(View itemView, final OnLatestBoardClickListener listener){
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPost1 = itemView.findViewById(R.id.txtPost1);
            txtPost2 = itemView.findViewById(R.id.txtPost2);
            txtPost3 = itemView.findViewById(R.id.txtPost3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });
        }

        public void setItem(LatestBoard item){
            txtTitle.setText(item.getTitle());
            txtPost1.setText(item.getLatestPost1());
            txtPost2.setText(item.getLatestPost2());
            txtPost3.setText(item.getLatestPost3());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.latest_board_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        LatestBoard item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(LatestBoard item){
        items.add(item);
    }

    public void setItems(ArrayList<LatestBoard> items){
        this.items = items;
    }

    public LatestBoard getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, LatestBoard item){
        items.set(position, item);
    }

    public void setOnItemClickListener(OnLatestBoardClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }
}
