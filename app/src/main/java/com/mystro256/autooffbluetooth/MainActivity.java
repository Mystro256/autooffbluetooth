package com.mystro256.autooffbluetooth;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static final int DISABLE_BATTERY_OPTIMIZATION = 0;

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        // Call back if user agrees to disabling battery optimization
        if (requestCode == DISABLE_BATTERY_OPTIMIZATION) {
            if (resultCode == RESULT_OK) {
                hideBatteryOptimization();
            }
        }
    }

    // By default, a battery optimization warning amd button is shown; this
    // function hides the warning/button and shows the welcome text instead
    private void hideBatteryOptimization() {
        TextView WelcomeText = findViewById(R.id.WelcomeText);
        TextView batteryOptimizationText = findViewById(R.id.batteryOptimizationText);
        Button batteryOptimizationButton = findViewById(R.id.batteryOptimizationButton);

        WelcomeText.setVisibility(View.VISIBLE);
        batteryOptimizationText.setVisibility(View.GONE);
        batteryOptimizationButton.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide battery optimization warning if battery optimization is
        // already disabled or user is running something older than android M
        PowerManager powMan = (PowerManager) getSystemService(POWER_SERVICE);
        String packageName = getPackageName();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                powMan.isIgnoringBatteryOptimizations(packageName)) {
            hideBatteryOptimization();
        }
    }

    public void disableBatteryOptimization(View v) {
        Intent intent = new Intent();
        String packageName = getPackageName();

        // Request disabling battery optimization and request a call back
        intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + packageName));
        startActivityForResult(intent, DISABLE_BATTERY_OPTIMIZATION);
    }
}
