
package com.owl.downloadview.kernel;

public class HttpApiFactory {

    public static IHttpApi get() {
        return new HttpUrlConnectionAgent();
    }

}
