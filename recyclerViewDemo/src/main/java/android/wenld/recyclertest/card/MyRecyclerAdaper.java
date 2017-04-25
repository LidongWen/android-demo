package android.wenld.recyclertest.card;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wenld.commontools.ScreenUtils;
import android.wenld.recyclertest.R;
import android.wenld.recyclertest.card.util.RecyclerScaleUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuantongqin on 2016/11/16.
 */

public class MyRecyclerAdaper extends RecyclerView.Adapter<MyRecyclerAdaper.MyViewHolder> {

    private final List<String> mList;
    private final Context mContext;

    public MyRecyclerAdaper(List<String> list, Context context){
        this.mContext = context;
        if(list == null){
            mList = new ArrayList<>();
        }else{
            this.mList = list;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_recyclerview_item,parent,false);
        //这里设置view的宽度
        RecyclerScaleUtils.onCreateViewHolder(parent,view, ScreenUtils.dip2px(30f));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //设置第一个与最后item对称显示
        RecyclerScaleUtils.onBindViewHolder(holder.itemView,position, getItemCount(), ScreenUtils.dip2px(30f));
        Glide.with(mContext).load(mList.get(position)).centerCrop().into(holder.mImageview);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageview;

        public MyViewHolder(View itemView) {
            super(itemView);
            mImageview = (ImageView) itemView.findViewById(R.id.image);

        }
    }



}
