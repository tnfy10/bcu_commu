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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ScheduleFragment extends Fragment {
    String date, title, memo;

    RecyclerView schdList;
    LinearLayoutManager layoutManager;
    ScheduleAdapter scheduleAdapter;

    FloatingActionButton addSchdBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup scheduleView = (ViewGroup) inflater.inflate(R.layout.fragment_schedule, container, false);

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
}