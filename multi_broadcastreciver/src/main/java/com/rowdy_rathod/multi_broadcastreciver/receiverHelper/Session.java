package com.rowdy_rathod.multi_broadcastreciver.receiverHelper;

import android.content.Context;
import android.content.Intent;

import com.rowdy_rathod.multi_broadcastreciver.interfaces.GlobalReceiverCallBack;


public class Session {
    public static GlobalReceiverCallBack mGlobalReceiverCallback;

    public static void setmGlobalReceiverCallback(GlobalReceiverCallBack listener) {
        if (listener != null) {
            mGlobalReceiverCallback = listener;
        }
    }

    public static void getGlobalReceiverCallBack(Context context, Intent intent) {
        if (mGlobalReceiverCallback != null) {
            mGlobalReceiverCallback.onCallBackReceived(context, intent);
        }
    }
}
