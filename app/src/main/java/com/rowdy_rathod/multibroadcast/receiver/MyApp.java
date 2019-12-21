package com.rowdy_rathod.multibroadcast.receiver;

import android.app.Application;
import android.content.BroadcastReceiver;

import com.rowdy_rathod.multi_broadcastreciver.receiverHelper.DynamicReceiver;

public class MyApp extends Application {

    BroadcastReceiver broadcastReceiver;
    MyReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        receiver = new MyReceiver();
        broadcastReceiver = DynamicReceiver.with(receiver)
                .register(this);
    }
}
