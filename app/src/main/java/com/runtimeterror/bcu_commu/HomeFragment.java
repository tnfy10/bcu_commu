package com.runtimeterror.bcu_commu;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment implements OnBackPressedListener{
    final String freeBoardTitle = "자유게시판";
    final String meetBoardTitle = "모임게시판";
    final String subjBoardTitle = "과제게시판";

    MainActivity activity;
    long backKeyPressedTime;
    Toast backPressToast;

    String[] latest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup homeView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        activity = (MainActivity) getActivity();
        backPressToast = Toast.makeText(getContext(),"한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT);





        // 사이트 이동 리사이클러뷰
        RecyclerView redirectView = homeView.findViewById(R.id.redirectView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(homeView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        redirectView.setLayoutManager(layoutManager);
        final SiteAdapter siteAdapter = new SiteAdapter();

        siteAdapter.addItem(new Site("부천대학교", 1));
        siteAdapter.addItem(new Site("포털사이트", 2));
        siteAdapter.addItem(new Site("학교공지사항", 3));
        siteAdapter.addItem(new Site("셔틀버스", 4));
        siteAdapter.addItem(new Site("종합민원", 5));
        siteAdapter.addItem(new Site("캠퍼스맵", 6));
        siteAdapter.addItem(new Site("e-Class", 7));

        redirectView.setAdapter(siteAdapter);

        siteAdapter.setOnItemClickListener(new OnSiteItemClickListener() {
            @Override
            public void onItemClick(SiteAdapter.ViewHolder holder, View view, int position) {
                Site item = siteAdapter.getItem(position);
                switch(item.getCode()){
                    case 1:
                        Intent site1 = new Intent(Intent.ACTION_VIEW);
                        Uri uri1 = Uri.parse("https://www.bc.ac.kr");
                        site1.setData(uri1);
                        startActivity(site1);
                        break;
                    case 2:
                        Intent site2 = new Intent(Intent.ACTION_VIEW);
                        Uri uri2 = Uri.parse("https://portal.bc.ac.kr");
                        site2.setData(uri2);
                        startActivity(site2);
                        break;
                    case 3:
                        Intent site3 = new Intent(Intent.ACTION_VIEW);
                        Uri uri3 = Uri.parse("https://www.bc.ac.kr/user/nd25285.do");
                        site3.setData(uri3);
                        startActivity(site3);
                        break;
                    case 4:
                        Intent site4 = new Intent(Intent.ACTION_VIEW);
                        Uri uri4 = Uri.parse("https://www.bc.ac.kr/user/nd48760.do");
                        site4.setData(uri4);
                        startActivity(site4);
                        break;
                    case 5:
                        Intent site5 = new Intent(Intent.ACTION_VIEW);
                        Uri uri5 = Uri.parse("https://www.bc.ac.kr/user/nd37413.do");
                        site5.setData(uri5);
                        startActivity(site5);
                        break;
                    case 6:
                        Intent site6 = new Intent(Intent.ACTION_VIEW);
                        Uri uri6 = Uri.parse("https://www.bc.ac.kr/user/nd27473.do");
                        site6.setData(uri6);
                        startActivity(site6);
                        break;
                    case 7:
                        Intent site7 = new Intent(Intent.ACTION_VIEW);
                        Uri uri7 = Uri.parse("https://eclass.bc.ac.kr");
                        site7.setData(uri7);
                        startActivity(site7);
                        break;
                }
            }
        });

        // 게시판 모음 리사이클러뷰
        RecyclerView latestBoardView = homeView.findViewById(R.id.latestBoardView);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(homeView.getContext(), LinearLayoutManager.VERTICAL, false);
        latestBoardView.setLayoutManager(layoutManager2);
        final LatestBoardAdapter latestBoardAdapter = new LatestBoardAdapter();

        latest = getPost("free");
        latestBoardAdapter.addItem(new LatestBoard(freeBoardTitle, latest[0], latest[1], latest[2]));
        latest = getPost("meet");
        latestBoardAdapter.addItem(new LatestBoard(meetBoardTitle, latest[0], latest[1], latest[2]));
        latest = getPost("subj");
        latestBoardAdapter.addItem(new LatestBoard(subjBoardTitle, latest[0], latest[1], latest[2]));

        latestBoardView.setAdapter(latestBoardAdapter);

        latestBoardAdapter.setOnItemClickListener(new OnLatestBoardClickListener() {
            @Override
            public void onItemClick(LatestBoardAdapter.ViewHolder holder, View view, int position) {
                LatestBoard item = latestBoardAdapter.getItem(position);
                switch(item.getTitle()){
                    case freeBoardTitle:
                        Intent freeIntent = new Intent(homeView.getContext(), FreeBoardActivity.class);
                        getActivity().finish();
                        startActivity(freeIntent);
                        break;
                    case meetBoardTitle:
                        Intent meetIntent = new Intent(homeView.getContext(), MeetBoardActivity.class);
                        getActivity().finish();
                        startActivity(meetIntent);
                        break;
                    case subjBoardTitle:
                        Intent subjIntent = new Intent(homeView.getContext(), SubjBoardActivity.class);
                        getActivity().finish();
                        startActivity(subjIntent);
                        break;
                }
            }
        });

        return homeView;
    }

    public String[] getPost(String board){
        String[] arrayPost = {"-", "-", "-"};
        try {
            String result;
            String post1;
            String post2;
            String post3;

            GetLatestPost task = new GetLatestPost();
            result = task.execute(board).get();
            Log.d("latestpost", result);

            JSONObject json = null;
            json = new JSONObject(result);

            JSONArray jarray = json.getJSONArray("latestpost");
            JSONObject jObject = jarray.getJSONObject(0);
            post1 = jObject.optString("post1");
            jObject = jarray.getJSONObject(1);
            post2 = jObject.optString("post2");
            jObject = jarray.getJSONObject(2);
            post3 = jObject.optString("post3");
            arrayPost[0] = post1;
            arrayPost[1] = post2;
            arrayPost[2] = post3;
        } catch (JSONException je) {
            je.printStackTrace();
            Log.d("json", "json 에러");
        } catch (Exception e){
            e.printStackTrace();
            Log.i("DBtest", ".....ERROR.....!");
        }
        return arrayPost;
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

    @Override
    public void onResume() {
        super.onResume();
        activity.setOnBackPressedListener(this);
    }
}