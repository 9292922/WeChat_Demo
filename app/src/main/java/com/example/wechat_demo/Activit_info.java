package com.example.wechat_demo;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import static android.os.Build.VERSION_CODES.LOLLIPOP;

/*
 * 实现功能:
 * 显示用户信息
 * 显示更多操作底部对话框BottomMenu
 * */
public class Activit_info extends AppCompatActivity {
    Dialog bottomDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_view);
        setWindows();//沉浸窗口


        setBottomMenu();//初始化底部对话框
        //获取组件对象
        ImageView imageView = (ImageView) findViewById(R.id.imageView_icon2_info);
        TextView textView = (TextView) findViewById(R.id.textView_name_info);
        TextView textView_place = (TextView) findViewById(R.id.textView_place_two);
        //获取上一个Activity传来的intent并获取序列化对象
        Intent intent = getIntent();
        Messages messages;
        messages = (Messages) intent.getSerializableExtra("Message");
        if (messages == null) {
            Log.d("调试", "message是空对象");
        } else {
            //设置消息
            textView.setText(messages.getName());
            textView_place.setText("地区:  " + messages.getPlace());
            imageView.setImageResource(messages.getIcon());
        }

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 82) {
            bottomDialog.show();
            //如果是菜单键则马上弹出菜单
            // ImageView imageView = (ImageView) findViewById(R.id.imageView_more);
            // showPopupMenu(imageView,0);//0代表不需要震动，因为菜单键默认有震动
        }

        if (keyCode == 4) {
            finish();//结束activity
        }
        return super.onKeyDown(keyCode, event);
    }


    private void setBottomMenu() {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.bottom_list, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        // bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
    }

    public void back_main(View view) {//返回主窗口Activity
        Intent intent = new Intent(Activit_info.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    private void showPopupMenu(final View view, int type) {
        // 获取布局文件
        PopupMenu popupMenu = null;

        // 这里的view代表popupMenu需要依附的view
        popupMenu = new PopupMenu(Activit_info.this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_info, popupMenu.getMenu());

        if (type != 0) {
            //---震动------
            Vibrator vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
            vibrator.vibrate(5);
        }
        popupMenu.show();//展示菜单}}}

        //设置监听菜单关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
    }

    public void more_info(View view) {
//        showPopupMenu(view,1);//震动并弹出
        bottomDialog.show();
    }
}

