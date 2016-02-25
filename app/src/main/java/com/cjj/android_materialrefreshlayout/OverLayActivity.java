package com.cjj.android_materialrefreshlayout;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

/**
 * Created by cjj on 2016/2/24.
 */
public class OverLayActivity extends BaseActivity {

    private MaterialRefreshLayout materialRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        String[] array = new String[50];
        for (int i = 0; i < array.length; i++) {
            array[i] = "啊哈哈哈哈哈 " + i;
        }

        final ListView listView = (ListView) findViewById(R.id.lv);
        listView.setAdapter(new android.widget.ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array));
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setSunStyle(true);
        materialRefreshLayout.setIsOverLay(true);
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
                Toast.makeText(OverLayActivity.this, "finish", Toast.LENGTH_LONG).show();
            }


        });

    }
}
