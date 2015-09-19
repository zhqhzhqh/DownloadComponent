# DownloadComponent
一个类似于WebView的下载组件，封装了下载的功能。

## 使用说明：

### 集成

<!--xml代码-->
		// xml
        <com.owl.downloadview.view.RectangelDownloadView
            android:id="@+id/download_test_01"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_below="@+id/title" />

### 控制

<pre class="brush: java; gutter: true;">
     	dr01 = (IDownloadService) findViewById(R.id.download_test_01);
        AttachedInfoMessage info01 = new AttachedInfoMessage();
        info01.setFileName("360weishi.apk");
        info01.setIconurl(null);
        info01.setTitle("360手机卫士");
        dr01.start("http://filelx.liqucn.com/upload/2015/anquan/360MobileSafe.ptada", info01);
</pre>




## 设计文档：
