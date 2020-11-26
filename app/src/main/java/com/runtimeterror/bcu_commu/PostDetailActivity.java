package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PostDetailActivity extends AppCompatActivity {
    ImageView prevBtn;
    ImageView postProfileImg;
    ImageView deleteBtn;
    TextView txtWriter;
    TextView txtWriteTime;
    TextView detailPostTitle;
    TextView detailPostContent;
    LinearLayout userInfo;

    String writer, writeTime, title, content;

    Boolean visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        prevBtn = findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        deleteBtn = findViewById(R.id.deleteBtn);

        if(getIntent().getBooleanExtra("deleteBtnInvisible", false)){
            deleteBtn.setVisibility(View.INVISIBLE);
        }else{
            deleteBtn.setVisibility(View.VISIBLE);
        }
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO - 게시글 삭제 버튼
                Toast.makeText(getApplicationContext(), "미구현된 기능", Toast.LENGTH_SHORT).show();
            }
        });

        postProfileImg = findViewById(R.id.postProfileImg);
        txtWriter = findViewById(R.id.txtWriter);
        txtWriteTime = findViewById(R.id.txtWriteTime);
        detailPostTitle = findViewById(R.id.detailPostTitle);
        detailPostContent = findViewById(R.id.detailPostContent);
        userInfo = findViewById(R.id.userInfo);

        writer = getIntent().getStringExtra("writer");
        // writeTime = getIntent().getStringExtra("writeTime");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");

        txtWriter.setText(writer);
        // txtWriteTime.setText(writeTime);
        detailPostTitle.setText(title);
        detailPostContent.setText(content);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}