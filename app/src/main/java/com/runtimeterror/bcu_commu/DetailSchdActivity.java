package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class DetailSchdActivity extends AppCompatActivity {
    ImageView deleteBtn;
    ImageView updateBtn;
    ImageView prevBtn;

    TextView txtTitle;
    TextView txtTime;
    TextView txtContent;

    String title, time, content, schdNum, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_schd);

        txtTitle = findViewById(R.id.schdTitle);
        txtTime = findViewById(R.id.schdTime2);
        txtContent = findViewById(R.id.schdContent);

        title = getIntent().getStringExtra("title");
        time = getIntent().getStringExtra("time");
        content = getIntent().getStringExtra("content");
        schdNum = getIntent().getStringExtra("schdNum");
        user_id = getIntent().getStringExtra("user_id");

        txtTitle.setText(title);
        txtTime.setText(time);
        txtContent.setText(content);

        prevBtn = findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        deleteBtn = findViewById(R.id.deleteBtn);
        deleteBtn.setVisibility(View.VISIBLE);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAlert(v);
            }
        });

        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setVisibility(View.VISIBLE);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateSchd = new Intent(getApplicationContext(), AddSchdActivity.class);
                updateSchd.putExtra("update", true);
                updateSchd.putExtra("title", title);
                updateSchd.putExtra("time", time);
                updateSchd.putExtra("content", content);
                updateSchd.putExtra("schdNum", schdNum);
                updateSchd.putExtra("user_id", user_id);
                finish();
                startActivity(updateSchd);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        Intent schdIntent = new Intent(getApplicationContext(), MainActivity.class);
        schdIntent.putExtra("schd", true);
        startActivity(schdIntent);
    }

    public void deleteAlert(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setMessage("정말로 삭제 하시겠습니까?");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            DeleteSchd task = new DeleteSchd();
                            String result = task.execute(user_id, schdNum).get();
                            Boolean check = Boolean.parseBoolean(result);
                            Log.d("check", check.toString());
                            if(check) {
                                finish();
                                Intent schdIntent = new Intent(getApplicationContext(), MainActivity.class);
                                schdIntent.putExtra("schd", true);
                                startActivity(schdIntent);
                            }
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
    }
}