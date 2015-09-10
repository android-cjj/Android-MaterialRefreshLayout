MaterialRefreshLayout
==================================
This is a drop-down refresh control, it is more beautiful and powerful than SwipeRefreshLayout.It is easy to use and support API LEVEL >= 8 . I hope you like it !

Now let me talk about MaterialRefreshLayout of function
---------------------------------------------------------------------------
(1)It can be done like SwipeRefreshLayout drop-down refresh effect,this is a kind of intrusive drop-down refresh.

![](http://www.apkbus.com/data/attachment/forum/201509/10/145037bwzigoghgrk414hw.gif)


(2)However, there are some people who are not as like invasive drop-down refresh, so, it also has a non-invasive drop-down refresh function.

![](http://www.apkbus.com/data/attachment/forum/201509/10/145142fp1z3fp0hkx0apg3.gif)


(3)If you feel too drab, we can add a background of the wave shape.

![](http://www.apkbus.com/data/attachment/forum/201509/10/144913t3beqg3eics1xwwr.gif)


(4)If you want to make waveform covering on content, it can be done.

![](http://www.apkbus.com/data/attachment/forum/201509/10/144736ah8xaeamz155zq54.gif)


(5)if you like the most simple effect,You can see the image below.

![](http://www.apkbus.com/data/attachment/forum/201509/10/145326ttfgttgm3gg68tgf.gif)

(6)There are a lot of functions, you can see the source code...

Usage
=================================================
Use it in your layout xml.
```xml
<com.cjj.MaterialRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/refresh"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    >
      <...ListView or GridView or RecyclerView or ScrollView and more...>

</com.cjj.MaterialRefreshLayout>
```
Get instance and use it.
```java
 materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id...);
 materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
      @Override
      public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
          //refreshing...
      }
  }
// refresh complete 
materialRefreshLayout.finishRefresh();
  
```

(1)if you like SwipeRefreshLayout drop-down refresh effect

![](http://www.apkbus.com/data/attachment/forum/201509/10/165357v69hm9gmrggt6zhr.png)




