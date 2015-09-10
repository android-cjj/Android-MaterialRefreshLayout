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

Config
=============
(1)if you like SwipeRefreshLayout drop-down refresh effect

![](http://www.apkbus.com/data/attachment/forum/201509/10/171338y8ufsxjrs7k2rxu3.jpg)

In xml, use attributes.
```xml
<com.cjj.MaterialRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/refresh"
    app:overlay="true"
    app:wave_show="false"
    >
```
In java code.
```java
materialRefreshLayout.setIsOverLay(true);
materialRefreshLayout.setWaveShow(false);
```

(2)if you like non-invasive drop-down refresh effect

![](http://www.apkbus.com/data/attachment/forum/201509/10/171336i3x75d7x4tadxezt.jpg)

In xml, use attributes.
```xml
<com.cjj.MaterialRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/refresh"
    app:overlay="false"
    app:wave_show="false"
    >
```
In java code.
```java
materialRefreshLayout.setIsOverLay(false);
materialRefreshLayout.setWaveShow(false);
```
(3)if you like add a background of the wave shape.

![](http://www.apkbus.com/data/attachment/forum/201509/10/171339lvhk3n3h4wkgkgdc.jpg)

In xml, use attributes.
```xml
<com.cjj.MaterialRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/refresh"
    app:overlay="false"
    app:wave_show="true"
    app:wave_color="@color/material_green"
    app:wave_height_type="normal"  (higher)
    >
```
In java code.
```java
materialRefreshLayout.setWaveColor(0xffffffff);
materialRefreshLayout.setIsOverLay(false);
materialRefreshLayout.setWaveShow(true);
```
(4)If you want to make waveform covering on content

![](http://www.apkbus.com/data/attachment/forum/201509/10/171334xtd0xphsag4ww4gs.jpg)

```xml
<com.cjj.MaterialRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/refresh"
    app:overlay="true"
    app:wave_show="true"
    app:wave_color="#90ffffff"
    app:progress_colors="@array/material_colors"
    app:wave_height_type="higher"
    >
```
In java code.
```java
materialRefreshLayout.setWaveColor(0xf90fffff);
materialRefreshLayout.setIsOverLay(true);
materialRefreshLayout.setWaveShow(true);
```

(5)other...

![](http://www.apkbus.com/data/attachment/forum/201509/10/171341xrgamch93mad4pcg.jpg)

```xml
<com.cjj.MaterialRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/refresh"
    app:overlay="false"
    app:wave_show="false"
    app:progress_colors="@array/material_colors"
    app:wave_height_type="higher"
    app:progress_show_circle_backgroud="false"
    >
```

AT LAST
===================================================================
If the MaterialRefreshLayout has failed to meet to your requirements, you can go to the [BeautifulRefreshLayout](https://github.com/android-cjj/BeautifulRefreshLayout) to find what you need.

THTANKS
[JakeWharton/NineOldAndroids](https://github.com/JakeWharton/NineOldAndroids)
[lsjwzh/MaterialLoadingProgressBar](https://github.com/lsjwzh/MaterialLoadingProgressBar)









