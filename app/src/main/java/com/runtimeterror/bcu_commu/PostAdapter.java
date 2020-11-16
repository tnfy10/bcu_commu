package com.runtimeterror.bcu_commu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> implements OnPostItemClickListener{
    ArrayList<Post> items = new ArrayList<Post>();
    OnPostItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.post_item, parent, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView postTitle;
        TextView postContent;
        TextView postWriter;

        public ViewHolder(View itemView, final OnPostItemClickListener listener){
            super(itemView);

            postTitle = itemView.findViewById(R.id.postTitle);
            postContent = itemView.findViewById(R.id.postContent);
            postWriter = itemView.findViewById(R.id.postWriter);

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

        public void setItem(Post item){
            postTitle.setText(item.getTitle());
            postContent.setText(item.getContent());
            postWriter.setText(item.getWriter());
        }
    }

    public void addItem(Post item){
        items.add(item);
    }

    public void setItems(ArrayList<Post> items){
        this.items = items;
    }

    public Post getItem(int position){
        return items.get(position);
    }

    public void setItem(int position, Post item){
        items.set(position, item);
    }

    public void setOnItemClickListener(OnPostItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }
}
