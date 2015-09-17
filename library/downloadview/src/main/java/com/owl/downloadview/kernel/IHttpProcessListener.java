
package com.owl.downloadview.kernel;

import com.owl.downloadview.data.Task;

public interface IHttpProcessListener {

    void onStart(Task t);

    void onUpdateTotalSize(Task t, long size);

    void onUpdateProcess(Task t, long process);

    void onFinish(Task t);

    void onStop(Task t);

    void onError(Task t, int error);

}
