package com.runtimeterror.bcu_commu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SiteAdapter extends RecyclerView.Adapter<SiteAdapter.ViewHolder> {
    ArrayList<Site> items = new ArrayList<Site>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder(View itemView){
            super(itemView);

            textView = itemView.findViewById(R.id.txtSite);
        }

        public void setItem(Site item){
            textView.setText(item.getName());
        }
    }

    public void addItem(Site item){
        items.add(item);
    }

    public void setItems(ArrayList<Site> items){
        this.items = items;
    }

    public Site getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Site item){
        items.set(position, item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.site_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Site item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
