package com.runtimeterror.bcu_commu;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ScheduleFragment extends Fragment implements OnBackPressedListener{
    String date, title, memo;

    RecyclerView schdList;
    LinearLayoutManager layoutManager;
    ScheduleAdapter scheduleAdapter;

    FloatingActionButton addSchdBtn;

    MainActivity activity;
    long backKeyPressedTime;
    Toast backPressToast;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup scheduleView = (ViewGroup) inflater.inflate(R.layout.fragment_schedule, container, false);
        activity = (MainActivity) getActivity();
        backPressToast = Toast.makeText(getContext(),"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);

        schdList = scheduleView.findViewById(R.id.scheduleList);
        layoutManager = new LinearLayoutManager(scheduleView.getContext(), LinearLayoutManager.VERTICAL, false);

        schdList.setLayoutManager(layoutManager);
        scheduleAdapter = new ScheduleAdapter();

        // 일정 만들기 버튼
        addSchdBtn = scheduleView.findViewById(R.id.addSchdBtn);
        addSchdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(scheduleView.getContext(), AddSchdActivity.class);
                startActivity(addIntent);
            }
        });

        // 데이터 받아오면 추가
        //scheduleAdapter.addItem(new Schedule(title, date, memo));
        
        schdList.setAdapter(scheduleAdapter);
        return scheduleView;
    }

    @Override
    public void onBackPressed() {
        //터치간 시간을 줄이거나 늘리고 싶다면 2000을 원하는 시간으로 변경해서 사용하시면 됩니다.
        if(System.currentTimeMillis() > backKeyPressedTime + 2000){
            backKeyPressedTime = System.currentTimeMillis();
            backPressToast.show();
            return;
        }
        if(System.currentTimeMillis() <= backKeyPressedTime + 2000){
            getActivity().finish();
            backPressToast.cancel();
        }
    }

    @Override public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(this);
    }
}