
package com.owl.downloadview.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.owl.downloadview.data.AttachedInfoMessage;
import com.owl.downloadview.data.DownloadMessage;
import com.owl.downloadview.data.Task;
import com.owl.downloadview.manager.IDownloadMessageListener;
import com.owl.downloadview.manager.Manager;
import com.owl.downloadview.manager.OperationRequest;
import com.owl.downloadview.utils.UrlUtils;

import com.owl.downloadview.R;


public class ImplDownloadServiceView extends RelativeLayout implements IDownloadService {

    protected Context context;

    private Task task;

    protected ViewHolder baseView;

    private IDownloadMessageListener callbak;

    private IDownloadStatusChangedListener downloadStatusListener;

    private UpdateHandler h;

    private class Msg {
        public static final int R = 101;

        public static final int W = 102;

        public static final int C = 103;

        public static final int E = 104;

        public static final int P = 105;
    }

    public ImplDownloadServiceView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public ImplDownloadServiceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    public ImplDownloadServiceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        this.context = context;
    }

    protected void init() {
        // TODO Auto-generated method stub
        baseView = new ViewHolder();
        callbak = new DownloadMessageListener();
        h = new UpdateHandler();
    }

    protected void attachViewToHolder(View v) {
        baseView.setIcon((ImageView) v.findViewById(R.id.icon));
        baseView.setTitle((TextView) v.findViewById(R.id.title));
        baseView.setProgress((ProgressBar) findViewById(R.id.process));
        baseView.setStatus((TextView) findViewById(R.id.status));
        baseView.setOpButton((Button) v.findViewById(R.id.op_button));
        baseView.getOpButton().setOnClickListener(new DownloadOpButtonClickListener());
        baseView.setDeleteButton((Button) v.findViewById(R.id.delete_button));
        baseView.getDeleteButton().setOnClickListener(new DownloadDeleteButtonClickListener());
    }

    @Override
    public void start(String url) {
        // TODO Auto-generated method stub
        if (task == null) {
            this.attach(url);
        }
        Manager.getSingleInstance().attach(task, callbak);
    }

    @Override
    public void start(String url, AttachedInfoMessage info) {
        // TODO Auto-generated method stub
        if (task == null) {
            this.attach(url, info);
        }
        Manager.getSingleInstance().attach(task, callbak);
    }

    @Override
    public void attach(String url) {
        // TODO Auto-generated method stub
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url == null");
        }
        // 检查URL
        if (!isURL(url))
            return;

        task = new Task();
        task.seturl(url);
        AttachedInfoMessage info = new AttachedInfoMessage();
        info.setFileName(UrlUtils.parseRealFileName(url));
        info.setIconurl(null);
        info.setTitle("测试用例");
        task.setInfo(info);
    }

    @Override
    public void attach(String url, AttachedInfoMessage info) {
        // TODO Auto-generated method stub
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("url == null");
        }
        if (info == null) {
            throw new IllegalArgumentException("info == null");
        }
        // 检查URL
        if (!isURL(url))
            return;

        baseView.getTitle().setText(info.getTitle());
        task = new Task();
        task.seturl(url);
        task.setInfo(info);
    }

    private boolean isURL(String url) {
        if (TextUtils.isEmpty(url))
            return false;
        else
            return true;
    }

    @Override
    public void setDownloadStatusListener(IDownloadStatusChangedListener listener) {
        // TODO Auto-generated method stub
        this.downloadStatusListener = listener;

    }

    private class DownloadMessageListener implements IDownloadMessageListener {

        @Override
        public void onUpdate(DownloadMessage message) {
            // TODO Auto-generated method stub
            if (message == null)
                return;

            Message m = h.obtainMessage();
            Bundle b = new Bundle();
            b.putLong("process", message.getProcess());
            b.putLong("totalsize", message.getTotalSize());
            m.setData(b);
            switch (message.getStatus()) {
                case R:
                    m.what = Msg.R;
                    break;
                case W:
                    m.what = Msg.W;
                    break;
                case C:
                    m.what = Msg.C;
                    break;
                case E:
                    m.what = Msg.E;
                    break;
                case P:
                    m.what = Msg.P;
                    break;

                default:
                    break;
            }

            h.sendMessage(m);
        }
    }

    private class UpdateHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub

            long process = msg.getData().getLong("process");
            long totalSize = msg.getData().getLong("totalsize");
            int p = (totalSize == 0) ? 0 : ((int) (process * 100 / totalSize));
            switch (msg.what) {

                case Msg.R:
                    if (downloadStatusListener != null) {
                        downloadStatusListener.onRunning(ImplDownloadServiceView.this);
                    }

                    //
                    baseView.getProgress().setProgress(p);
                    baseView.getOpButton().setText("下载中");
                    baseView.getStatus().setText(process + " / " + totalSize);
                    break;
                case Msg.W:
                    if (downloadStatusListener != null) {
                        downloadStatusListener.onWaiting(ImplDownloadServiceView.this);
                    }

                    //
                    baseView.getOpButton().setText("等待中");
                    break;
                case Msg.C:
                    if (downloadStatusListener != null) {
                        downloadStatusListener.onCompleted(ImplDownloadServiceView.this);
                    }

                    //
                    baseView.getOpButton().setText("完成");
                    break;
                case Msg.E:
                    if (downloadStatusListener != null) {
                        downloadStatusListener.onError(ImplDownloadServiceView.this);
                    }

                    //
                    baseView.getOpButton().setText("错误");
                    break;
                case Msg.P:
                    if (downloadStatusListener != null) {
                        downloadStatusListener.onPausing(ImplDownloadServiceView.this);
                    }

                    //
                    baseView.getOpButton().setText("暂停中");
                    break;

                default:
                    break;
            }
        }
    }

    private class DownloadOpButtonClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int rz = Manager.getSingleInstance().doOperationRequest(task, OperationRequest.click);
            if (rz == -2) {
                if (task != null) {
                    Manager.getSingleInstance().attach(task, callbak);
                }
            }
        }
    }

    private class DownloadDeleteButtonClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            // UI处理
            baseView.getTitle().setText("测试用例");
            baseView.getProgress().setProgress(0);
            baseView.getOpButton().setText("已删除");
            baseView.getStatus().setText(0 + " / " + 0);
            Manager.getSingleInstance().doOperationRequest(task, OperationRequest.delete);
        }

    }
}
