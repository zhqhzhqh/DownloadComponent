package com.owl.downloadview.manager;

public class ThreadPoolFactory {
    
    public static IThreadPool get() {
        return ImplIThreadPool.getSingleInstance();
    }

}
