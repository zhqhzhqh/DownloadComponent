
package com.owl.downloadview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.owl.downloadview.R;

public class RectangelDownloadView extends ImplDownloadServiceView {

    public RectangelDownloadView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init();
    }

    public RectangelDownloadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        init();
    }

    public RectangelDownloadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        init();
    }

    protected void init() {
        // TODO Auto-generated method stub
        super.init();
        View v = LayoutInflater.from(context).inflate(R.layout.rectangle_download_view, this);
        attachViewToHolder(v);
    }

}
