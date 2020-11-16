package com.runtimeterror.bcu_commu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {
    final String freeBoardTitle = "자유게시판";
    final String meetBoardTitle = "모임게시판";
    final String subjBoardTitle = "과제게시판";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup homeView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

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

        latestBoardAdapter.addItem(new LatestBoard(freeBoardTitle, "게시물1", "게시물2", "게시물3"));
        latestBoardAdapter.addItem(new LatestBoard(meetBoardTitle, "게시물1", "게시물2", "게시물3"));
        latestBoardAdapter.addItem(new LatestBoard(subjBoardTitle, "게시물1", "게시물2", "게시물3"));

        latestBoardView.setAdapter(latestBoardAdapter);

        latestBoardAdapter.setOnItemClickListener(new OnLatestBoardClickListener() {
            @Override
            public void onItemClick(LatestBoardAdapter.ViewHolder holder, View view, int position) {
                LatestBoard item = latestBoardAdapter.getItem(position);
                switch(item.getTitle()){
                    case freeBoardTitle:
                        Intent freeIntent = new Intent(homeView.getContext(), FreeBoardActivity.class);
                        startActivity(freeIntent);
                        break;
                    case meetBoardTitle:
                        Intent meetIntent = new Intent(homeView.getContext(), MeetBoardActivity.class);
                        startActivity(meetIntent);
                        break;
                    case subjBoardTitle:
                        Intent subjIntent = new Intent(homeView.getContext(), SubjBoardActivity.class);
                        startActivity(subjIntent);
                        break;
                }
            }
        });

        return homeView;
    }

}