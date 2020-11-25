package com.runtimeterror.bcu_commu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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

    SQLiteDatabase sqlDB;
    myDBHelper myHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup setView = (ViewGroup) inflater.inflate(R.layout.fragment_set, container, false);

        txtVer = setView.findViewById(R.id.txtVer);
        txtVer.setText(getVersionInfo(setView.getContext()));

        userId = setView.findViewById(R.id.userId);
        userName = setView.findViewById(R.id.userName);
        userStdNum = setView.findViewById(R.id.userStdNum);

        myHelper = new myDBHelper(setView.getContext());
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM userTBL;", null);
        cursor.moveToNext();

        userId.setText(cursor.getString(0));
        userName.setText(cursor.getString(1));
        userStdNum.setText(cursor.getString(2));

        cursor.close();
        sqlDB.close();

        imgChgBtn = setView.findViewById(R.id.imgChgBtn);
        imgChgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgChgAlert(setView);
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
                quitAlert(setView);
            }
        });

        logoutBtn = setView.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("DROP TABLE IF EXISTS userTBL");
                sqlDB.close();
                Intent login = new Intent(setView.getContext(), LoginActivity.class);
                getActivity().finish();
                startActivity(login);
            }
        });

        return setView;
    }

    public void imgChgAlert(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("프로필 이미지 설정");
        builder.setMessage("아직 미구현된 기능입니다.");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
        /*
        final List<String> ListItems = new ArrayList<>();
        ListItems.add("프로필 이미지 변경");
        ListItems.add("프로필 이미지 삭제");
        final CharSequence[] items =  ListItems.toArray(new String[ ListItems.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("프로필 이미지 설정");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int pos) {
                switch(pos){
                    case 0:
                        // TODO - 프로필 이미지 변경
                        break;
                    case 1:
                        // TODO - 프로필 이미지 삭제
                        break;
                }
            }
        });
        builder.show();
         */
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

    public void quitAlert(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("회원탈퇴");
        builder.setMessage("정말로 회원탈퇴를 하시겠습니까?");
        builder.setPositiveButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO - 회원탈퇴 처리
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builder.show();
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