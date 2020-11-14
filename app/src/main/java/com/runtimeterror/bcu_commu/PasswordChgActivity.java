package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordChgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_chg);
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