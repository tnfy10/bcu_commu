package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class PostDetailActivity extends AppCompatActivity {
    ImageView prevBtn;
    ImageView postProfileImg;
    ImageView deleteBtn;
    TextView txtWriter;
    TextView txtWriteTime;
    TextView detailPostTitle;
    TextView detailPostContent;
    LinearLayout userInfo;

    String writer, writeTime, title, content, post_num, board, user_id;

    SQLiteDatabase sqlDB;
    myDBHelper myHelper;

    Boolean visible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);

        myHelper = new myDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM userTBL;", null);
        cursor.moveToNext();

        user_id = cursor.getString(0);

        cursor.close();
        sqlDB.close();

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
                board = getIntent().getStringExtra("board");

                if(writer.equals(user_id)){
                    deleteAlert(v);
                }else{
                    Toast.makeText(getApplicationContext(), "본인 게시글만 삭제 가능합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        postProfileImg = findViewById(R.id.postProfileImg);
        txtWriter = findViewById(R.id.txtWriter);
        txtWriteTime = findViewById(R.id.txtWriteTime);
        detailPostTitle = findViewById(R.id.detailPostTitle);
        detailPostContent = findViewById(R.id.detailPostContent);
        userInfo = findViewById(R.id.userInfo);

        writer = getIntent().getStringExtra("writer");
        writeTime = getIntent().getStringExtra("writeTime");
        title = getIntent().getStringExtra("title");
        content = getIntent().getStringExtra("content");

        txtWriter.setText(writer);
        txtWriteTime.setText(writeTime);
        detailPostTitle.setText(title);
        detailPostContent.setText(content);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void deleteAlert(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        //builder.setTitle("회원탈퇴");
        builder.setMessage("삭제 하시겠습니까?");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(board.equals("free")){
                            post_num = getIntent().getStringExtra("postNum");
                            try {
                                DeletePost task = new DeletePost();
                                String result = task.execute(board, post_num).get();
                                Boolean check = Boolean.parseBoolean(result);

                                Log.d("check", check.toString());
                                if(check) {
                                    Log.d("check", "게시글 삭제가 성공하였습니다.");
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), FreeBoardActivity.class));
                                }else{
                                    Log.d("check", "게시글 삭제 실패");
                                }
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else if(board.equals("meet")){
                            post_num = getIntent().getStringExtra("postNum");
                            try {
                                DeletePost task = new DeletePost();
                                String result = task.execute(board, post_num).get();
                                Boolean check = Boolean.parseBoolean(result);

                                Log.d("check", check.toString());
                                if(check) {
                                    Log.d("check", "게시글 삭제가 성공하였습니다.");
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), MeetBoardActivity.class));
                                }else{
                                    Log.d("check", "게시글 삭제 실패");
                                }
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }else if(board.equals("subj")){
                            post_num = getIntent().getStringExtra("postNum");
                            try {
                                DeletePost task = new DeletePost();
                                String result = task.execute(board, post_num).get();
                                Boolean check = Boolean.parseBoolean(result);

                                Log.d("check", check.toString());
                                if(check) {
                                    Log.d("check", "게시글 삭제가 성공하였습니다.");
                                    finish();
                                    startActivity(new Intent(getApplicationContext(), SubjBoardActivity.class));
                                }else{
                                    Log.d("check", "게시글 삭제 실패");
                                }
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
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

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "userDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}