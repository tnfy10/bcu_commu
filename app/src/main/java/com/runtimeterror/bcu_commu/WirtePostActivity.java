package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class WirtePostActivity extends AppCompatActivity {
    ImageView writeBtn;
    ImageView prevBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wirte_post);

        prevBtn = findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        writeBtn = findViewById(R.id.writeBtn);
        writeBtn.setVisibility(View.VISIBLE);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO - 글작성 완료 버튼
                finish(); // 임시로 넣은 문장
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}