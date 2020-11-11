package com.runtimeterror.bcu_commu;

import android.content.Context;
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
    Button QnABtn;
    Button appNoticeBtn;
    Button privacyInfoBtn;
    Button quitBtn;
    Button logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup setView = (ViewGroup) inflater.inflate(R.layout.fragment_set, container, false);

        txtVer = setView.findViewById(R.id.txtVer);
        txtVer.setText(getVersionInfo(setView.getContext()));

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