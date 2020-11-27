package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NoticeActivity extends AppCompatActivity {
    final String title = "공지사항";
    TextView txtTitle;
    ImageView prevBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        prevBtn = findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        RecyclerView postList = findViewById(R.id.noticePostList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postList.setLayoutManager(layoutManager);
        final PostAdapter postAdapter = new PostAdapter();

        postAdapter.addItem(new Post("부천대 커뮤니티 1.0.0 출시!", "부천대 커뮤니티 앱이 새로 출시되었습니다. 많은 이용바랍니다.", "운영자", null));

        postList.setAdapter(postAdapter);

        postAdapter.setOnItemClickListener(new OnPostItemClickListener() {
            @Override
            public void onItemClick(PostAdapter.ViewHolder holder, View view, int position) {
                Post item = postAdapter.getItem(position);
                Intent detailIntent = new Intent(getApplicationContext(), PostDetailActivity.class);
                detailIntent.putExtra("writer", item.getWriter());
                detailIntent.putExtra("title", item.getTitle());
                detailIntent.putExtra("content", item.getContent());
                detailIntent.putExtra("deleteBtnInvisible", true);
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}