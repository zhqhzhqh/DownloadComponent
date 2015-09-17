
package com.owl.downloadview.view;

import android.view.View;

public interface IDownloadStatusChangedListener {

    void onWaiting(View v);

    void onPausing(View v);

    void onRunning(View v);

    void onError(View v);

    void onCompleted(View v);

    void onDeleted(View v);
}
