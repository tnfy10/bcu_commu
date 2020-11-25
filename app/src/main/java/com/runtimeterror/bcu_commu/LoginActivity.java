package com.runtimeterror.bcu_commu;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {
    final String blankSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    String ID, PW, name=null, stdNum=null;

    Button loginBtn;
    Button registerBtn;

    EditText edtID;
    EditText edtPW;

    TextView textErr;

    SQLiteDatabase sqlDB;
    myDBHelper myHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myHelper = new myDBHelper(this);

        edtID = findViewById(R.id.inputID);
        edtPW = findViewById(R.id.inputPW);

        textErr = findViewById(R.id.textErr);

        // 로그인 버튼
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String result;
                    Boolean check;

                    ID = edtID.getText().toString();
                    PW = sha256(edtPW.getText().toString());

                    if(ID.equals("")||PW.equals(blankSha256)){
                        Log.d("빈칸", "빈칸 있음.");
                    }else{
                        Login task = new Login();
                        result = task.execute(ID, PW).get();
                        check = Boolean.parseBoolean(result);
                        if(check){
                            sqlDB = myHelper.getWritableDatabase();
                            if(!checkTable(sqlDB)) {
                                sqlDB.execSQL("CREATE TABLE  userTBL ( uId CHAR(20), uName CHAR(20), uStdNum CHAR(10));");
                            }else{
                                sqlDB.execSQL("DROP TABLE IF EXISTS userTBL");
                                sqlDB.execSQL("CREATE TABLE  userTBL ( uId CHAR(20), uName CHAR(20), uStdNum CHAR(10));");
                            }

                            String result2;

                            User task2 = new User();
                            result2 = task2.execute(ID).get();

                            String[] splited = result2.split(",");
                            name = splited[0];
                            stdNum = splited[1];

                            sqlDB.execSQL("INSERT INTO userTBL VALUES ( '" + ID + "', '" + name + "', '" + stdNum + "');" );
                            sqlDB.close();
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            textErr.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
            }
        });

        // 회원가입 버튼
        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register);
            }
        });
    }

    private boolean checkTable( SQLiteDatabase db){
        //catch에 안 붙잡히면 테이블이 있다는 의미이므로 true, 잡히면 테이블이 없으므로 false를 반환
        try{
            db.rawQuery("SELECT '' FROM userTBL limit 1;" , null);
        }catch(SQLiteException e){
            e.printStackTrace();
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // SQLite 아이디 저장
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