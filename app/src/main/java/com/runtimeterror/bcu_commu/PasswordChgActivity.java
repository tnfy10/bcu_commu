package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordChgActivity extends AppCompatActivity {
    final String title = "비밀번호 변경";
    String nowPW, newPW, newPW2;

    TextView txtTitle;
    ImageView prevBtn;
    Button chgPWBtn;

    EditText edtNowPW;
    EditText edtNewPw, edtNewPW2;

    TextView chkNewPW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_chg);
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

        chgPWBtn = findViewById(R.id.chgPWBtn);
        chgPWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowPW = sha256(edtNowPW.getText().toString());
                newPW = sha256(edtNewPw.getText().toString());
                newPW2 = sha256(edtNewPW2.getText().toString());

                // TODO - 현재 비밀번호 맞는지 확인하는 코드

                // 새 비밀번호 일치 확인
                if(!(newPW.equals(newPW2))){
                    chkNewPW.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
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