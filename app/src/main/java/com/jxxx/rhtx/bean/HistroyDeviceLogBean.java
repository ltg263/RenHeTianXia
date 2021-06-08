package com.jxxx.rhtx.bean;

import java.io.Serializable;
import java.util.List;

public class HistroyDeviceLogBean {

    private int count;
    private List<HistroyDeviceBean> list;

    public void setCount(int count) {
        this.count = count;
    }

    public void setList(List<HistroyDeviceBean> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public List<HistroyDeviceBean> getList() {
        return list;
    }

    public static class HistroyDeviceBean implements Serializable {
        /**
         * createTime : 1620992141000
         * delTf : 0
         * deviceName : Smart-G
         * deviceNo : F8:33:31:A8:C9:1E
         * deviceType : 1
         * id : 19
         * imgUrl : https://elastech.nbqichen.com/zhongkeyan/upload/545517FA9AF4CF6DEF789C2D9AD96BBC.png
         * status : 1
         * typeLink : /pages/smartGlovesData/smartGlovesData
         * typeStr : 智能手套
         * userId : 32
         */

        private long createTime;
        private int delTf;
        private String deviceName;
        private String deviceNo;
        private int deviceType;
        private int id;
        private String imgUrl;
        private int status;
        private String typeLink;
        private String typeStr;
        private int userId;

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
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

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTypeLink() {
            return typeLink;
        }

        public void setTypeLink(String typeLink) {
            this.typeLink = typeLink;
        }

        public String getTypeStr() {
            return typeStr;
        }

        public void setTypeStr(String typeStr) {
            this.typeStr = typeStr;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
