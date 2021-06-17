package com.jxxx.rhtx.lanya;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jxxx.rhtx.utils.StringUtil;
import com.jxxx.rhtx.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Ble4_0Util implements BleUtil {

//    private String serviceUUid = "0000fff0-0000-1000-8000-00805f9b34fb";        // 服务uuid
//    private String readUUID = "0000fff1-0000-1000-8000-00805f9b34fb";            // 读数据uuid
//    private String writeUUID = "0000fff2-0000-1000-8000-00805f9b34fb";    // 写数据uuid

//    private String serviceUUid = "49535343-fe7d-4ae5-8fa9-9fafd205e455";        // 服务uuid
//    private String readUUID = "49535343-1e4d-4bd9-ba61-23c647249616";            // 读数据uuid
//    private String writeUUID = "49535343-8841-43f4-a8d4-ecbe34729bb3";    // 写数据uuid

    private List<String> serviceUUid;// 服务uuid
    private List<String> readUUID; // 读数据uuid
    private List<String> writeUUID; // 写数据uuid

    private String[] uuidStr = new String[3];

    private static final int PERMISSION_REQUEST_CODE = 0x114; // 系统权限管理页面的参数

    private Activity context;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattService gattServiceMain;
    private BluetoothGattCharacteristic mDevWriteCharacteristic; // 写服务
    private BluetoothGattCharacteristic mDevReadCharacteristic; // 读服务
    private BluetoothAdapter.LeScanCallback leScanCallback;
    private BluetoothDevice curConnectDev;
    int newStates = -1;
    private String blemac;


    public void setUuidStr(String[] uuidStr) {
        serviceUUid = new ArrayList<>();
        readUUID = new ArrayList<>();
        writeUUID = new ArrayList<>();

        serviceUUid.add(uuidStr[0]);
        readUUID.add(uuidStr[1]);
        writeUUID.add(uuidStr[2]);
    }

    public Ble4_0Util(Activity context) {
        this.context = context;
    }

    public BluetoothDevice getCurConnectDev() {
        return curConnectDev;
    }

    @Override
    public boolean init() {
        //先检查权限 anroid 6.0以上 需要动态获取 位置权限
        checkBluetoothPermission();
        //不支持 蓝牙
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            return false;
        }
        final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);

        mBluetoothAdapter = bluetoothManager.getAdapter();
        //不支持 蓝牙
        if (mBluetoothAdapter == null) {
            return false;
        }

        //没有打开蓝牙
        if (!mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.enable();
        }

        return true;
    }

    public BluetoothAdapter getBluetoothAdapter() {
        return mBluetoothAdapter;
    }

    public boolean isConnect() {
        return mBluetoothGatt != null;
    }

    public String getBlemac() {
        return blemac;
    }

    @Override
    public boolean connect(String blemac, final CallBack callback) {
        callback.StateChange(0, BluetoothGatt.STATE_CONNECTING);
        curConnectDev = mBluetoothAdapter.getRemoteDevice(blemac);
        this.blemac = blemac;
        if (curConnectDev == null) {
            Log.e("BLE", "蓝牙" + blemac + "未找到");
            return false;
        }
        //已连接 先断开连接
        if (null != mBluetoothGatt) {
            mBluetoothGatt.close();
            mBluetoothGatt = null;
        }
        //不能拿到 名称和 蓝牙mac的按假连接处理
        if (null == curConnectDev.getName() && null == curConnectDev.getAddress()) {
            return false;
        }
        //连接蓝牙
        mBluetoothGatt = curConnectDev.connectGatt(context, false, new BluetoothGattCallback() {
            @Override
            public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                super.onPhyUpdate(gatt, txPhy, rxPhy, status);
            }

            @Override
            public void onPhyRead(BluetoothGatt gatt, int txPhy, int rxPhy, int status) {
                super.onPhyRead(gatt, txPhy, rxPhy, status);
            }

            @Override
            public void onConnectionStateChange(final BluetoothGatt gatt, int status, int newState) {
                super.onConnectionStateChange(gatt, status, newState);
                Log.w("---》》》","onConnectionStateChange"+newState);
                newStates = newState;
                if (newState == BluetoothGatt.STATE_CONNECTED) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //设置接收数据长度，默认20
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mBluetoothGatt.requestMtu(512);
                    }
                }
                if (newState == BluetoothGatt.STATE_DISCONNECTED) {
                    if(StringUtil.isBlank("")){
                        callback.StateChange(status, newStates);
                    }else{
                        ToastUtil.showToast("蓝牙断开连接");
                    }
                    disconnect();
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                super.onServicesDiscovered(gatt, status);
                List<BluetoothGattService> serviceList = gatt.getServices();
                for (BluetoothGattService gattService : serviceList) {
                    if(!isCurrentUuid(gattService.getUuid().toString(),serviceUUid)){
                        continue;
                    }
                    gattServiceMain = gattService;
                    uuidStr[0] = gattServiceMain.getUuid().toString();
                    Log.w("---》》》gattServiceMain:",gattService.getUuid().toString());
                    List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                    for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                        Log.w("---》》》","gattCharacteristic1.getUuid().toString():"+ gattCharacteristic.getUuid().toString());
                        if (mDevReadCharacteristic == null && isCurrentUuid(gattCharacteristic.getUuid().toString(),readUUID)) {
                            mDevReadCharacteristic = gattCharacteristic;
                            uuidStr[1] = mDevReadCharacteristic.getUuid().toString();
                            Log.w("---》》》","mDevReadCharacteristic:"+ mDevReadCharacteristic.getUuid().toString());
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(500);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    mBluetoothGatt.readCharacteristic(mDevReadCharacteristic);
                                }
                            }).start();
                            mBluetoothGatt.setCharacteristicNotification(gattCharacteristic, true);

                            List<BluetoothGattDescriptor> descriptorlist = gattCharacteristic.getDescriptors();

                            lp:
                            for (BluetoothGattDescriptor descriptor : descriptorlist) {
                                if (descriptor.getUuid().toString().contains("")) {
                                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                    mBluetoothGatt.writeDescriptor(descriptor);
                                    break lp;
                                }
                            }
                        }

                        Log.w("---》》》","gattCharacteristic.getProperties():"+ gattCharacteristic.getProperties());
                        if (mDevWriteCharacteristic == null && isCurrentUuid(gattCharacteristic.getUuid().toString(),writeUUID)) {
                            mDevWriteCharacteristic = gattCharacteristic;
                            uuidStr[2] = mDevWriteCharacteristic.getUuid().toString();
                            Log.w("---》》》","mDevWriteCharacteristic:"+ mDevWriteCharacteristic.getUuid().toString());
                            Log.e("---》》》", "连接成功"+newStates);
                            callback.StateChange(status, newStates);
                        }
                    }
                }
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicRead(gatt, characteristic, status);
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                super.onCharacteristicWrite(gatt, characteristic, status);
            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
                super.onCharacteristicChanged(gatt, characteristic);
                // 处理解释反馈指令
                callback.ReadValue(characteristic.getValue());
            }


            @Override
            public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                super.onDescriptorRead(gatt, descriptor, status);
            }

            @Override
            public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                super.onDescriptorWrite(gatt, descriptor, status);
            }

            @Override
            public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
                super.onReliableWriteCompleted(gatt, status);
            }

            @Override
            public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                super.onReadRemoteRssi(gatt, rssi, status);
            }

            @Override
            public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
                super.onMtuChanged(gatt, mtu, status);
                if (BluetoothGatt.GATT_SUCCESS == status) {
                    Log.w("---》》》","onMtuChanged success MTU = " + mtu);
                    gatt.discoverServices();
                } else {
                    Log.w("---》》》","onMtuChanged fail ");
                }
            }
        });

        return true;
    }

    private boolean isCurrentUuid(String uuid,List<String> uuidList){
        for(int i=0;i<uuidList.size();i++){
            if (uuid.contains(uuidList.get(i))) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean disconnect() {
        Log.w("---》》》","mBluetoothGatt:"+mBluetoothGatt);
        stopScan();
        if (BluetoothLjUtils.ble4Util!=null && null != mBluetoothGatt) {
            mBluetoothGatt.close();
            mBluetoothGatt.disconnect();
            mBluetoothGatt = null;
        }
        mDevWriteCharacteristic = null;
        mDevReadCharacteristic = null;
        return true;
    }

    @Override
    public boolean close() {
        mBluetoothAdapter = null;
        return true;
    }

    @Override
    public boolean send(String str) {
        if (mDevWriteCharacteristic == null) {
            getService();
        }
        return sendStrToDev(str);
    }

    private boolean getService() {
        if (null == mBluetoothGatt) {
            return false;
        }
        if (mDevWriteCharacteristic != null) {
            return true;
        }
        if (gattServiceMain == null) return false;

        //获取写的特征值
        List<BluetoothGattCharacteristic> characteristicList = gattServiceMain.getCharacteristics();
        for (BluetoothGattCharacteristic characteristic : characteristicList) {
            Log.w("---》》》","writeUUID:"+characteristic.getUuid().toString());
            if ((characteristic.getProperties() == BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE
                    || characteristic.getProperties() == BluetoothGattCharacteristic.PROPERTY_WRITE
                    || characteristic.getProperties() == BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) &&
                        isCurrentUuid(characteristic.getUuid().toString(),writeUUID)) {
//                    characteristic.getUuid().toString().indexOf(writeUUID) >= 0) {
                mDevWriteCharacteristic = characteristic;
                break;
            }
        }

        return mDevWriteCharacteristic == null;
    }


    private boolean sendStrToDev(String str) {
        if (mDevWriteCharacteristic == null) {
            return false;
        }
        byte[] value = new byte[10];
        value[0] = (byte) 0x00;
        mDevWriteCharacteristic.setValue(value[0], BluetoothGattCharacteristic.FORMAT_UINT8, 0);
        mDevWriteCharacteristic.setValue(str);
        return mBluetoothGatt.writeCharacteristic(mDevWriteCharacteristic);
    }

    private boolean sendByteToDev(byte[] byteCmd) {
        if (mDevWriteCharacteristic == null) {
            return false;
        }
        mDevWriteCharacteristic.setValue(byteCmd);
        return mBluetoothGatt.writeCharacteristic(mDevWriteCharacteristic);
    }

    @Override
    public boolean startScan(BluetoothAdapter.LeScanCallback callBack) {

        if (mBluetoothAdapter == null) {
            return false;
        }
        if (mBluetoothAdapter.isDiscovering()) {
            return false;
        }

        mBluetoothAdapter.startLeScan(callBack);
        this.leScanCallback = callBack;
        return true;
    }

    @Override
    public boolean stopScan() {
        if (this.leScanCallback == null) {
            return false;
        }
        mBluetoothAdapter.stopLeScan(this.leScanCallback);
        this.leScanCallback = null;
        return true;
    }

    /*
    校验蓝牙权限
   */
    private void checkBluetoothPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return;
        }
        //校验是否已具有模糊定位权限
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        }

    }

    public static void OpenA2dp() {

        BluetoothProfile.ServiceListener bs = new BluetoothProfile.ServiceListener() {
            @Override
            public void onServiceConnected(int profile, BluetoothProfile proxy) {
                Log.w("Kavenir", "onServiceConnected");
                List<BluetoothDevice> bluetoothDevices = proxy.getConnectedDevices();
                if (bluetoothDevices.size() > 0) {
                    Log.w("Kavenir", "onServiceConnected==" + bluetoothDevices.get(0).getName());
                    if (profile == BluetoothProfile.HEADSET) {
                        BluetoothHeadset bh = (BluetoothHeadset) proxy;
                        if (bh.getConnectionState(bluetoothDevices.get(0)) == BluetoothProfile.STATE_CONNECTED) {
                            try {
                                bh.getClass().getMethod("connect", BluetoothDevice.class).invoke(bh, bluetoothDevices.get(0));
                                Log.w("Kavenir", "onServiceConnected==" + "headset通道");
                            } catch (Exception e) {
                            }
                        }
                    } else if (profile == BluetoothProfile.A2DP) {
                        BluetoothA2dp a2dp = (BluetoothA2dp) proxy;
                        if (a2dp.getConnectionState(bluetoothDevices.get(0)) == BluetoothProfile.STATE_CONNECTED) {
                            try {
                                a2dp.getClass().getMethod("connect", BluetoothDevice.class).invoke(a2dp, bluetoothDevices.get(0));
                                Log.w("Kavenir", "onServiceConnected==" + "a2dp通道");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }

            @Override
            public void onServiceDisconnected(int profile) {
                Log.e("Kavenir", "未连接");
            }
        };

    }
}
