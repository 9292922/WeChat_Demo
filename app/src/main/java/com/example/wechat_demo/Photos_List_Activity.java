package com.example.wechat_demo;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

/*
 * 实现功能：
 * 没有，空窗口，只作显示
 * */
public class Photos_List_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photos_list);
        setWindows();//沉浸窗口
    }

    private void setWindows() {
        //-------------------------沉浸化-----------------------
        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
            //设置沉浸完毕


        }
    }

    public void back_MainActivty(View view) {
        Intent intent = new Intent(Photos_List_Activity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            finish();//结束activity
        }
        return super.onKeyDown(keyCode, event);
    }
}

