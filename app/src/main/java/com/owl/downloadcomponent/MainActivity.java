
package com.owl.downloadcomponent;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.owl.downloadview.data.AttachedInfoMessage;
import com.owl.downloadview.view.IDownloadService;

public class MainActivity extends Activity {

    private IDownloadService dr01;

    private IDownloadService dr02;

    private IDownloadService dr03;

    private IDownloadService dr04;

    private IDownloadService dr05;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dr01 = (IDownloadService) findViewById(R.id.download_test_01);
        dr02 = (IDownloadService) findViewById(R.id.download_test_02);
        dr03 = (IDownloadService) findViewById(R.id.download_test_03);
        dr04 = (IDownloadService) findViewById(R.id.download_test_04);
        dr05 = (IDownloadService) findViewById(R.id.download_test_05);
    }

    public void startDownload(View view) {
        // ex1
        AttachedInfoMessage info01 = new AttachedInfoMessage();
        info01.setFileName("360weishi.apk");
        info01.setIconurl(null);
        info01.setTitle("360手机卫士");
        dr01.start("http://filelx.liqucn.com/upload/2015/anquan/360MobileSafe.ptada", info01);
        // ex2
        AttachedInfoMessage info02 = new AttachedInfoMessage();
        info02.setFileName("meituan.apk");
        info02.setIconurl(null);
        info02.setTitle("美团");
        dr02.start(
                "http://filelx.liqucn.com/upload/2011/gouwu/aimeituan-270-liqushichang1-signed-aligned-1.ptada",
                info02);
        // ex3
        AttachedInfoMessage info03 = new AttachedInfoMessage();
        info03.setFileName("didi_psnger_test4_93_v393_92.apk");
        info03.setIconurl(null);
        info03.setTitle("滴滴打车");
        dr03.start(
                "http://filelx.liqucn.com/upload/2014/jiaotong/didi_psnger_test4_93_v393_92.ptada",
                info03);
        // ex4
        AttachedInfoMessage info04 = new AttachedInfoMessage();
        info04.setFileName("qqbrowser_5.8.1.1490_22513.apk");
        info04.setIconurl(null);
        info04.setTitle("QQ浏览器");
        dr04.start("http://filelx.liqucn.com/upload/2014/wangluo/qqbrowser_5.8.1.1490_22513.ptada",
                info04);
        // ex5
        AttachedInfoMessage info05 = new AttachedInfoMessage();
        info05.setFileName("com.tencent.mm_6.2.0.52_liqucn.com.apk");
        info05.setIconurl(null);
        info05.setTitle("微信");
        dr05.start(
                "http://file.liqucn.com/upload/2011/liaotian/com.tencent.mm_6.2.0.52_liqucn.com.apk",
                info05);
    }

    public void startAttach(View view) {
        // ex1
        AttachedInfoMessage info01 = new AttachedInfoMessage();
        info01.setFileName("360weishi.apk");
        info01.setIconurl(null);
        info01.setTitle("360手机卫士");
        dr01.attach("http://filelx.liqucn.com/upload/2015/anquan/360MobileSafe.ptada", info01);
        // ex2
        AttachedInfoMessage info02 = new AttachedInfoMessage();
        info02.setFileName("meituan.apk");
        info02.setIconurl(null);
        info02.setTitle("美团");
        dr02.attach(
                "http://filelx.liqucn.com/upload/2011/gouwu/aimeituan-270-liqushichang1-signed-aligned-1.ptada",
                info02);
        // ex3
        AttachedInfoMessage info03 = new AttachedInfoMessage();
        info03.setFileName("didi_psnger_test4_93_v393_92.apk");
        info03.setIconurl(null);
        info03.setTitle("滴滴打车");
        dr03.attach(
                "http://filelx.liqucn.com/upload/2014/jiaotong/didi_psnger_test4_93_v393_92.ptada",
                info03);
        // ex4
        AttachedInfoMessage info04 = new AttachedInfoMessage();
        info04.setFileName("qqbrowser_5.8.1.1490_22513.apk");
        info04.setIconurl(null);
        info04.setTitle("QQ浏览器");
        dr04.attach(
                "http://filelx.liqucn.com/upload/2014/wangluo/qqbrowser_5.8.1.1490_22513.ptada",
                info04);
        // ex5
        AttachedInfoMessage info05 = new AttachedInfoMessage();
        info05.setFileName("com.tencent.mm_6.2.0.52_liqucn.com.apk");
        info05.setIconurl(null);
        info05.setTitle("微信");
        dr05.attach(
                "http://file.liqucn.com/upload/2011/liaotian/com.tencent.mm_6.2.0.52_liqucn.com.apk",
                info05);
    }

}
