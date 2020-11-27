package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MeetBoardActivity extends AppCompatActivity {
    final String title = "모임게시판";
    TextView txtTitle;
    ImageView prevBtn;
    FloatingActionButton meetWriteBtn;

    ArrayList<String[]> postArray = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetboard);
        getPost("meet");

        txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        prevBtn = findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        meetWriteBtn = findViewById(R.id.meetWriteBtn);
        meetWriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent writeIntent = new Intent(getApplicationContext(), WirtePostActivity.class);
                writeIntent.putExtra("meetBoard", true);
                finish();
                startActivity(writeIntent);
            }
        });

        RecyclerView postList = findViewById(R.id.meetPostList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        postList.setLayoutManager(layoutManager);
        final PostAdapter postAdapter = new PostAdapter();

        Collections.reverse(postArray);
        for(int i=0; i<postArray.size(); i++){
            String[] freeBoard = postArray.get(i);
            postAdapter.addItem(new Post(freeBoard[2], freeBoard[3], freeBoard[0], freeBoard[1]));
        }

        postList.setAdapter(postAdapter);

        postAdapter.setOnItemClickListener(new OnPostItemClickListener() {
            @Override
            public void onItemClick(PostAdapter.ViewHolder holder, View view, int position) {
                Post item = postAdapter.getItem(position);
                Intent detailIntent = new Intent(getApplicationContext(), PostDetailActivity.class);
                detailIntent.putExtra("writer", item.getWriter());
                detailIntent.putExtra("title", item.getTitle());
                detailIntent.putExtra("content", item.getContent());
                detailIntent.putExtra("writeTime", item.getTime());
                startActivity(detailIntent);
            }
        });
    }

    public void getPost(String board){
        try {
            String result;
            String user_id;
            String post_time;
            String post_name;
            String post_content;

            GetBoard task = new GetBoard();
            result = task.execute(board).get();
            Log.d("board", result);

            JSONObject json = null;
            json = new JSONObject(result);

            JSONArray jarray = json.getJSONArray("board");
            for(int i=0; i<jarray.length(); i++){
                HashMap map = new HashMap<>();
                String[] arrayPost = new String[4];
                JSONObject jObject = jarray.getJSONObject(i);

                user_id = jObject.optString("user_id");
                post_time = jObject.optString("post_time");
                post_name = jObject.optString("post_name");
                post_content = jObject.optString("post_content");

                arrayPost[0] = user_id;
                arrayPost[1] = post_time;
                arrayPost[2] = post_name;
                arrayPost[3] = post_content;

                postArray.add(arrayPost);
            }
        } catch (JSONException je) {
            je.printStackTrace();
            Log.d("json", "json 에러");
        } catch (Exception e){
            e.printStackTrace();
            Log.i("DBtest", ".....ERROR.....!");
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}