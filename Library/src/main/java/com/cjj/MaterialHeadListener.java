package com.cjj;

/**
 * Created by cjj on 2015/9/7.
 */
public interface MaterialHeadListener {

    public void onComlete(MaterialRefreshLayout materialRefreshLayout);

    public void onBegin(MaterialRefreshLayout materialRefreshLayout);

    public void onPull(MaterialRefreshLayout materialRefreshLayout, float fraction);

    public void onRelease(MaterialRefreshLayout materialRefreshLayout, float fraction);

    public void onRefreshing(MaterialRefreshLayout materialRefreshLayout);
}
