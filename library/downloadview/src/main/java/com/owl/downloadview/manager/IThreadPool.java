
package com.owl.downloadview.manager;


import com.owl.downloadview.data.Task;
import com.owl.downloadview.kernel.IHttpProcessListener;

public interface IThreadPool {

    void add(Task task, IHttpProcessListener listener);

    void stop(Task task);

}
