package com.sarmadali.floatingwidget;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class FloatingWidgetService extends Service {


    private WindowManager mWindowManager;
    private View mFloatingWidgetView;
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    // Method to request the SYSTEM_ALERT_WINDOW permission
    private void requestOverlayPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    // Method to create the floating widget
    private void createFloatingWidget() {

        // Inflate the layout for the floating widget
        mFloatingWidgetView = LayoutInflater.from(this).inflate(R.layout.floating_widget_layout,
                null);

        // Initialize the button on the floating widget
        Button button = mFloatingWidgetView.findViewById(R.id.floating_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the button click event
                showTextDialog();
                // You can perform other actions here as needed
            }
        });

        // Initialize the WindowManager
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        // Set up WindowManager.LayoutParams for the floating widget
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        // Set the gravity of the floating widget (position on the screen)
        params.gravity = Gravity.BOTTOM | Gravity.END;

        // Add the floating widget to the WindowManager
        mWindowManager.addView(mFloatingWidgetView, params);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Check if the permission is granted (only for API level 23 and higher)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            // You don't have permission to draw overlays, so request it
            requestOverlayPermission();
        } else {
            // Permission is granted, proceed with creating the floating widget
            createFloatingWidget();
        }



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFloatingWidgetView != null && mWindowManager != null) {
            mWindowManager.removeView(mFloatingWidgetView);
        }
    }

    private void showTextDialog() {

    }
}