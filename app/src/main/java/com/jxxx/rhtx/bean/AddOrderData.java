package com.jxxx.rhtx.bean;

import java.util.List;

/**
 * author : LiuJie
 * date   : 2020/6/115:56
 */
public class AddOrderData {


    /**
     * changeList : [{"changeTime":"2020-11-18T06:21:46.212Z","createTime":"2020-11-18T06:21:46.212Z","deviceId":0,"id":0,"value":"string"}]
     * createTime : 2020-11-18T06:21:46.212Z
     * delTf : 0
     * deviceName : string
     * deviceNo : string
     * deviceType : 0
     * id : 0
     * status : 0
     * userId : 0
     */
    private String createTime;
    private int delTf;
    private String deviceName;
    private String deviceNo;
    private int deviceType;
    private int id;
    private int status;
    private int userId;
    private List<ChangeListBean> changeList;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDelTf() {
        return delTf;
    }

    public void setDelTf(int delTf) {
        this.delTf = delTf;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public int getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(int deviceType) {
        this.deviceType = deviceType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<ChangeListBean> getChangeList() {
        return changeList;
    }

    public void setChangeList(List<ChangeListBean> changeList) {
        this.changeList = changeList;
    }

    public static class ChangeListBean {
        /**
         * changeTime : 2020-11-18T06:21:46.212Z
         * createTime : 2020-11-18T06:21:46.212Z
         * deviceId : 0
         * id : 0
         * value : string
         */

        private String changeTime;
        private String createTime;
        private int deviceId;
        private int id;
        private String value;

        public String getChangeTime() {
            return changeTime;
        }

        public void setChangeTime(String changeTime) {
            this.changeTime = changeTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }


}
