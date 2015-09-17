
package com.owl.downloadview.manager;


import com.owl.downloadview.data.Task;

public interface IManager {

    void attach(Task task, IDownloadMessageListener messageListener);

    void deAttach(Task task);

    /**
     * @return 0 成功， -1 出错, -2 应用未启动
     */
    int doOperationRequest(Task task, OperationRequest opr);

}
