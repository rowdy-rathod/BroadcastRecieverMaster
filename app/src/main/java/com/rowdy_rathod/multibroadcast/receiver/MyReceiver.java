package com.rowdy_rathod.multibroadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.rowdy_rathod.multi_broadcastreciver.annotations.BroadcastReceiverActions;
import com.rowdy_rathod.multi_broadcastreciver.receiverHelper.Session;


@BroadcastReceiverActions({
        "android.provider.Telephony.SMS_RECEIVED",
        "android.location.GPS_ENABLED_CHANGE",
        "android.location.PROVIDERS_CHANGED",
        "android.net.conn.CONNECTIVITY_CHANGE"})
public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Session.getGlobalReceiverCallBack(context, intent);
    }
}