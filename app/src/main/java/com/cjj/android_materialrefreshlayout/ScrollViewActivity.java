package com.cjj.android_materialrefreshlayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

/**
 * Created by Administrator on 2015/9/10.
 */
public class ScrollViewActivity extends BaseActivity implements View.OnClickListener {
    private MaterialRefreshLayout materialRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);

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
                materialRefreshLayout.finishRefreshLoadMore();

            }

            @Override
            public void onfinish() {
                Toast.makeText(ScrollViewActivity.this, "finish", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.simple:
                startActivity(new Intent(this,SimpleActivity.class));
                break;

            case R.id.wave:
                startActivity(new Intent(this,WaveActivity.class));
                break;

            case R.id.rv:
                Intent intent = new Intent(this,LoadMoreActivity.class);
                startActivity(intent);
                break;
            case R.id.lv:
                Intent intent2 = new Intent(this,AutoRefreshActivity.class);
                startActivity(intent2);
                break;


            case R.id.sun:
                startActivity(new Intent(this,SunActivity.class));
                break;

            case R.id.overLay:
                startActivity(new Intent(this,OverLayActivity.class));
                break;

        }
    }
}
