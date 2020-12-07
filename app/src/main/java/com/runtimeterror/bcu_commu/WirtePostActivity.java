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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class WirtePostActivity extends AppCompatActivity {
    ImageView writeBtn;
    ImageView prevBtn;

    EditText edtPostName;
    EditText edtPost;

    SQLiteDatabase sqlDB;
    myDBHelper myHelper;

    Boolean freeBoard;
    Boolean meetBoard;
    Boolean subjBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wirte_post);

        edtPostName = findViewById(R.id.edtPostName);
        edtPost = findViewById(R.id.edtPost);

        if(getIntent().getBooleanExtra("update", false)){
            edtPostName.setText(getIntent().getStringExtra("title"));
            edtPost.setText(getIntent().getStringExtra("content"));
        }

        freeBoard = getIntent().getBooleanExtra("freeBoard", false);
        meetBoard = getIntent().getBooleanExtra("meetBoard", false);
        subjBoard = getIntent().getBooleanExtra("subjBoard", false);

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
                if(edtPostName.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }else{
                    if(edtPost.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(), "내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }else{
                        if(getIntent().getBooleanExtra("update", false)){
                            updatePost();
                        }else{
                            writePost();
                        }
                    }
                }
            }
        });
    }

    public void writePost(){
        myHelper = new myDBHelper(getApplicationContext());
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM userTBL;", null);
        cursor.moveToNext();

        String userId = cursor.getString(0);

        cursor.close();
        sqlDB.close();

        SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd");

        Calendar time = Calendar.getInstance();

        String postdate = format.format(time.getTime());

        Post post = new Post(edtPostName.getText().toString(), edtPost.getText().toString(), userId, postdate);

        if(freeBoard) {
            try {
                FreePost task = new FreePost();
                String result = task.execute(post.getTitle(), post.getContent(), post.getWriter(), post.getTime()).get();
                Boolean check = Boolean.parseBoolean(result);

                Log.d("check", check.toString());
                if(check) {
                    Log.d("check", "게시글 작성에 성공하셨습니다.");
                    finish();
                    startActivity(new Intent(getApplicationContext(), FreeBoardActivity.class));
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(meetBoard) {
            try {
                MeetPost task = new MeetPost();
                String result = task.execute(post.getTitle(), post.getContent(), post.getWriter(), post.getTime()).get();
                Boolean check = Boolean.parseBoolean(result);
                Log.d("checdk", check.toString());

                if(check) {

                    finish();
                    startActivity(new Intent(getApplicationContext(), MeetBoardActivity.class));
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(subjBoard) {
            try {
                SubjectPost task = new SubjectPost();
                String result = task.execute(post.getTitle(), post.getContent(), post.getWriter(), post.getTime()).get();
                Boolean check = Boolean.parseBoolean(result);

                if (check) {
                    Log.d("check", "게시글 작성에 성공하셨습니다.");
                    finish();
                    startActivity(new Intent(getApplicationContext(), SubjBoardActivity.class));
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updatePost(){
        String board = getIntent().getStringExtra("board");
        String title = edtPostName.getText().toString();
        String content = edtPost.getText().toString();
        String postNum = getIntent().getStringExtra("postNum");

        try {
            UpdatePost task = new UpdatePost();
            String result = task.execute(board, title, content, postNum).get();
            Boolean check = Boolean.parseBoolean(result);

            if(check) {
                Log.d("check", "게시글 수정에 성공하셨습니다.");
                finish();
                switch (board){
                    case "free":
                        startActivity(new Intent(getApplicationContext(), FreeBoardActivity.class));
                        break;
                    case "meet":
                        startActivity(new Intent(getApplicationContext(), MeetBoardActivity.class));
                        break;
                    case "subj":
                        startActivity(new Intent(getApplicationContext(), SubjBoardActivity.class));
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "게시판 값 전달 에러", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if(freeBoard) {
            finish();
            startActivity(new Intent(getApplicationContext(), FreeBoardActivity.class));
        }
        else if(meetBoard) {
            finish();
            startActivity(new Intent(getApplicationContext(), MeetBoardActivity.class));
        }
        else if(subjBoard) {
            finish();
            startActivity(new Intent(getApplicationContext(), SubjBoardActivity.class));
        }
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