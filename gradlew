package com.example.android.bluetoothlegatt_touchMe.com.main_menu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bluetoothlegatt_touchMe.Common.CommonData;
import com.example.android.bluetoothlegatt_touchMe.R;
import com.example.android.bluetoothlegatt_touchMe.com.BluetoothLeService;
import com.example.android.bluetoothlegatt_touchMe.com.DeviceControlActivity;
import com.example.android.bluetoothlegatt_touchMe.com.SampleGattAttributes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.android.bluetoothlegatt_touchMe.Common.CommonData.CMD_PLAY_NODE_DO;
import static com.example.android.bluetoothlegatt_touchMe.Common.CommonData.CMD_PLAY_NODE_MI;
import static com.example.android.bluetoothlegatt_touchMe.Common.CommonData.CMD_PLAY_NODE_PA;
import static com.example.android.bluetoothlegatt_touchMe.Common.CommonData.CMD_PLAY_NODE_RA;
import static com.example.android.bluetoothlegatt_touchMe.Common.CommonData.CMD_PLAY_NODE_RE;
import static com.example.android.bluetoothlegatt_touchMe.Common.CommonData.CMD_PLAY_NODE_SI;
import static com.example.android.bluetoothlegatt_touchMe.Common.CommonData.CMD_PLAY_NODE_SO;
import static com.example.android.bluetoothlegatt_touchMe.Common.CommonData.TOUCH_GTO_TEST1;
import static com.example.android.bluetoothlegatt_touchMe.Common.CommonData.TOUCH_GTO_TEST2;
import static com.example.android.bluetoothlegatt_touchMe.com.DeviceControlActivity.action;
import static com.example.android.bluetoothlegatt_touchMe.com.DeviceControlActivity.mGattCharacteristics;
import static com.example.android.bluetoothlegatt_touchMe.com.DeviceControlActivity.packet;

import static android.bluetooth.BluetoothProfile.STATE_CONNECTED;
import static com.example.android.bluetoothlegatt_touchMe.com.BluetoothLeService.EXTRA_DATA;
import static com.example.android.bluetoothlegatt_touchMe.com.BluetoothLeService.JDY_RX_MEASUREMENT;
import static com.example.android.bluetoothlegatt_touchMe.com.BluetoothLeService.JDY_TX_MEASUREMENT;
import static com.example.android.bluetoothlegatt_touchMe.com.BluetoothLeService.RX_CHAR_UUID;
import static com.example.android.bluetoothlegatt_touchMe.com.BluetoothLeService.RX_SERVICE_UUID;
import static com.example.android.bluetoothlegatt_touchMe.com.DeviceControlActivity.run_btn;
import static com.example.android.bluetoothlegatt_touchMe.com.main_menu.NodeScanningActivity.node_count;
import static com.example.android.bluetoothlegatt_touchMe.com.main_menu.NodeScanningActivity.scan_node_count;
import static com.example.android.bluetoothlegatt_touchMe.com.main_menu.SetupActivity.act_flag;
import static com.example.android.bluetoothlegatt_touchMe.com.main_menu.SetupActivity.auto_flag;
import static com.example.android.bluetoothlegatt_touchMe.com.main_menu.SetupActivity.color_flag;
import static com.example.android.bluetoothlegatt_touchMe.com.main_menu.SetupActivity.set_color_flag;
import static com.example.android.bluetoothlegatt_touchMe.com.main_menu.SetupActivity.timer_setting;
import static com.example.android.bluetoothlegatt_touchMe.com.main_menu.SetupActivity.voice_flag;
import static com.example.android.bluetoothlegatt_touchMe.com.main_menu.SetupActivity.cds_flag;

/**
 * Created by GTO on 2020-01-22.
 */

public class RunActivity extends Activity {
    private final static String TAG = "DCA";

    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";

    //TODO: BLE variable
    private TextView mConnectionState;
    private SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm:ss");
    public static final int DUMP = -1;

    public String mDeviceName;
    public String mDeviceAddress;
    public static BluetoothLeService mBluetoothLeService;

    public static ImageView node_imageView01, node_imageView02, node_imageView03, node_imageView04, node_imageView05, node_imageView06;

    /*public ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics =
            new ArrayList<ArrayList<BluetoothGattCharacteristic>>();*/
    private boolean mConnected = false;
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private final String LIST_NAME = "NAME";
    private final String LIST_UUID = "UUID";

    int standardSize_X, standardSize_Y;
    float density;