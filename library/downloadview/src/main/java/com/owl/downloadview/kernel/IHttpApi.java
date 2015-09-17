
package com.owl.downloadview.kernel;

import com.owl.downloadview.data.Task;

public interface IHttpApi {

    int start(Task task, IHttpProcessListener listener);

    int stop(Task task);

}
