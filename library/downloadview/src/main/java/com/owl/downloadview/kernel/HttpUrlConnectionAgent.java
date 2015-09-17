
package com.owl.downloadview.kernel;

import com.owl.downloadview.data.Task;
import com.owl.downloadview.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUrlConnectionAgent implements IHttpApi {

    private HttpURLConnection conn;

    private Task t;

    private IHttpProcessListener listener;

    private File file;

    private FileOutputStream fileOutputSteam;

    private boolean canStop;

    @Override
    public int start(Task task, IHttpProcessListener listener) {
        // TODO Auto-generated method stub
        if (task == null || listener == null) {
            this.listener.onError(this.t, -1);
            return -1;
        }

        file = FileUtils.getTaskFile(task);
        if (file.exists()) {
            return -2;
        }
        
        String filePath = file.getPath();
        
        file = FileUtils.getTaskTempFile(task);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        this.t = task;
        this.listener = listener;
        fileOutputSteam = null;
        try {
            URL u = new URL(task.geturl());
            conn = (HttpURLConnection) u.openConnection();
            conn.setReadTimeout(6 * 1000);
            conn.setConnectTimeout(6 * 1000);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Accept-Encoding", "identity"); 
            long tempFileSize = file.length();
            if (tempFileSize > 0) {
                conn.setRequestProperty("Range", "bytes=" + tempFileSize + "-");
            }

            long totalSize = conn.getContentLength();
            if (totalSize <= 0) {
                this.listener.onError(this.t, -1);
                return -1;
            }
            this.listener.onStart(this.t);
            // 更新文件总大小
            if (totalSize > 0) {
                this.listener.onUpdateTotalSize(this.t, totalSize + tempFileSize);
            }
            // 更新进度
            long processSize = tempFileSize;
            int responseCode = conn.getResponseCode();
            if (responseCode == 200 || responseCode == 206) {
                InputStream inputSteam = conn.getInputStream();
                fileOutputSteam = new FileOutputStream(file, true);
                final int GAP_SIZE = 4 * 1024;
                byte[] buffer = new byte[GAP_SIZE];
                int n = 0;
                while ((n = inputSteam.read(buffer)) != -1) {
                    processSize += n;
                    this.listener.onUpdateProcess(this.t, processSize);
                    fileOutputSteam.write(buffer, 0, n);
                    if (canStop) {
                        stopTask();
                        canStop = false;
                        this.listener.onStop(this.t);
                        return -1;
                    }
                }
                // 重命名文件
                file.renameTo(new File(filePath));
                this.listener.onFinish(this.t);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            this.listener.onError(this.t, -1);
            return -1;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
            if (fileOutputSteam != null)
                try {
                    fileOutputSteam.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return -1;
                }
        }

        return 0;
    }

    @Override
    public int stop(Task task) {
        // TODO Auto-generated method stub
        canStop = true;
        return 0;
    }

    private int stopTask() {
        // TODO Auto-generated method stub
        try {
            conn.disconnect();
            conn = null;
            fileOutputSteam.close();
            fileOutputSteam = null;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

}
