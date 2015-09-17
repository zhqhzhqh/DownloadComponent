
package com.owl.downloadview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.owl.downloadview.R;

public class CircleDownloadView extends ImplDownloadServiceView {

    private Context mContext;

    public CircleDownloadView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
        init();
    }

    public CircleDownloadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mContext = context;
        init();
    }

    public CircleDownloadView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        mContext = context;
        init();
    }

    protected void init() {
        // TODO Auto-generated method stub
        super.init();
        View v = LayoutInflater.from(mContext).inflate(R.layout.circle_download_view, this);
    }

}
