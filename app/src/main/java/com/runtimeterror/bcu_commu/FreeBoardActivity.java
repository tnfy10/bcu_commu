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

public class FreeBoardActivity extends AppCompatActivity {
    final String title = "자유게시판";
    TextView txtTitle;
    ImageView prevBtn;
    FloatingActionButton freeWriteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeboard);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        prevBtn = findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        freeWriteBtn = findViewById(R.id.freeWriteBtn);
        freeWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeIntent = new Intent(getApplicationContext(), WirtePostActivity.class);
                writeIntent.putExtra("freeBoard", true);
                startActivity(writeIntent);
            }
        });

        RecyclerView postList = findViewById(R.id.freePostList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postList.setLayoutManager(layoutManager);
        final PostAdapter postAdapter = new PostAdapter();

        for(int i=0; i<30; i++){
            postAdapter.addItem(new Post("자유"+i, "자유내용"+i, "홍길동"+i));
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