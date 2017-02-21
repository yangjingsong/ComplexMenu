package com.yjs.complexmenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ComplexMenuView complexMenuView;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        complexMenuView = (ComplexMenuView) findViewById(R.id.complexMenuView);
        View view = LayoutInflater.from(this).inflate(R.layout.title_view, null);
        View view1 = LayoutInflater.from(this).inflate(R.layout.title_view, null);
        View sortView = LayoutInflater.from(this).inflate(R.layout.sort_view,null);
        View sortView1 = LayoutInflater.from(this).inflate(R.layout.sort_view1,null);
        Log.d("sortView",sortView.getHeight()+"");
        sortView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"我是选择项一",Toast.LENGTH_SHORT).show();
                complexMenuView.hideMenus();
            }
        });
        sortView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"我是选择项二",Toast.LENGTH_SHORT).show();
                complexMenuView.hideMenus();
            }
        });
        ComplexTitleView complexTitleView = new ComplexTitleView(this);
        complexTitleView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        complexTitleView.setText("选项一");
        complexTitleView.setHeight(100);
        complexTitleView.setGravity(Gravity.CENTER);
        ComplexTitleView complexTitleView1 = new ComplexTitleView(this);
        complexTitleView1.setText("选项二");
        complexTitleView1.setGravity(Gravity.CENTER);
        complexTitleView1.setHeight(100);



        ComplexSortMenu complexSortMenu = new ComplexSortMenu(this);
        complexSortMenu.setOnSelectedListener(new ComplexSortMenu.OnSelectedListener() {
            @Override
            public void onSelected(String name) {
                textView.setText(name);
                complexMenuView.hideMenus();
            }
        });
        complexMenuView.addTitleView(complexTitleView,complexSortMenu)
                .addTitleView(complexTitleView1,sortView1)
                .build();

        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"TextView",Toast.LENGTH_SHORT).show();
            }
        });





    }


}
