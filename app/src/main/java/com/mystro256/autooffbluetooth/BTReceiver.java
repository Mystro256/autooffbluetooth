package com.mystro256.autooffbluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BTReceiver extends BroadcastReceiver {

    // "OnReceive" is an abstract function called when there's BT activity
    // or state change; we can use this to try to catch a disconnection. When
    // called, if no devices are connected then we can disable BT.
    // See AndroidManifest.xml for the intent triggers.
    @Override
    public void onReceive(Context context, Intent intent) {
        // Check if BT adapter is valid and hasn't already been disabled
        if (BluetoothAdapter.getDefaultAdapter() != null && BluetoothAdapter.getDefaultAdapter().isEnabled()) {
            int[] profiles = {BluetoothProfile.A2DP, BluetoothProfile.HEADSET, BluetoothProfile.HEALTH};
            for (int profileId : profiles) {
                // If any device is connected, return to avoid disabling BT
                if (BluetoothAdapter.getDefaultAdapter().getProfileConnectionState(profileId) ==
                        BluetoothProfile.STATE_CONNECTED) {
                        return;
                }
            }
            // This point should only be reacted if nothing is connected
            BluetoothAdapter.getDefaultAdapter().disable();
        }
    }

}
