package com.runtimeterror.bcu_commu;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    final String blankSha256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";
    String ID, PW;

    Button loginBtn;
    Button registerBtn;

    EditText edtID;
    EditText edtPW;

    TextView textErr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtID = findViewById(R.id.inputID);
        edtPW = findViewById(R.id.inputPW);

        textErr = findViewById(R.id.textErr);

        // 로그인 버튼
        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ID = edtID.getText().toString();
                PW = sha256(edtPW.getText().toString());

                Intent login = new Intent(getApplicationContext(), MainActivity.class);
                Log.d("pw", PW);
                // 로그인 테스트
                startActivity(login);
/*
                try {
                    String result;

                    ID = edtID.getText().toString();
                    PW = sha256(edtPW.getText().toString());

                    if(ID.equals("")||PW.equals(blankSha256)){
                        Log.d("빈칸", "빈칸 있음.");
                    }else{
                        if(PW.equals()){
                            Register task = new Register();
                            result = task.execute(ID, PW).get();
                        } else {
                            textErr.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    Log.i("DBtest", ".....ERROR.....!");
                }
 */
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