
package com.owl.downloadview.manager;

import com.owl.downloadview.data.DownloadMessage;
import com.owl.downloadview.data.DownloadMessage.Status;
import com.owl.downloadview.data.Task;
import com.owl.downloadview.kernel.IHttpProcessListener;
import com.owl.downloadview.utils.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;


public class ImplManager implements IManager {

    private TaskQueueSet taskSet;

    private Map<Task, HttpProcessListener> linker;

    private final int MAX_TASK_COUNT = 3;

    private IThreadPool threadPool;

    private DownloadMessage message;

    public ImplManager() {
        // TODO Auto-generated constructor stub
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        taskSet = new TaskQueueSet();
        linker = new HashMap<Task, HttpProcessListener>();
    }

    // 接口部分
    @Override
    public void attach(Task task, IDownloadMessageListener messageListener) {
        // TODO Auto-generated method stub
        if (task == null || messageListener == null)
            return;

        if (taskSet != null) {
            HttpProcessListener l = new HttpProcessListener(messageListener);
            linker.put(task, l);
            if (taskSet.r.size() < MAX_TASK_COUNT) {
                // 开启下载
                taskSet.getR().add(task);
                threadPool = ThreadPoolFactory.get();
                threadPool.add(task, l);
            } else {
                // 添加多等待队列
                taskSet.getW().add(task);
            }
        }
    }

    private class HttpProcessListener implements IHttpProcessListener {

        private long totalSize;

        private long process;

        private IDownloadMessageListener messageListener;

        public HttpProcessListener(IDownloadMessageListener messageListener) {
            // TODO Auto-generated constructor stub
            message = new DownloadMessage();
            this.messageListener = messageListener;
        }

        @Override
        public void onStart(Task t) {
            // TODO Auto-generated method stub
            if (taskSet.getW().contains(t)) {
                taskSet.getR().add(t);
                taskSet.getW().remove(t);
            }

            //
            this.totalSize = 0;
            this.process = 0;
            message.setStatus(Status.R);
            update();
        }

        @Override
        public void onUpdateTotalSize(Task t, long size) {
            // TODO Auto-generated method stub
            this.totalSize = size;
            message.setStatus(Status.R);
            update();
        }

        @Override
        public void onUpdateProcess(Task t, long process) {
            // TODO Auto-generated method stub
            this.process = process;
            message.setStatus(Status.R);
            update();
        }

        @Override
        public void onFinish(Task t) {
            // TODO Auto-generated method stub
            if (taskSet.getR().contains(t)) {
                taskSet.getR().remove(t);
            }

            //
            message.setStatus(Status.C);
            update();

            // 调度新任务
            if (taskSet.getR().size() < MAX_TASK_COUNT) {
                Task w = taskSet.getW().poll();
                if (w != null) {
                    taskSet.getR().add(w);
                    threadPool = ThreadPoolFactory.get();
                    HttpProcessListener l = linker.get(w);
                    if (l != null) {
                        threadPool.add(w, l);
                    }
                }

            }
        }

        @Override
        public void onError(Task t, int error) {
            // TODO Auto-generated method stub
            if (taskSet.getR().contains(t)) {
                taskSet.getE().add(t);
                taskSet.getR().remove(t);
            }

            //
            message.setStatus(Status.E);
            update();
        }

        @Override
        public void onStop(Task t) {
            // TODO Auto-generated method stub
            message.setStatus(Status.P);
            update();
        }

        private void update() {
            message.setProcess(process);
            message.setTotalSize(totalSize);
            messageListener.onUpdate(message);
        }
    }

    @Override
    public void deAttach(Task task) {
        // TODO Auto-generated method stub

    }

    @Override
    public int doOperationRequest(Task task, OperationRequest opr) {
        // TODO Auto-generated method stub
        if (task == null || opr == null)
            return -1;

        switch (opr) {
            case click:

                // 如果未启动，先启动应用
                if (!taskSet.getR().contains(task) && !taskSet.getP().contains(task)
                        && !taskSet.getW().contains(task) && !taskSet.getE().contains(task)) {
                    return -2;
                }
                // r-p
                if (taskSet.getR().contains(task)) {
                    taskSet.getP().add(task);
                    taskSet.getR().remove(task);
                    message.setStatus(Status.P);
                    threadPool.stop(task);
                    return 0;
                }

                // p-w
                if (taskSet.getP().contains(task)) {
                    taskSet.getW().add(task);
                    taskSet.getP().remove(task);
                    message.setStatus(Status.W);
                    threadPool.add(task, linker.get(task));
                    return 0;
                }

                // e-w
                if (taskSet.getE().contains(task)) {
                    taskSet.getW().add(task);
                    taskSet.getE().remove(task);
                    message.setStatus(Status.W);
                    threadPool.add(task, linker.get(task));
                }
                break;
            case delete:
                File taskFile = FileUtils.getTaskFile(task);
                if (taskFile.exists()) {
                    taskFile.delete();
                    return 0;
                }
                File taskTempFile = FileUtils.getTaskTempFile(task);
                if (taskTempFile.exists()) {
                    if (taskSet.getR().contains(task)) {
                        threadPool.stop(task);
                        taskSet.getR().remove(task);
                    } else if (taskSet.getW().contains(task)) {
                        threadPool.stop(task);
                        taskSet.getW().remove(task);
                    } else if (taskSet.getP().contains(task)) {
                        threadPool.stop(task);
                        taskSet.getP().remove(task);
                    } else if (taskSet.getE().contains(task)) {
                        threadPool.stop(task);
                        taskSet.getE().remove(task);
                    }
                    linker.remove(task);
                    taskTempFile.delete();
                    return 0;
                }
                break;

            default:
                break;
        }
        return 0;
    }

    private class TaskQueueSet {

        private Queue<Task> w;

        private Queue<Task> r;

        private Queue<Task> p;

        private Queue<Task> e;

        public TaskQueueSet() {
            // TODO Auto-generated constructor stub
            w = new LinkedList<Task>();
            r = new LinkedList<Task>();
            p = new LinkedList<Task>();
            e = new LinkedList<Task>();
        }

        public Queue<Task> getW() {
            return w;
        }

        public Queue<Task> getR() {
            return r;
        }

        public Queue<Task> getP() {
            return p;
        }

        public Queue<Task> getE() {
            return e;
        }
    }

}
