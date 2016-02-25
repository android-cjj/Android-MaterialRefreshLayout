[![Android Gems](http://www.android-gems.com/badge/android-cjj/Android-MaterialRefreshLayout.svg?branch=master)](http://www.android-gems.com/lib/android-cjj/Android-MaterialRefreshLayout) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MaterialRefreshLayout-brightgreen.svg?style=flat)](http://www.android-arsenal.com/details/1/2492)
[中文版文档](https://github.com/android-cjj/Android-MaterialRefreshLayout/blob/master/README-cn.md)
--------------------------------------------

MaterialRefreshLayout v1.3.0
==================================
This is a drop-down refresh control, it is more beautiful and powerful than SwipeRefreshLayout.It is easy to use and support API LEVEL >= 11 . I hope you like it !
![](http://www.apkbus.com/data/attachment/forum/201509/11/095859kp297mjmj2php5pm.jpg)

Now let me talk about MaterialRefreshLayout of function
---------------------------------------------------------------------------
add a lovely sun

![](https://github.com/android-cjj/Android-MaterialRefreshLayout/blob/master/gif%2Fcjj6.gif)

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

(6)MaterialRefreshLayout Can pull load more, drop-down refresh automatically, pull up refresh automatically.

(7)There are a lot of functions, you can see the source code...

Usage
=================================================

Add dependency.

```
dependencies {
    compile 'com.cjj.materialrefeshlayout:library:1.3.0'
}
```

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

       @Override
       public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
       	  //load more refreshing...
       }
  }

// refresh complete
materialRefreshLayout.finishRefresh();

// load more refresh complete
 materialRefreshLayout.finishRefreshLoadMore();
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

(6)add pull up loading more features...

![](http://www.apkbus.com/data/attachment/forum/201509/16/180340xypx76ypefpdzfrf.png)

```xml
<com.cjj.MaterialRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/refresh"
    app:isLoadMore="true"
    >

```
In java code.
```java
       materialRefreshLayout.setLoadMore(true);
```

(7) It can drop-down refresh automatically and pull up refresh automatically.

In java code.
```java
       materialRefreshLayout.autoRefresh();//drop-down refresh automatically
        materialRefreshLayout.autoRefreshLoadMore();// pull up refresh automatically
```


V 1.3.0
===========================
add drop-down refresh automatically and pull up refresh automatically

V 1.2.1
===========================
add pull up loading more features

V 1.2.0
===========================
add progress size type ,you can use xml attr to set app:progress_size_type="normal"(big) and fix some bugs

V 1.1.0
===========================
a new way to perform animations with support library, the library size and the amount of libraries decreased.


AT LAST
===================================================================
If the MaterialRefreshLayout has failed to meet to your requirements, you can go to the [BeautifulRefreshLayout](https://github.com/android-cjj/BeautifulRefreshLayout) to find what you need.

If you want to support me,you can follow me on GitHub:
[https://github.com/android-cjj](https://github.com/android-cjj).

[About me](http://android-cjj.github.io/)
------------------------------------

Thanks
============================================

[lsjwzh/MaterialLoadingProgressBar](https://github.com/lsjwzh/MaterialLoadingProgressBar)


License
=======

    The MIT License (MIT)

	Copyright (c) 2015 android-cjj

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.






