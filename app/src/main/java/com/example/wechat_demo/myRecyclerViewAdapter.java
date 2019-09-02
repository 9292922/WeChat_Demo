package com.example.wechat_demo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

/*
 * 实现功能:
 * 是recyclerView的自定义适配器
 * 刷新数据
 * 添加数据
 * 返回三种不同的item
 *  */
public class myRecyclerViewAdapter extends RecyclerView.Adapter<myRecyclerViewAdapter.viewHolder_common> {
    private int resoureId;
    private Context context;
    private int headViewId;
    private ArrayList<Messages> arrayList;
    private onclicklistener mOnclicklistener; //定义单个item的监听器
    private final int VIEW_PIC_1 = 2;
    private final int COMMON_VIEW_ID = 0;
    private final int HEAD_VIEW_ID = 1;
    private final int VIEW_PIC_3 = 3;
    private myRecyclerViewAdapter holder;
    private int position;


    @Override
    public int getItemViewType(int position) {

        //---------------------执行判断--------------------

        if (position == 0) {        //需要判断是否为头部
            //是头部，返回头部id
            return HEAD_VIEW_ID;
        } else {
            //返回普通ViewId
            int viewId = 0;//初始化viewId
            Messages messages = arrayList.get(position - 1);//获取message实例
            switch (messages.getType()) {//判断message实例的item类型
                case 0:
                    viewId = COMMON_VIEW_ID;//普通viewId
                    break;
                case 1:
                    viewId = VIEW_PIC_1;//单图viewId
                    break;
                case 2:
                    viewId = VIEW_PIC_3;//3图viewId
                    break;
                default:
                    viewId = COMMON_VIEW_ID;//普通viewId
                    break;
            }
            return viewId;
        }

    }

    //定义构造方法用于接受参数和数据
    public myRecyclerViewAdapter(Context context, ArrayList<Messages> arrayList, int resourceId, int headViewId) {
        //接受到参数
        this.context = context;
        this.headViewId = headViewId;
        this.resoureId = resourceId;
        this.arrayList = arrayList;
        //接受参数完成
    }


    public interface onclicklistener {//定义个监听点击事件的接口interface

        //定义两个抽象方法
        void onItemClick(View view, int position, int type, View textView_agree);//单击事件

        void onItemLongClick(View view, int position, int type);//长按事件
    }


    //定义个方法，设置监听器，接受适配器绑定成功传进来的监听器
    public void setmOnclicklistener(onclicklistener onclicklistener) {
        this.mOnclicklistener = onclicklistener;//接受监听器
    }

    //添加项目
    public void add() {
        //通知adapter数据已经更改，请更新ui
        notifyDataSetChanged();
        // getItemCount();
    }

    @NonNull
    @Override//创建viewHplder成功回调事件
    public myRecyclerViewAdapter.viewHolder_common onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case HEAD_VIEW_ID:
                myRecyclerViewAdapter.viewHolder_common viewHolder_common1 = new myRecyclerViewAdapter.viewHolder_common(LayoutInflater.from(this.context).inflate(this.headViewId, parent, false));
                viewHolder_common1.setType(viewType);
                return viewHolder_common1;//返回一个头部viewHolder给adapter

            case COMMON_VIEW_ID:
                myRecyclerViewAdapter.viewHolder_common viewHolder_common2 = new myRecyclerViewAdapter.viewHolder_common(LayoutInflater.from(this.context).inflate(this.resoureId, parent, false));
                viewHolder_common2.setType(viewType);
                return viewHolder_common2;//返回一个普通viewHolder给adapter
            case VIEW_PIC_1:
                myRecyclerViewAdapter.viewHolder_common viewHolder_common3 = new myRecyclerViewAdapter.viewHolder_common(LayoutInflater.from(this.context).inflate(R.layout.item_pic_1, parent, false));
                viewHolder_common3.setType(viewType);
                return viewHolder_common3;//返回一个单图viewHolder给adapter
            case VIEW_PIC_3:
                myRecyclerViewAdapter.viewHolder_common viewHolder_common4 = new myRecyclerViewAdapter.viewHolder_common(LayoutInflater.from(this.context).inflate(R.layout.item_pic_3, parent, false));
                viewHolder_common4.setType(viewType);
                return viewHolder_common4;//返回一个多图viewHolder给adapter
            default:
                myRecyclerViewAdapter.viewHolder_common viewHolder_common5 = new myRecyclerViewAdapter.viewHolder_common(LayoutInflater.from(this.context).inflate(this.resoureId, parent, false));
                viewHolder_common5.setType(viewType);
                return viewHolder_common5;//返回一个普通viewHolder给adapter
        }


    }

    @Override//适配器绑定view回调事件
    public void onBindViewHolder(@NonNull final myRecyclerViewAdapter.viewHolder_common holder, final int position) {

        if (position == 0) {
            //是头部view
            //...


            //----------------头部view点击事件---------------------------
            holder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnclicklistener.onItemClick(view, 0, 3, null);//3代表的是点赞TextView
                }
            });


            //-----------------设置头部icon点击事件----------------------
            holder.imageView_icon2.setOnClickListener(new View.OnClickListener() {//监听头部icon点击事件
                @Override
                public void onClick(View view) {
                    mOnclicklistener.onItemClick(view, 0, 2, null);//2代表的是头部icon
                }
            });

        } else {//非头部view，进行绑定事件
            try {
                Messages messages = arrayList.get(position - 1);  //获取对应的message实例
                holder.textView_type.setText(messages.getTypeText());
                holder.textView_time.setText(messages.getTime());
                holder.textView_context.setText(messages.getContext());
                holder.imageView_icon.setImageResource(messages.getIcon());
                holder.textView_place.setText(messages.getPlace());
                holder.textView_name.setText(messages.getName());

                if (messages.getAgree()) {
                    //点赞状态=true
                    holder.textView_agree.setText("已赞");
                    holder.imageView_agree.setImageResource(R.drawable.icon_like_1);
                } else {//点赞状态=false
                    holder.textView_agree.setText("赞");
                    holder.imageView_agree.setImageResource(R.drawable.icon_like_0);
                }


                //----------监听点赞ImageView和TextView事件------------------
                holder.imageView_agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnclicklistener.onItemClick(view, position - 1, 0, holder.textView_agree);//0代表的是点赞ImageView
                    }
                });

                //---------------点赞textview绑定点击事件--------------------
                holder.textView_agree.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnclicklistener.onItemClick(view, position - 1, 1, holder.imageView_agree);//1代表的是点赞TextView
                    }
                });


                //------------------绑定头像点击事件-------------------------
                holder.imageView_icon2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnclicklistener.onItemClick(view, position - 1, 5, null);//调用适配器的监听器实现单击事件
                    }
                });


                //------------------绑定文本长按事件-------------------------
                holder.textView_context.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mOnclicklistener.onItemLongClick(view, position - 1, 0);//调用适配器的监听器实现长按事件
                        return false;
                    }
                });


                //----------------------绑定图像长按事件-----------------------

                holder.imageView_icon.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mOnclicklistener.onItemLongClick(view, position - 1, 1);//调用适配器的监听器实现长按事件
                        return false;
                    }
                });
                //----------------处理所有事件完毕----------------------------


            } catch (Exception e) {
                Toast.makeText(this.context, "捕获到异常:" + e.getMessage(), Toast.LENGTH_LONG).show();//提示异常信息
                Log.println(Log.WARN, "异常", e.getMessage());
            }


        }
    }


    @Override
    public int getItemCount() {
        return arrayList.size() + 1;
    }//多了一个头部view


    //定义普通view的viewholder
    class viewHolder_common extends RecyclerView.ViewHolder {
        //定义一组item里的组件
        TextView textView_name;
        TextView textView_place;
        ImageView imageView_agree;
        TextView textView_agree;
        TextView textView_time;
        TextView textView_type;
        TextView textView_context;
        ImageView imageView_icon;
        ImageView img;
        ImageView imageView_icon2;//这是头部的icon
        int type;

        public void setType(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }

        public viewHolder_common(@NonNull View itemView) {//构造方法
            super(itemView);
            //获取控件
            textView_name = (TextView) itemView.findViewById(R.id.textView_name);
            textView_agree = (TextView) itemView.findViewById(R.id.textView_agree);
            textView_place = (TextView) itemView.findViewById(R.id.TextView_place);
            imageView_agree = (ImageView) itemView.findViewById(R.id.imageView_agree);
            imageView_icon = (ImageView) itemView.findViewById(R.id.imageView_icon2_info);
            textView_context = (TextView) itemView.findViewById(R.id.textView_context);
            textView_time = (TextView) itemView.findViewById(R.id.textView_time);
            textView_type = (TextView) itemView.findViewById(R.id.textView_type);
            imageView_icon2 = (ImageView) itemView.findViewById(R.id.imageView_icon2_info);
            img = (ImageView) itemView.findViewById(R.id.img);


        }
    }
}


