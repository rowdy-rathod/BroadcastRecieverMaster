# BroadcastRecieverMaster

How to
To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.rowdy-rathod:BroadcastRecieverMaster:0.1'
	}




Manifeast.xml


        <?xml version="1.0" encoding="utf-8"?>
        <manifest xmlns:android="http://schemas.android.com/apk/res/android"
            package="com.rowdy_rathod.multibroadcast">

            <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

            <application
                android:name=".receiver.MyApp"
                android:allowBackup="true"
                android:icon="@mipmap/ic_launcher"
                android:label="@string/app_name"
                android:roundIcon="@mipmap/ic_launcher_round"
                android:supportsRtl="true"
                android:theme="@style/AppTheme">
                <activity android:name=".MainActivity">
                    <intent-filter>
                        <action android:name="android.intent.action.MAIN" />

                        <category android:name="android.intent.category.LAUNCHER" />
                    </intent-filter>
                </activity>
                <receiver
                    android:name=".receiver.MyReceiver"
                    android:enabled="true"
                    android:exported="true">

                </receiver>
                <activity android:name=".BaseActivity" />
            </application>

        </manifest>
        
        
  MyApp.java
  
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
        
 MyReceiver.java
 
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
        
        
BaseActivity.java

          package com.rowdy_rathod.multibroadcast;

          import android.content.ContentResolver;
          import android.content.Context;
          import android.content.Intent;
          import android.net.ConnectivityManager;
          import android.net.NetworkInfo;
          import android.os.Bundle;
          import android.provider.Settings;
          import android.util.Log;
          import android.widget.Toast;

          import androidx.annotation.Nullable;
          import androidx.appcompat.app.AppCompatActivity;

          import com.rowdy_rathod.multi_broadcastreciver.interfaces.GlobalReceiverCallBack;
          import com.rowdy_rathod.multi_broadcastreciver.receiverHelper.Session;

          import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
          // This is a can apply multiple broadcast receiver in single activity or base activity.
          // for that is import sdk
          public class BaseActivity extends AppCompatActivity implements GlobalReceiverCallBack {

              @Override
              protected void onCreate(@Nullable Bundle savedInstanceState) {
                  super.onCreate(savedInstanceState);
                  Session.setmGlobalReceiverCallback(this);
              }

              @Override
              public void onCallBackReceived(Context context, Intent intent) {
                  String action = intent.getAction();
                  try {
                      String actionOfIntent = intent.getAction();
                      boolean isConnected = isNetworkConnected(context);
                      assert actionOfIntent != null;
                      if (actionOfIntent.equals(CONNECTIVITY_ACTION)) {
                          if (isConnected) {
                              Toast.makeText(context, "Network Connected", Toast.LENGTH_SHORT).show();
                          } else {
                              Toast.makeText(context, "Network Disconnected", Toast.LENGTH_SHORT).show();
                          }
                      } else if (intent.getAction().matches("android.location.GPS_ENABLED_CHANGE")) {
                          boolean enabled = intent.getBooleanExtra("enabled", false);
                          if (enabled) {
                              Toast.makeText(context, "GPS on", Toast.LENGTH_SHORT).show();
                          } else {
                              Toast.makeText(context, "GPS Off", Toast.LENGTH_SHORT).show();
                          }
                      } else if (intent.getAction().matches("android.location.PROVIDERS_CHANGED")) {
                          boolean isGpsCheck = isGpsEnabled(context);
                          if (isGpsCheck) {
                              Toast.makeText(context, "GPS On", Toast.LENGTH_SHORT).show();
                          } else {
                              Toast.makeText(context, "GPS Off", Toast.LENGTH_SHORT).show();
                          }
                      } else if (intent.getAction().matches("android.provider.Telephony.SMS_RECEIVED")) {

                      }
                  } catch (Exception e) {
                      e.printStackTrace();
                  }
              }

              private boolean isGpsEnabled(Context context) {
                  ContentResolver contentResolver = getContentResolver();
                  // Find out what the settings say about which providers are enabled
                  //  String locationMode = "Settings.Secure.LOCATION_MODE_OFF";
                  int mode = Settings.Secure.getInt(
                          contentResolver, Settings.Secure.LOCATION_MODE, Settings.Secure.LOCATION_MODE_OFF);
                  if (mode != Settings.Secure.LOCATION_MODE_OFF) {
                      return true;
                         /* if (mode == Settings.Secure.LOCATION_MODE_HIGH_ACCURACY) {
                              locationMode = "High accuracy. Uses GPS, Wi-Fi, and mobile networks to determine location";
                          } else if (mode == Settings.Secure.LOCATION_MODE_SENSORS_ONLY) {
                              locationMode = "Device only. Uses GPS to determine location";
                          } else if (mode == Settings.Secure.LOCATION_MODE_BATTERY_SAVING) {
                              locationMode = "Battery saving. Uses Wi-Fi and mobile networks to determine location";
                          }*/
                  } else {
                      return false;
                  }
              }

              public static boolean isNetworkConnected(Context context) {
                  ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                  assert manager != null;
                  NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
                  if (activeNetwork != null) {
                      return activeNetwork.isConnected();
                  }
                  return false;
              }


              @Override
              public void onPointerCaptureChanged(boolean hasCapture) {

              }
          }



