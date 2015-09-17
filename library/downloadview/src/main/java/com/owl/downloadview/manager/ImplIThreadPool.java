
package com.owl.downloadview.manager;

import com.owl.downloadview.data.Task;
import com.owl.downloadview.kernel.HttpApiFactory;
import com.owl.downloadview.kernel.IHttpApi;
import com.owl.downloadview.kernel.IHttpProcessListener;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ImplIThreadPool implements IThreadPool {

    private static ExecutorService pool;

    private static ImplIThreadPool instance;

    private static List<TaskRunable> taskRunableSet;

    public static ImplIThreadPool getSingleInstance() {
        if (instance == null) {
            instance = new ImplIThreadPool();
            pool = Executors.newFixedThreadPool(3);
            taskRunableSet = new LinkedList<TaskRunable>();
        }
        return instance;
    }

    @Override
    public void add(Task task, IHttpProcessListener listener) {
        // TODO Auto-generated method stub
        if (task == null || listener == null) {
            return;
        }

        TaskRunable r = new TaskRunable();
        r.setTask(task);
        r.setListener(listener);
        this.taskRunableSet.add(r);

        pool.execute(r);

    }

    @Override
    public void stop(Task task) {
        // TODO Auto-generated method stub
        if (task != null) {
            // 识别任务并停止
            TaskRunable t = null;
            for (TaskRunable r : taskRunableSet) {
                if (r.getTask().equals(task)) {
                    r.stop();
                    t = r;
                }
            }
            if (t != null) {
                taskRunableSet.remove(t);
            }
        }
    }

    private class TaskRunable implements Runnable {

        private Task task;

        private IHttpProcessListener listener;

        public TaskRunable() {
            // TODO Auto-generated constructor stub
        }

        private IHttpApi httpApi;

        {
            httpApi = HttpApiFactory.get();
        }

        public void setTask(Task task) {
            this.task = task;
        }

        public Task getTask() {
            return this.task;
        }

        public void setListener(IHttpProcessListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            httpApi.start(task, listener);
        }

        public void stop() {
            httpApi.stop(task);
        }

    }

}
