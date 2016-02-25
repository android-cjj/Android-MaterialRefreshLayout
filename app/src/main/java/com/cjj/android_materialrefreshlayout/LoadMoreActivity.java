package com.cjj.android_materialrefreshlayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LoadMoreActivity extends BaseActivity {
    private MaterialRefreshLayout materialRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list);
        initsToolbar();

        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();

                    }
                }, 3000);


            }

            @Override
            public void onfinish() {
                Toast.makeText(LoadMoreActivity.this, "finish", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                Toast.makeText(LoadMoreActivity.this, "load more", Toast.LENGTH_LONG).show();

                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();

                    }
                }, 3000);
            }
        });


        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerview);
        setupRecyclerView(rv);
    }

    private void initsToolbar() {
        Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.style0:
                    materialRefreshLayout.setWaveColor(0xff8BC34A);
                    materialRefreshLayout.setIsOverLay(false);
                    materialRefreshLayout.setWaveShow(true);
                    materialRefreshLayout.setShowProgressBg(true);
                    materialRefreshLayout.setProgressColors(getResources().getIntArray(R.array.material_colors));
                    materialRefreshLayout.setShowArrow(true);
                    break;
                case R.id.style1:

                    break;
//                case R.id.style2:
//                    materialRefreshLayout.setWaveColor(0x90ffffff);
//                    materialRefreshLayout.setIsOverLay(true);
//                    materialRefreshLayout.setWaveShow(true);
//                    materialRefreshLayout.setShowProgressBg(true);
//                    materialRefreshLayout.setProgressColors(getResources().getIntArray(R.array.material_colors));
//                    materialRefreshLayout.setShowArrow(true);
//                    break;
//                case R.id.style3:
//                    materialRefreshLayout.setWaveColor(0xff8BC34A);
//                    materialRefreshLayout.setIsOverLay(false);
//                    materialRefreshLayout.setWaveShow(true);
//                    materialRefreshLayout.setShowProgressBg(true);
//                    materialRefreshLayout.setProgressColors(getResources().getIntArray(R.array.material_colors));
//                    materialRefreshLayout.setShowArrow(true);
//                    break;
            }


            return true;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(LoadMoreActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {


        public static class ViewHolder extends RecyclerView.ViewHolder {

            public final ImageView mImageView;

            public ViewHolder(View view) {
                super(view);
                mImageView = (ImageView) view.findViewById(R.id.avatar);
            }


        }

        public SimpleStringRecyclerViewAdapter(Context context) {
            super();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            if (position == 0) {
                holder.mImageView.setImageResource(R.drawable.a6);
            } else if (position == 1) {
                holder.mImageView.setImageResource(R.drawable.a5);
            }

        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
