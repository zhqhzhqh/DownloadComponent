
package com.owl.downloadview.view;


import com.owl.downloadview.data.AttachedInfoMessage;

public interface IDownloadService {

    // 直接命令组件启动下载
    void start(String url);

    void start(String url, AttachedInfoMessage info);

    // 绑定信息，由组件控制启动下载
    void attach(String url);

    void attach(String url, AttachedInfoMessage info);

    // UI线程安全的
    void setDownloadStatusListener(IDownloadStatusChangedListener listener);

}
