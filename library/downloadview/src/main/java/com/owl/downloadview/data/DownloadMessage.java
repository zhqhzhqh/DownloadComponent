
package com.owl.downloadview.data;

public class DownloadMessage {

    //
    public enum Status {
        W, R, P, E, C
    }

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private long process;

    public long getProcess() {
        return process;
    }

    public void setProcess(long process) {
        this.process = process;
    }

    private long totalSize;

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

}
