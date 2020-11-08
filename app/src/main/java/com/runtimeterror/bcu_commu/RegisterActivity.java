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

    String PW, PW2, encryptPW;

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
                PW = edtPW.getText().toString();
                PW2 = edtPW2.getText().toString();

                if(PW.equals(PW2)){
                    encryptPW = sha256(PW);
                    Log.d("encrypt: ", encryptPW); // 암호화 디버그
                } else {
                    txtPWErr.setVisibility(View.VISIBLE);
                }
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