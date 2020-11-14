package com.runtimeterror.bcu_commu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
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
        SiteAdapter siteAdapter = new SiteAdapter();

        siteAdapter.addItem(new Site("사이트1"));
        siteAdapter.addItem(new Site("사이트2"));
        siteAdapter.addItem(new Site("사이트3"));
        siteAdapter.addItem(new Site("사이트4"));
        siteAdapter.addItem(new Site("사이트5"));
        siteAdapter.addItem(new Site("사이트6"));
        siteAdapter.addItem(new Site("사이트7"));
        siteAdapter.addItem(new Site("사이트8"));

        redirectView.setAdapter(siteAdapter);

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
                        Intent freeIntent = new Intent(homeView.getContext(), Freeboard.class);
                        startActivity(freeIntent);
                        break;
                    case meetBoardTitle:
                        Intent meetIntent = new Intent(homeView.getContext(), Meetboard.class);
                        startActivity(meetIntent);
                        break;
                    case subjBoardTitle:
                        Intent subjIntent = new Intent(homeView.getContext(), Subjboard.class);
                        startActivity(subjIntent);
                        break;
                }
            }
        });

        return homeView;
    }

}