package com.example.wechat_demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

/*
 * 实现功能:
 * 发布朋友圈并返回result给MainActivity
 * 监听editText
 * QQ空间imageView点击切换
 * */
public class Activity_announce extends AppCompatActivity {
    int[] imgs = {R.drawable.icon_zoom_0, R.drawable.icon_zoom_1};
    int currentImg = 0;
    String resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setWindows();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.announce);

        final Button button = (Button) findViewById(R.id.button_announce);//获取发布按钮

        //实现多行输入
        EditText editText = (EditText) findViewById(R.id.editText);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        editText.setSingleLine(false);
        editText.setHorizontallyScrolling(false);
        //设置监听editText内容改变事件
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().equals("")) {
                    button.setEnabled(false);
                } else {
                    button.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    //设置窗口沉浸
    private void setWindows() {
        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
        //设置沉浸完毕
    }


    //返回MainActivity
    public void back_MainActivity(View view) {
        Intent intent = new Intent(Activity_announce.this, MainActivity.class);
        startActivity(intent);
    }


    //单击QQ空间图标
    public void zoom(View view) {
        ((ImageView) view).setImageResource(imgs[++currentImg % imgs.length]);
    }

    public void announce_btn(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        Intent intent = getIntent().putExtra("result", editText.getText().toString());//获取上一个intent并传入信息
        setResult(0, intent);//传递intent
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {

            finish();
        }
        return super.onKeyDown(keyCode, event);

    }
}
