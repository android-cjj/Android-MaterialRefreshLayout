[![Android Gems](http://www.android-gems.com/badge/android-cjj/Android-MaterialRefreshLayout.svg?branch=master)](http://www.android-gems.com/lib/android-cjj/Android-MaterialRefreshLayout)

MaterialRefreshLayout v1.4.0
==================================
这是一个下拉刷新控件，它比SwipeRefreshLayout更漂亮和强大，使用也比较简单。支持android 3.0 以上。希望你喜欢，呵呵。


现在让我来说说他有多牛逼的功能吧，其实也就那样......gif图录制的很奇怪，建议真机查看，啊哈哈哈
---------------------------------------------------------------------------
(1)如果你喜欢官方的刷新效果，没问题，它也可以做到，这是一种侵入式的下拉刷新

![](https://github.com/android-cjj/Android-MaterialRefreshLayout/blob/master/gif%2Fcjj2.gif)

(2)然而，有些人并不喜欢侵入刷新，或者说，需求需要的是非侵入刷新，没问题，同时，觉得上面的效果太单调了，好的，我们可以加个波浪形状的背景，就像下图所示

![](https://github.com/android-cjj/Android-MaterialRefreshLayout/blob/master/gif%2Fcjj1.gif)

(3)如果这时候你想要侵入式的刷新，又要有波浪背景，也就是所有效果覆盖在内容之上的话，没问题，它还是可以做到的，呵呵......

![](https://github.com/android-cjj/Android-MaterialRefreshLayout/blob/master/gif%2Fcjj3.gif)

(4)我知道你需要加载更多，本人还是不建议两者一起，你可以用这个，两者去组合[RecyclerViewWithFooter](https://github.com/android-cjj/Android-RecyclerViewWithFooter)，当然，这里也写了。

![](https://github.com/android-cjj/Android-MaterialRefreshLayout/blob/master/gif%2Fcjj4.gif)

(5)自动下拉刷新也是很多人需要的，比如下图所示，当然也做了自动上拉刷新，具体看源码

![](https://github.com/android-cjj/Android-MaterialRefreshLayout/blob/master/gif%2Fcjj5.gif)


(6)看久了你如果讨厌的话，我还增加了一种样式，如下图：

![](https://github.com/android-cjj/Android-MaterialRefreshLayout/blob/master/gif%2Fcjj6.gif)

(7)如果需要覆盖的，也可以.......

![](https://github.com/android-cjj/Android-MaterialRefreshLayout/blob/master/gif%2Fcjj7.gif)


用法
=================================================

AS添加依赖库，这样就不用导入整个library库,Eclipse的用户导入library库,慢慢折腾吧！（因为我觉得如果不是环境不允许你用as的话,还是放弃eclipse吧，毕竟github上很多优秀的项目都是用as的，好吧我多嘴了...）

1.4版本的bug比较少，功能还行，你直接导入library，想改什么就改什么了，
```
dependencies {
    compile 'com.cjj.materialrefeshlayout:library:1.3.0'//不建议在as直接导入这个  bug 多  还是直接用library ,自己修改也方便
}
```

在你的layout xml.添加下面的代码
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
实例化它并设置监听，onrefresh是必须实现的，其他都是方法可选，为了代码可读性而设计......
```java
 materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id...);
 materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
      @Override
      public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
          //下拉刷新...
      }
      
       @Override
       public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
       	  //上拉刷新...
       }
  }
  
// 结束下拉刷新...
materialRefreshLayout.finishRefresh();

// 结束上拉刷新...
 materialRefreshLayout.finishRefreshLoadMore();
```

配置
=============
(1)如果你喜欢官方的刷新效果

![](http://www.apkbus.com/data/attachment/forum/201509/10/171338y8ufsxjrs7k2rxu3.jpg)

在xml中, 设置以下属性
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
在代码中是这样的：
```java
materialRefreshLayout.setIsOverLay(true);
materialRefreshLayout.setWaveShow(false);
```

(2)如果你喜欢非侵入刷新

![](http://www.apkbus.com/data/attachment/forum/201509/10/171336i3x75d7x4tadxezt.jpg)

在xml中, 设置以下属性
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
在代码中是这样的：
```java
materialRefreshLayout.setIsOverLay(false);
materialRefreshLayout.setWaveShow(false);
```
(3)如果你觉得上面的效果太单调了，加个波浪形状的背景，就像下图所示

![](http://www.apkbus.com/data/attachment/forum/201509/10/171339lvhk3n3h4wkgkgdc.jpg)

在xml中, 设置以下属性
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
在java代码中是这样的：
```java
materialRefreshLayout.setWaveColor(0xffffffff);
materialRefreshLayout.setIsOverLay(false);
materialRefreshLayout.setWaveShow(true);
```
(4)你想要侵入式的刷新，又要有波浪背景，也就是所有效果覆盖在内容之上

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
在java代码中是这样的：
```java
materialRefreshLayout.setWaveColor(0xf90fffff);
materialRefreshLayout.setIsOverLay(true);
materialRefreshLayout.setWaveShow(true);
```

(5)其他...

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

(6)上拉加载更多...

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
在java代码中是这样的：
```java
       materialRefreshLayout.setLoadMore(true);
```

(7)自动下拉刷新，自动上拉刷新.

在java代码中是这样的：
```java
       materialRefreshLayout.autoRefresh();//drop-down refresh automatically
        materialRefreshLayout.autoRefreshLoadMore();// pull up refresh automatically
```


V 1.3.0
===========================
添加自动下拉刷新，自动上拉刷新.

V 1.2.1
===========================
添加上拉加载更多...

V 1.2.0
===========================
添加了可设置加载圆圈的大小，有两种模式可以选择，在xml配置属性app:progress_size_type="normal"(big)和修复一些bug 

V 1.1.0
===========================
一个全新的动画展示效果，减小了库的大小，很不幸的从支持api 8 升到 api 11 不过，也没多少人用2.x.x的手机了......

V 1.0.0
===================================================================
支持api 8，第一版本，啊哈哈......

最后
===================================================================
如果这些都没能满足你的需求的话，没问题，你可以去看看[BeautifulRefreshLayout](https://github.com/android-cjj/BeautifulRefreshLayout)，我想，你可以找到一个专属于你的人...错了...是下拉刷新......

在GitHub follow 我就是对我最大的支持，呵呵:
[https://github.com/android-cjj](https://github.com/android-cjj).


[关于我](http://android-cjj.github.io/)
------------------------------------

感谢
============================================

[lsjwzh/MaterialLoadingProgressBar](https://github.com/lsjwzh/MaterialLoadingProgressBar)

随意说说
-----------------------------
刚建了个[github小伙伴交流群](http://t.cn/RyiVjnv)(477826523),有兴趣的朋友可以加进来的...小小要求：github有至少7个关注者或者开源库有7颗星星的...

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






