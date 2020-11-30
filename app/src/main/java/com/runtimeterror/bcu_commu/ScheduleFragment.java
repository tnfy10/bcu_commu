package com.runtimeterror.bcu_commu;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ScheduleFragment extends Fragment implements OnBackPressedListener{
    String date, title, memo;
    String id;

    RecyclerView schdList;
    LinearLayoutManager layoutManager;
    ScheduleAdapter scheduleAdapter;

    FloatingActionButton addSchdBtn;

    MainActivity activity;
    long backKeyPressedTime;
    Toast backPressToast;

    SQLiteDatabase sqlDB;
    myDBHelper myHelper;

    ArrayList<String[]> schdArray = new ArrayList<String[]>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup scheduleView = (ViewGroup) inflater.inflate(R.layout.fragment_schedule, container, false);
        activity = (MainActivity) getActivity();
        backPressToast = Toast.makeText(getContext(),"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);

        schdList = scheduleView.findViewById(R.id.scheduleList);
        layoutManager = new LinearLayoutManager(scheduleView.getContext(), LinearLayoutManager.VERTICAL, false);

        schdList.setLayoutManager(layoutManager);
        scheduleAdapter = new ScheduleAdapter();

        myHelper = new myDBHelper(scheduleView.getContext());
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM userTBL;", null);
        cursor.moveToNext();

        id = cursor.getString(0);

        cursor.close();
        sqlDB.close();

        getSchd(id);

        // 일정 만들기 버튼
        addSchdBtn = scheduleView.findViewById(R.id.addSchdBtn);
        addSchdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(scheduleView.getContext(), AddSchdActivity.class);
                startActivityForResult(addIntent, 1);
            }
        });

        // 데이터 받아오면 추가
        Collections.reverse(schdArray);
        for(int i=0; i<schdArray.size(); i++){
            String[] schedule = schdArray.get(i);
            scheduleAdapter.addItem(new Schedule(schedule[2], schedule[1], schedule[3], schedule[0]));
        }

        scheduleAdapter.setOnItemClickListener(new OnSchdItemClickListener() {
            @Override
            public void onItemClick(ScheduleAdapter.ViewHolder holder, View view, int position) {
                Schedule item = scheduleAdapter.getItem(position);
                Intent detailIntent = new Intent(scheduleView.getContext(), DetailSchdActivity.class);
                detailIntent.putExtra("title", item.getName());
                detailIntent.putExtra("time", item.getDate());
                detailIntent.putExtra("content", item.getMemo());
                detailIntent.putExtra("schdNum", item.getSchdNum());
                detailIntent.putExtra("user_id", id);
                startActivity(detailIntent);
            }
        });
        
        schdList.setAdapter(scheduleAdapter);
        return scheduleView;
    }

    public void getSchd(String id){
        try {
            String result;
            String schedule_num;   // 일정 번호0
            String write_date;     // 일정 날짜1
            String subject;        // 일정 제목2
            String content;        // 일정 내용3

            GetSchd task = new GetSchd();
            result = task.execute(id).get();
            Log.d("schd", result);

            JSONObject json = null;
            json = new JSONObject(result);

            JSONArray jarray = json.getJSONArray("schedule");
            for(int i=0; i<jarray.length(); i++){
                HashMap map = new HashMap<>();
                String[] arraySchd = new String[4];
                JSONObject jObject = jarray.getJSONObject(i);

                schedule_num = jObject.optString("schedule_num");
                write_date = jObject.optString("write_date");
                subject = jObject.optString("subject");
                content = jObject.optString("content");

                arraySchd[0] = schedule_num;
                arraySchd[1] = write_date;
                arraySchd[2] = subject;
                arraySchd[3] = content;

                schdArray.add(arraySchd);
            }
        } catch (JSONException je) {
            je.printStackTrace();
            Log.d("json", "json 에러");
        } catch (Exception e){
            e.printStackTrace();
            Log.i("DBtest", ".....ERROR.....!");
        }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode==RESULT_OK){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(this).attach(this).commit();
            }
        }
    }
}