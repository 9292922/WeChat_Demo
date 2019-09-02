package com.example.wechat_demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/*
 * 实现功能：
 * 程序主进口
 * 实现菜单Menu以及DiaLog
 * */

public class StartActivity extends AppCompatActivity {
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);//设置ActionBar
        setContentView(R.layout.start_activity);
        setDialog();
    }

    private void setDialog() {
        builder = new AlertDialog.Builder(StartActivity.this);


        builder.setTitle("实现的模块");
        builder.setMessage("1.朋友圈RecyclerLayout三种显示模式\n2.长按文本和头像弹出popupMenu菜单\n3.实现点击头像进入个人信息页\n4.个人信息页更多按钮实现BottomDiaLog\n5.实现发布信息\n6.实现更换相册页面\n7.状态栏沉浸\n8.点赞功能");
        builder.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //点击事件
            }
        });

    }


    public void enter_MainActivity(View view) {
        //跳转到列表页面
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override//实现菜单Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void exit(View view) {
        finish();
    }//设置退出软件事件

    @Override//监听菜单点击事件
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
//                Toast.makeText(StartActivity.this, "created on August 31,2019", Toast.LENGTH_LONG).show();
                builder.create().show();//弹出对话框
                break;
            case R.id.exit:
                finish();
                break;
        }
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {

            finish();//结束activity
        }
        return super.onKeyDown(keyCode, event);

    }
}
