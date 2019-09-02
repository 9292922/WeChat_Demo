package com.example.wechat_demo;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import java.util.ArrayList;
import java.util.Random;
import static android.os.Build.VERSION_CODES.LOLLIPOP;


/*
 * MainActivity:
 * Create by: 子君。
 * QQ:2314815201
 * Date:2019.8.31
 *>>>>>> MainActivity承载的功能<<<<<<<<
 * 监听适配器的点击/长按事件
 * 发布朋友圈
 * 下拉刷新(采用MaterialRefreshLayout)
 * 显示三种不同的item
 * 点赞功能(因为还没有学自定义view和绘图，实现一个小按钮与自绘弹出.9图片折叠菜单对本人难度太大，实践很多次还没有成功，故用ImageView+TextView当作点赞功能)
 * 更换相册封面UI实现
 * 长按弹出菜单
 * */


public class MainActivity extends AppCompatActivity {
    myRecyclerViewAdapter myRecyclerViewAdapter;
    ArrayList<Messages> arrayList = new ArrayList<>();

    @RequiresApi(api = LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTheme(R.style.Theme_AppCompat_DayNight_NoActionBar);
        //-------------以下是代码实现---------------------

        setWindows();//设置窗口沉浸

        //创建新的假数据源arraryList集合
        arrayList = addData(arrayList);

        //初始化列表RecyclerLayout
        createList();

        setMaterialRefreshLayout();//设置下拉刷新

    }

    private void setMaterialRefreshLayout() {
        MaterialRefreshLayout materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }
        });

    }


    //设置返回activity事件
    @Override
    protected void onResume() {
        super.onResume();
        myRecyclerViewAdapter.add();//通知适配器刷新数据
    }

    private void showPopupMenu(final View view, final int tpye) {

        // 获取布局文件
        PopupMenu popupMenu = null;
        switch (tpye) {
            case 0:
                // 这里的view代表popupMenu需要依附的view
                popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_context, popupMenu.getMenu());
                break;
            case 1:
                // 这里的view代表popupMenu需要依附的view
                popupMenu = new PopupMenu(MainActivity.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_member_control, popupMenu.getMenu());
                break;
        }

        //---震动------
        Vibrator vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
        vibrator.vibrate(10);

        popupMenu.show();//展示菜单

        //设置监听菜单关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                switch (tpye) {
                    case 0://文本context
                        ((TextView) view).setBackgroundColor(getResources().getColor(R.color.color_textView_background_whirt, null));//更换textview背景配色=透明
                        break;
                    case 1:
                        break;
                }
            }
        });

    }


    private void createList() {//初始化列表RecyclerLayout

        //找到recyclerView并设置layoutManager
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        //设置并配置好linearManager
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);//设置布局管理器

        //创建适配器
        myRecyclerViewAdapter = new myRecyclerViewAdapter(this, arrayList, R.layout.item_text, R.layout.head);

        //绑定适配器提供的监听器并实现事件
        myRecyclerViewAdapter.setmOnclicklistener(new myRecyclerViewAdapter.onclicklistener() {
            @Override
            public void onItemClick(View view, int position, int type, View view2) {
                Messages messages = arrayList.get(position);

                switch (type) {
                    case 0://0是点赞图片  imageView_agree

                        if (messages.isAgree) {//判断该item对应实例点赞状态
                            //取消点赞
                            messages.setAgree(false);
                            arrayList.set(position, messages);//重新修改arrayList
                            ((ImageView) view).setImageResource(R.drawable.icon_like_0);
                            ((TextView) view2).setText("赞");
                        } else {
                            //点赞
                            messages.setAgree(true);
                            arrayList.set(position, messages);//重新修改arrayList
                            ((ImageView) view).setImageResource(R.drawable.icon_like_1);
                            ((TextView) view2).setText("已赞");
                        }
                        break;

                    case 1://1是点赞TextView_agree
                        //   Toast.makeText(MainActivity.this, "textView被单击！", Toast.LENGTH_LONG).show();
                        if (messages.isAgree) {//判断该item对应实例点赞状态
                            //取消点赞
                            ((ImageView) view2).setImageResource(R.drawable.icon_like_0);
                            ((TextView) view).setText("赞");
                            messages.setAgree(false);
                        } else {
                            //点赞
                            ((ImageView) view2).setImageResource(R.drawable.icon_like_1);
                            ((TextView) view).setText("已赞");
                            messages.setAgree(true);
                        }
                        break;

                    case 2:
                        Intent intent = new Intent(MainActivity.this, Activit_info.class);
                        startActivity(intent);
                        break;
                    case 3:
                        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constrainLayout_forground);
                        constraintLayout.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        Intent intent1 = new Intent(MainActivity.this, Activit_info.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Message", messages);
                        intent1.putExtras(bundle);
                        startActivity(intent1);
                        break;
                    default:
                        break;
                }

            }


            //-----------------处理长按事件---------------------------------
            @Override
            public void onItemLongClick(View view, int position, int type) {
                switch (type) {
                    case 0://这是文本context
                        ((TextView) view).setBackgroundColor(getResources().getColor(R.color.color_textView_background, null));
                        showPopupMenu(view, 0);
                        break;
                    case 1:
                        showPopupMenu(view, 1);
                    default:
                        break;
                }
            }
        });


        myRecyclerViewAdapter.setHasStableIds(true);
        recyclerView.setAdapter(myRecyclerViewAdapter);
        //测试滚动事件
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int poistion;
//                //获取两个constrainLayout
                ConstraintLayout constraintLayout1;
                constraintLayout1 = (ConstraintLayout) findViewById(R.id.constrainLayout_table_1);
                ConstraintLayout constraintLayout2;
                constraintLayout2 = (ConstraintLayout) findViewById(R.id.constraintLayout_table_2);
                ImageView imageView_back = (ImageView) findViewById(R.id.imageView_back);
                ImageView imageView_camera = (ImageView) findViewById(R.id.imageView_photo);
                poistion = linearLayoutManager.findFirstVisibleItemPosition();
                if (poistion == 0) {
                    constraintLayout2.setVisibility(View.VISIBLE);
                    constraintLayout1.setVisibility(View.INVISIBLE);
                    imageView_back.setVisibility(View.VISIBLE);
                    imageView_camera.setVisibility(View.VISIBLE);
                } else {
                    constraintLayout1.setVisibility(View.VISIBLE);
                    constraintLayout2.setVisibility(View.INVISIBLE);
                    imageView_back.setVisibility(View.INVISIBLE);
                    imageView_camera.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


    private void setWindows() {//设置窗口沉浸
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


    private ArrayList<Messages> addData(ArrayList<Messages> arrayList) {//添加数据
        for (int num = 0; num < 12; num++) {
            //创建message实例
            Messages messages = new Messages();

            //随机添加数据
            switch (new Random().nextInt(3)) {
                case 0:
                    messages.setName("子君");
                    messages.setPlace("广东·茂名");
                    messages.setContext("一起来体验吧，推出了朋友圈例子\uD83D\uDE01\uD83D\uDE01");
                    messages.setTime("16分钟前");
                    messages.setTypeText("微信客户端");
                    messages.setAgree(false);//设置点赞为假
                    messages.setType(0);//设置item类型
                    messages.setIcon(R.drawable.icon);
                    break;
                case 1:
                    messages.setName("许嵩");
                    messages.setPlace("安徽·安徽医科大学");
                    messages.setContext("放一张近照，最近排期:10月份在香港红馆开演唱会哦！");
                    messages.setTime("1天前");
                    messages.setTypeText("微博共享");
                    messages.setAgree(false);//设置点赞为假
                    messages.setType(1);//设置item类型
                    messages.setIcon(R.drawable.icon_xusong);
                    break;
                case 2:
                    messages.setName("胡歌");
                    messages.setPlace("桂林·漓江");
                    messages.setContext("终于杀青了，出来旅游放松一下自己！来桂林漓江找我玩呗。");
                    messages.setTime("59分钟前");
                    messages.setType(2);//设置item类型
                    messages.setTypeText("携程旅游");
                    messages.setAgree(false);//设置点赞为假
                    messages.setIcon(R.drawable.icon_huge);
                    break;
                default:
                    messages.setName("子君");
                    messages.setPlace("广东·茂名");
                    messages.setContext("一起来体验吧，推出了朋友圈例子\uD83D\uDE01\uD83D\uDE01");
                    messages.setTime("16分钟前");
                    messages.setType(0);//设置item类型
                    messages.setTypeText("微信客户端");
                    messages.setAgree(true);
                    messages.setIcon(R.drawable.icon);
                    break;
            }
            //添加message实例到Arrays集合
            arrayList.add(messages);

        }
        return arrayList;
    }


    public void forbackground(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    public void forbackground1(View view) {
        ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constrainLayout_forground);
        constraintLayout.setVisibility(View.INVISIBLE);//关闭相册按钮与面板
        Intent intent = new Intent(MainActivity.this, Photos_List_Activity.class);
        startActivity(intent);
    }

    public void announce(View view) {
        Intent intent = new Intent(this, Activity_announce.class);
        startActivityForResult(intent, 0);

    }

    //获取announce发布的信息
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == 0) {
            if (data != null) {
                String resultText;
                resultText = data.getStringExtra("result");
//                Toast.makeText(MainActivity.this, resultText, Toast.LENGTH_LONG).show();
                Messages messages = new Messages();
                messages.setName("子君");
                messages.setPlace("广东·茂名");
                messages.setContext(resultText);
                messages.setTime("1分钟前");
                messages.setTypeText("微信客户端");
                messages.setAgree(false);//设置点赞为假
                messages.setType(0);//设置item类型
                messages.setIcon(R.drawable.icon);
                arrayList.add(0, messages);
                myRecyclerViewAdapter.add();//因为内容单一，此方法没有在适配器实现具体内容，直接在MainActiviy实现添加data，适配器只负责更新ui
            }
        }
    }

    public void back_startActivity(View view) {

        Intent intent = new Intent(MainActivity.this, StartActivity.class);
        Bundle bundle = new Bundle();
        Messages messages = new Messages();
        messages.setIcon(R.drawable.icon_xusong);
        messages.setName("子君");
        bundle.putSerializable("Message", messages);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            finish();//结束activity
        }
        return super.onKeyDown(keyCode, event);
    }
}


