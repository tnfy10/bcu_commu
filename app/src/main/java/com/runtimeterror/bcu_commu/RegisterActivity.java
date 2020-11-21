package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterActivity extends AppCompatActivity {
    EditText edtName;
    EditText edtStdNum;
    EditText edtID;
    EditText edtPW;
    EditText edtPW2;

    TextView txtIDErr;
    TextView txtPWErr;

    Button viewAgreeBtn;
    Button regConfirmBtn;

    String name, stdNum, ID, PW, PW2;
    final String blankSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.setName);
        edtStdNum = findViewById(R.id.setStdNum);
        edtID = findViewById(R.id.setID);
        edtPW = findViewById(R.id.setPW);
        edtPW2 = findViewById(R.id.setPW2);

        txtIDErr = findViewById(R.id.IDErrMsg);
        txtPWErr = findViewById(R.id.PWErrMsg);

        viewAgreeBtn = findViewById(R.id.viewAgreeBtn);
        regConfirmBtn = findViewById(R.id.regConfirmBtn);

        // 아이디 중복 확인
/*
        edtID.getOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    // TODO
                }
            }
        });
*/
        /* // 개인정보 수집 동의서 버튼
        viewAgreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO DO
            }
        });
         */
        // 가입완료 버튼
        regConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*    오라클 연동
                try {
                    String result;

                    name = edtName.getText().toString();
                    stdNum = edtStdNum.getText().toString();
                    ID = edtID.getText().toString();
                    PW = sha256(edtPW.getText().toString());
                    PW2 = sha256(edtPW2.getText().toString());

                    if(name.equals("") || stdNum.equals("") || ID.equals("") || PW.equals(blankSha256) || PW2.equals(blankSha256)){
                        Log.d("빈칸", "빈칸 있음.");
                    }else{
                        if(PW.equals(PW2)){
                            Register task = new Register();
                            result = task.execute(ID, stdNum, PW, name).get();
                        } else {
                            txtPWErr.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
                 */
            }
        });
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