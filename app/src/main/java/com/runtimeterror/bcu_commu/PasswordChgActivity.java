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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordChgActivity extends AppCompatActivity {
    final String blankSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    final String title = "비밀번호 변경";
    String ID=null, nowPW, newPW, newPW2;

    TextView txtTitle;
    ImageView prevBtn;
    Button chgPWBtn;

    EditText edtNowPW;
    EditText edtNewPw, edtNewPW2;

    TextView chkNewPW, pwErr, blankPW;

    SQLiteDatabase sqlDB;
    myDBHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_chg);

        myHelper = new myDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM userTBL;", null);
        cursor.moveToNext();

        ID = cursor.getString(0);

        cursor.close();
        sqlDB.close();

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        prevBtn = findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtNowPW = findViewById(R.id.edtNowPW);
        edtNewPw = findViewById(R.id.edtNewPW);
        edtNewPW2 = findViewById(R.id.edtNewPW2);

        chkNewPW = findViewById(R.id.chkNewPW);
        pwErr = findViewById(R.id.pwErr);
        blankPW = findViewById(R.id.blankPW);

        chgPWBtn = findViewById(R.id.chgPWBtn);
        chgPWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowPW = sha256(edtNowPW.getText().toString());
                newPW = sha256(edtNewPw.getText().toString());
                newPW2 = sha256(edtNewPW2.getText().toString());

                pwErr.setVisibility(View.INVISIBLE);

                if(nowPW.equals(blankSha256) || newPW.equals(blankSha256) || newPW2.equals(blankSha256)){
                    blankPW.setVisibility(View.VISIBLE);
                }else{
                    blankPW.setVisibility(View.INVISIBLE);
                    // 새 비밀번호 일치 확인
                    if(!(newPW.equals(newPW2))){
                        chkNewPW.setVisibility(View.VISIBLE);
                    }else{
                        chkNewPW.setVisibility(View.INVISIBLE);
                        try {
                            String result;
                            Boolean check;

                            PWChange task = new PWChange();
                            result = task.execute(ID, nowPW, newPW).get();
                            Log.d("DB", result);
                            check = Boolean.parseBoolean(result);

                            if(check){
                                sqlDB = myHelper.getWritableDatabase();
                                sqlDB.execSQL("DROP TABLE IF EXISTS userTBL");
                                sqlDB.close();
                                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                                finish();
                                startActivity(login);
                            }else {
                                // 현재 비밀번호와 일치하지 않으면 메시지 보이도록 함
                                pwErr.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception e) {
                            Log.i("DBtest", ".....ERROR.....!");
                        }
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
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

    // SHA-256 암호화
    public static String sha256(String str){
        String SHA = "";
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer();
            for(int i=0; i<byteData.length; i++)
                sb.append(Integer.toString((byteData[i]&0xff)+0x100, 16).substring(1));
            SHA = sb.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            SHA = null;
        }
        return SHA;
    }
}