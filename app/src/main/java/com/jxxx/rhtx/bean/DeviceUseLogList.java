package com.jxxx.rhtx.bean;

import java.io.Serializable;
import java.util.List;

public class DeviceUseLogList {

    /**
     * count : 201
     * list : [{"deviceId":18,"deviceName":"韧盒","endTime":1622102515000,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/E99E344BEDA2E21E05B5D34D833A722A.png","mACAddress":"F8:33:31:A8:C9:0A","sortName":"HC-08","startTime":1622102385000,"useDuration":"2分钟"},{"deviceId":18,"deviceName":"韧盒","endTime":1622102374000,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/E99E344BEDA2E21E05B5D34D833A722A.png","mACAddress":"F8:33:31:A8:C9:0A","sortName":"HC-08","startTime":1622102282000,"useDuration":"1分钟"},{"deviceId":18,"deviceName":"韧盒","endTime":1622079242000,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/E99E344BEDA2E21E05B5D34D833A722A.png","mACAddress":"F8:33:31:A8:C9:0A","sortName":"HC-08","startTime":1622079176000,"useDuration":"1分钟"},{"deviceId":18,"deviceName":"韧盒","endTime":1622707230290,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/E99E344BEDA2E21E05B5D34D833A722A.png","mACAddress":"F8:33:31:A8:C9:0A","sortName":"HC-08","startTime":1622078957000,"useDuration":"7天6小时31分钟"},{"deviceId":17,"deviceName":"呼吸带","endTime":1622707230290,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/02C8066DCC7727D35CCC5CE5A6AF965D.png","mACAddress":"34:14:B5:BB:D1:8E","sortName":"Smart-H","startTime":1622014052000,"useDuration":"8天32分钟"},{"deviceId":17,"deviceName":"呼吸带","endTime":1622014032000,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/02C8066DCC7727D35CCC5CE5A6AF965D.png","mACAddress":"34:14:B5:BB:D1:8E","sortName":"Smart-H","startTime":1622013931000,"useDuration":"1分钟"},{"deviceId":17,"deviceName":"呼吸带","endTime":1622013905000,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/02C8066DCC7727D35CCC5CE5A6AF965D.png","mACAddress":"34:14:B5:BB:D1:8E","sortName":"Smart-H","startTime":1622013827000,"useDuration":"1分钟"},{"deviceId":17,"deviceName":"呼吸带","endTime":1622707230290,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/02C8066DCC7727D35CCC5CE5A6AF965D.png","mACAddress":"34:14:B5:BB:D1:8E","sortName":"Smart-H","startTime":1622013743000,"useDuration":"8天38分钟"},{"deviceId":17,"deviceName":"呼吸带","endTime":1622707230290,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/02C8066DCC7727D35CCC5CE5A6AF965D.png","mACAddress":"34:14:B5:BB:D1:8E","sortName":"Smart-H","startTime":1622013660000,"useDuration":"8天39分钟"},{"deviceId":17,"deviceName":"呼吸带","endTime":1622013640000,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/02C8066DCC7727D35CCC5CE5A6AF965D.png","mACAddress":"34:14:B5:BB:D1:8E","sortName":"Smart-H","startTime":1622013573000,"useDuration":"1分钟"}]
     */

    private int count;
    private List<ListBean> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable {
        /**
         * deviceId : 18
         * deviceName : 韧盒
         * endTime : 1622102515000
         * imgUrl : https://elastech.nbqichen.com/zhongkeyan/upload/E99E344BEDA2E21E05B5D34D833A722A.png
         * mACAddress : F8:33:31:A8:C9:0A
         * sortName : HC-08
         * startTime : 1622102385000
         * useDuration : 2分钟
         */

        private int deviceId;
        private String deviceName;
        private long endTime;
        private String imgUrl;
        private String mACAddress;
        private String sortName;
        private long startTime;
        private String useDuration;

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getMACAddress() {
            return mACAddress;
        }

        public void setMACAddress(String mACAddress) {
            this.mACAddress = mACAddress;
        }

        public String getSortName() {
            return sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public String getUseDuration() {
            return useDuration;
        }

        public void setUseDuration(String useDuration) {
            this.useDuration = useDuration;
        }
    }
}
