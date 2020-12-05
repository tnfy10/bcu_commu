package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class AddSchdActivity extends AppCompatActivity {
    String date, title, memo;
    String schdNum, user_id;

    EditText year, month, day;
    EditText setSchdName, setSchdMemo;

    ImageView exitBtn;

    Button setBtn;
    TextView checkBlank;

    SQLiteDatabase sqlDB;
    myDBHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schd);
        checkBlank = findViewById(R.id.checkBlank);

        exitBtn = findViewById(R.id.exitBtn);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        year = findViewById(R.id.year);
        month =findViewById(R.id.month);
        day = findViewById(R.id.day);

        setSchdName = findViewById(R.id.setSchdName);
        setSchdMemo = findViewById(R.id.setSchdMemo);

        if(getIntent().getBooleanExtra("update", false)){
            String[] dateArr = getIntent().getStringExtra("time").split("-");
            year.setText(dateArr[0]);
            month.setText(dateArr[1]);
            day.setText(dateArr[2]);
            setSchdName.setText(getIntent().getStringExtra("title"));
            setSchdMemo.setText(getIntent().getStringExtra("content"));
            schdNum = getIntent().getStringExtra("schdNum");
            user_id = getIntent().getStringExtra("user_id");
        }

        setBtn = findViewById(R.id.setBtn);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = year.getText().toString() + "년 " + month.getText().toString() + "월 " + day.getText().toString() + "일";
                title = setSchdName.getText().toString();
                memo = setSchdMemo.getText().toString();

                if(date.length()<9 || title.length()==0 || memo.length()==0){   // 빈칸 체크 보완 필요
                    checkBlank.setVisibility(View.VISIBLE);
                } else {
                    if(getIntent().getBooleanExtra("update", false)){
                        try {
                            UpdateSchd task = new UpdateSchd();
                            String date = year.getText().toString() + "-" + month.getText().toString() + "-" + day.getText().toString();
                            String result = task.execute(user_id, date, setSchdName.getText().toString(), setSchdMemo.getText().toString(), schdNum).get();
                            Boolean check = Boolean.parseBoolean(result);

                            if(check){
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
                    }else{
                        writeSchd();
                    }
                }
            }
        });
    }

    public void writeSchd(){
        try {
            myHelper = new myDBHelper(getApplicationContext());
            sqlDB = myHelper.getReadableDatabase();
            Cursor cursor;
            cursor = sqlDB.rawQuery("SELECT * FROM userTBL;", null);
            cursor.moveToNext();

            String username = cursor.getString(0);

            cursor.close();
            sqlDB.close();

            AddSchd task = new AddSchd();
            String date = year.getText().toString() + "-" + month.getText().toString() + "-" + day.getText().toString();
            String result = task.execute(setSchdName.getText().toString(), setSchdMemo.getText().toString(), username, date).get();
            Boolean check = Boolean.parseBoolean(result);

            if(check){
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