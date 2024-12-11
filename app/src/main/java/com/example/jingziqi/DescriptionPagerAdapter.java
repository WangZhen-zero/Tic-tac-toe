package com.example.jingziqi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// 翻页适配器
public class DescriptionPagerAdapter extends RecyclerView.Adapter<DescriptionPagerAdapter.PageViewHolder> {

    private final List<String> pages; // 分页内容

    public DescriptionPagerAdapter(List<String> pages) {
        this.pages = pages;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_item_description, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        holder.textView.setText(pages.get(position));  // 设置每页内容
    }

    @Override
    public int getItemCount() {
        return pages.size(); // 页数
    }

    static class PageViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public PageViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.page_text);
        }
    }
}
