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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

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
                            if(checkTable(sqlDB)) {
                                sqlDB.execSQL("DROP TABLE IF EXISTS userTBL");
                            }
                            sqlDB.execSQL("CREATE TABLE  userTBL ( uId CHAR(20), uName CHAR(20), uStdNum CHAR(10));");

                            String result2;

                            User task2 = new User();
                            result2 = task2.execute(ID).get();
                            Log.d("result2", result2);

                            String[] splited = result2.split(",");
                            name = splited[0];
                            stdNum = splited[1];

                            sqlDB.execSQL("INSERT INTO userTBL VALUES ( '" + ID + "', '" + name + "', '" + stdNum + "');" );
                            sqlDB.close();
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

        /* 회원탈퇴 로직
         * setFragment에서 회원 DB의 내용을 지울 경우 앱이 죽는 상황이 발생
         * => SQLite의 setFragment 테이블은 DROP문으로 삭제
         * => INTENT문과 함께 PUTEXTRA로 ID값을 로그인 액티비티로 전달
         * => 단, 로그인 액티비티가 회원탈퇴에 의해서 불려진 것인지 구분하기 위해 Boolean형으로 getIsQuit 변수 선언
         * => getIsQuit가 True라면 회원탈퇴 진행 (False라면 진행하지 않음)
         */

        Boolean getIsQuit;
        if (getIntent().getBooleanExtra("isQuit", false)) getIsQuit = true;
        else getIsQuit = false;
        String quitId = getIntent().getStringExtra("id");

        Log.d("id", getIsQuit.toString());
        if(getIsQuit){
            Withdrawal task = new Withdrawal();
            try {
                String result = task.execute(quitId).get();
                Boolean check = Boolean.parseBoolean(result);
                if(check){
                    Toast.makeText(getApplicationContext(), "회원탈퇴에 성공하셨습니다.", Toast.LENGTH_SHORT);
                    getIsQuit = false;
                }
                else {
                    Toast.makeText(getApplicationContext(), "회원탈퇴에 실패하셨습니다.", Toast.LENGTH_SHORT);
                    getIsQuit = false;
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
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