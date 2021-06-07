package com.jxxx.rhtx.lanya;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.jxxx.rhtx.app.MainApplication;
import com.jxxx.rhtx.base.BaseActivity;
import com.jxxx.rhtx.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BluetoothLjUtils {
    public static Ble4_0Util ble4Util;
    public static void sousuo(BaseActivity mBaseActivity,String name,BluetoothLjInterface lineInterface){
        ble4Util.startScan(new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(BluetoothDevice bluetoothDevice, int rssi, byte[] bytes) {
                if (bluetoothDevice.getName() != null && bluetoothDevice.getName().length() > 0){
                    Log.w("---》》》","bluetoothDevice.getName():"+bluetoothDevice.getName());
                    if(bluetoothDevice.getName().equals(name)){
                        lineInterface.linkState(1);
                        goLianJie(mBaseActivity,bluetoothDevice.getAddress(),lineInterface);
                    }
                }
            }
        });
    }
    public static void goLianJie(BaseActivity mBaseActivity, String bluetoothName, BluetoothLjInterface lineInterface) {
//        ble4Util.init();
        String[] uuidData = new String[3];
        uuidData[0] = "0000ffe0-0000-1000-8000-00805f9b34fb";
        uuidData[1] = "0000ffe1-0000-1000-8000-00805f9b34fb";
        uuidData[2] = "0000ffe1-0000-1000-8000-00805f9b34fb";
        ble4Util.setUuidStr(uuidData);
        ble4Util.stopScan();
        ble4Util.connect(bluetoothName, new BleUtil.CallBack() {
            @Override
            public void StateChange(int state, int newState) {
                String value = null;
                if (newState == BluetoothGatt.STATE_CONNECTED){
                    lineInterface.linkState(2);
                    value = "连接成功";
                } else if (newState == BluetoothGatt.STATE_DISCONNECTED){
                    lineInterface.linkState(0);
                    value = "连接失败";
                } else if(newState == BluetoothGatt.STATE_CONNECTING){
                    lineInterface.linkState(3);
                    value = "连接设备中";
                } else if(newState == BluetoothGatt.STATE_DISCONNECTING){
                    value = "断开连接中";
                    lineInterface.linkState(4);
                }
                Log.w("---》》》","value:"+value);
//                if (linkHandler != null && value != null){
//                    //发送连接成功通知
//                    Message message = new Message();
//                    message.what = 99;
//                    message.obj = value;
//                    linkHandler.sendMessage(message);
//                }
//                ToastUtil.showLongStrToast(mBaseActivity,value);
            }

            @Override
            public void ReadValue(byte[] value) {
//                mBaseActivity.dismiss();
                if (linkHandler != null){
                    Message message = new Message();
                    message.what = 101;
                    message.obj = value;
                    linkHandler.sendMessage(message);
                }

            }
        });
    }
    public static Handler linkHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 99:
                    //连接
                    Ble4_0Util.OpenA2dp();
                    if(message.obj.toString().equals("连接成功")){

                    }
                    break;
                case 101:
                    byte[] resultData = (byte[]) message.obj;
                    Log.w("---》》》","接收resultData："+Arrays.toString(resultData));
                    String resultData_0xff = "";
                    for (int i = 0; i < resultData.length; i++) {
                        resultData_0xff += Integer.toHexString(resultData[i] & 0xFF)+",";
                    }
                    startBroadcast(resultData);
                    Log.w("---》》》","接收resultData_0xff："+resultData_0xff);
                    break;
            }
            return false;
        }
    });
    public interface BluetoothLjInterface{
        //1搜索成功//2链接成功//3链接中//4断开连接中
        void linkState(int state);
    }

    private static void startBroadcast(byte[] resultData){
        //开启广播
        //创建一个意图对象
        Intent intent = new Intent("com.jxxx.rhtx");
        //发送广播的数据
        intent.putExtra("resultData", resultData);
        //发送
        MainApplication.getContext().sendBroadcast(intent);
    }
}
