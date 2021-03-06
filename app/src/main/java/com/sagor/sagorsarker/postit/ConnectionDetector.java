package com.sagor.sagorsarker.postit;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Sagor Sarker on 17-Nov-17.
 */

public class ConnectionDetector {

    Context context;

    public ConnectionDetector(Context context) {
        this.context = context;
    }

    public boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(cm!=null){
            NetworkInfo info = cm.getActiveNetworkInfo();

            if(info!=null){
                if(info.getState() == NetworkInfo.State.CONNECTED)
                    return true;
            }
        }
        return false;
    }
}
