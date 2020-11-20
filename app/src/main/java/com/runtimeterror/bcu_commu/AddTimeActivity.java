package com.runtimeterror.bcu_commu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddTimeActivity extends AppCompatActivity {
    ImageView prevBtn;
    Button completeBtn;
    Button monday;
    Button tuesday;
    Button wednesday;
    Button thursday;
    Button friday;

    TextView normalTitle;

    EditText edtClass;
    EditText edtProfessor;
    EditText edtPlace;

    LinearLayout daySelect;
    LinearLayout startSelect;
    LinearLayout endSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);
        normalTitle.findViewById(R.id.normalTitle);
        normalTitle.setText("수업 추가");
        normalTitle.setVisibility(View.VISIBLE);

        prevBtn.findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        completeBtn.findViewById(R.id.completeBtn);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timeData = new Intent();

                setResult(RESULT_OK, timeData);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}