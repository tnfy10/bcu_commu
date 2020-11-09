package com.runtimeterror.bcu_commu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup homeView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView redirectView = homeView.findViewById(R.id.redirectView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(homeView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        redirectView.setLayoutManager(layoutManager);
        SiteAdapter adapter = new SiteAdapter();

        adapter.addItem(new Site("사이트1"));
        adapter.addItem(new Site("사이트2"));
        adapter.addItem(new Site("사이트3"));
        adapter.addItem(new Site("사이트4"));
        adapter.addItem(new Site("사이트5"));
        adapter.addItem(new Site("사이트6"));
        adapter.addItem(new Site("사이트7"));
        adapter.addItem(new Site("사이트8"));

        redirectView.setAdapter(adapter);

        return homeView;
    }

}