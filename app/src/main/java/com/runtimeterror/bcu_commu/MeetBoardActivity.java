package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MeetBoardActivity extends AppCompatActivity {
    final String title = "모임게시판";
    TextView txtTitle;
    ImageView prevBtn;
    FloatingActionButton meetWriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetboard);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        prevBtn = findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        meetWriteBtn = findViewById(R.id.meetWriteBtn);
        meetWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeIntent = new Intent(getApplicationContext(), WirtePostActivity.class);
                writeIntent.putExtra("meetBoard", true);
                startActivity(writeIntent);
            }
        });

        RecyclerView postList = findViewById(R.id.meetPostList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postList.setLayoutManager(layoutManager);
        final PostAdapter postAdapter = new PostAdapter();

        for(int i=0; i<30; i++){
            postAdapter.addItem(new Post("모임"+i, "모임내용"+i, "김길동"+i));
        }

        postList.setAdapter(postAdapter);

        postAdapter.setOnItemClickListener(new OnPostItemClickListener() {
            @Override
            public void onItemClick(PostAdapter.ViewHolder holder, View view, int position) {
                Post item = postAdapter.getItem(position);
                Intent detailIntent = new Intent(getApplicationContext(), PostDetailActivity.class);
                detailIntent.putExtra("writer", item.getWriter());
                detailIntent.putExtra("title", item.getTitle());
                detailIntent.putExtra("content", item.getContent());
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}