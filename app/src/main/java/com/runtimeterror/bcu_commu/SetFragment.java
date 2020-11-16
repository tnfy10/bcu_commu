package com.runtimeterror.bcu_commu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SetFragment extends Fragment {
    TextView txtVer;
    TextView userId;
    TextView userName;
    TextView userStdNum;

    Button imgChgBtn;
    Button PWChgBtn;
    Button appNoticeBtn;
    Button privacyInfoBtn;
    Button quitBtn;
    Button logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup setView = (ViewGroup) inflater.inflate(R.layout.fragment_set, container, false);

        txtVer = setView.findViewById(R.id.txtVer);
        txtVer.setText(getVersionInfo(setView.getContext()));

        userId = setView.findViewById(R.id.userId);
        userName = setView.findViewById(R.id.userName);
        userStdNum = setView.findViewById(R.id.userStdNum);

        imgChgBtn = setView.findViewById(R.id.imgChgBtn);
        imgChgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO DO
            }
        });

        PWChgBtn = setView.findViewById(R.id.PWChgBtn);
        PWChgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent PWChgIntent = new Intent(setView.getContext(), PasswordChgActivity.class);
                startActivity(PWChgIntent);
            }
        });

        appNoticeBtn = setView.findViewById(R.id.appNoticeBtn);
        appNoticeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent noticeIntent = new Intent(setView.getContext(), NoticeActivity.class);
                startActivity(noticeIntent);
            }
        });

        privacyInfoBtn = setView.findViewById(R.id.privacyInfoBtn);
        privacyInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent privacyInfoIntent = new Intent(setView.getContext(), PrivacyInfoActivity.class);
                startActivity(privacyInfoIntent);
            }
        });

        quitBtn = setView.findViewById(R.id.quitBtn);
        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO DO
            }
        });

        logoutBtn = setView.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TO DO
            }
        });


        return setView;
    }

    // 앱 버전 반환
    public String getVersionInfo(Context context){
        String version = null;
        try{
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pi.versionName;
        }catch(PackageManager.NameNotFoundException e) {}
        return version;
    }
}