package com.yjs.complexmenu;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjingsong on 17/2/20.
 */

public class ComplexSortMenu extends LinearLayout {
    public ComplexSortMenu(Context context) {
        this(context, null);
    }

    public ComplexSortMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ComplexSortMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private OnSelectedListener onSelectedListener;

    private void init() {
        //setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //LayoutInflater.from(getContext()).inflate(R.layout.sort_view, this);
        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.sort_view, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        //
        List<String> strings = new ArrayList<>();
        strings.add("北京市");
        strings.add("东城区");
        strings.add("西城区");
        strings.add("海淀区");
        strings.add("朝阳区");
        SortAdapter sortAdapter = new SortAdapter(strings);
        recyclerView.setAdapter(sortAdapter);
        addView(recyclerView);


    }

    private class SortAdapter extends RecyclerView.Adapter {

        List<String> data;

        public SortAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent,false);

            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof ItemViewHolder) {
                ((ItemViewHolder) holder).textView.setText(data.get(position));
                holder.itemView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onSelectedListener!=null){
                            onSelectedListener.onSelected(data.get(position));
                        }

                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return data.size();
        }


        class ItemViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            public ItemViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.textView);
            }
        }
    }

    public interface OnSelectedListener {
        void onSelected(String name);
    }

    public OnSelectedListener getOnSelectedListener() {
        return onSelectedListener;
    }

    public void setOnSelectedListener(OnSelectedListener onSelectedListener) {
        this.onSelectedListener = onSelectedListener;
    }
}
