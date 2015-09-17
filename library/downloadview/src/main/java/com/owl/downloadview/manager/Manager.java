
package com.owl.downloadview.manager;

import com.owl.downloadview.data.Task;

public class Manager extends ImplManager {

    private static Manager instance;


    private Manager() {
        // TODO Auto-generated constructor stub
    }

    public static Manager getSingleInstance() {
        if (instance == null) {
            instance = new Manager();
        }
        return instance;
    }

    @Override
    public void attach(Task task, IDownloadMessageListener messageListener) {
        // TODO Auto-generated method stub
        super.attach(task, messageListener);
    }

    @Override
    public void deAttach(Task task) {
        // TODO Auto-generated method stub
        super.deAttach(task);
    }

    @Override
    public int doOperationRequest(Task task, OperationRequest opr) {
        // TODO Auto-generated method stub
        return super.doOperationRequest(task, opr);
    }

}
