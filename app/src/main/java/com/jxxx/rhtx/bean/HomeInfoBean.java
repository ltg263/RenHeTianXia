package com.jxxx.rhtx.bean;

import java.io.Serializable;
import java.util.List;

public class HomeInfoBean implements Serializable {

    /**
     * histroyDevice : [{"createTime":1620992141000,"delTf":0,"deviceName":"Smart-G","deviceNo":"F8:33:31:A8:C9:1E","deviceType":1,"id":19,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/545517FA9AF4CF6DEF789C2D9AD96BBC.png","status":1,"typeLink":"/pages/smartGlovesData/smartGlovesData","typeStr":"智能手套","userId":32},{"createTime":1620991141000,"delTf":0,"deviceName":"HC-08","deviceNo":"F8:33:31:A8:C9:0A","deviceType":8,"id":18,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/E99E344BEDA2E21E05B5D34D833A722A.png","status":1,"typeLink":"/pages/toughBox/toughBox","typeStr":"韧盒","userId":32},{"createTime":1620989343000,"delTf":0,"deviceName":"Smart-H","deviceNo":"34:14:B5:BB:D1:8E","deviceType":2,"id":17,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/02C8066DCC7727D35CCC5CE5A6AF965D.png","status":1,"typeLink":"/pages/breatheZone/breatheZone","typeStr":"呼吸带","userId":32}]
     * user : {"avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKOrllZAeqwpvMV8wr8nQZyyqVLsc6oKpb4Ric16tDDpdL5xrqLLkMXwlqSktzOheRSOGxh5FTZDtQ/132","birthday":"1991-03-03","createTime":1620988552000,"defaultShow":2,"delTf":0,"envelopesSum":0,"height":180,"id":32,"inviteCode":"701658","loginPswd":"609CEA5B599F2A4212BF3182B4CF85BB","nickName":"呆","openId":"oZsyM5EovxdlMZIdlN4jmdW9YUDw","refererId":0,"region":"浙江 衢州 中国","saltValue":"OIa9GkF1Ku","sex":1,"status":1,"weight":48.1}
     * device : {"changeList":[{"changeTime":1622014234000,"createTime":1622014234000,"deviceId":17,"id":44244,"value":"1073"},{"changeTime":1622014231000,"createTime":1622014231000,"deviceId":17,"id":44243,"value":"1083"},{"changeTime":1622014227000,"createTime":1622014227000,"deviceId":17,"id":44242,"value":"1073"},{"changeTime":1622014223000,"createTime":1622014223000,"deviceId":17,"id":44241,"value":"1086"},{"changeTime":1622014219000,"createTime":1622014219000,"deviceId":17,"id":44240,"value":"1086"},{"changeTime":1622014215000,"createTime":1622014215000,"deviceId":17,"id":44239,"value":"1091"},{"changeTime":1622014211000,"createTime":1622014211000,"deviceId":17,"id":44238,"value":"1089"},{"changeTime":1622014207000,"createTime":1622014206000,"deviceId":17,"id":44237,"value":"1085"},{"changeTime":1622014202000,"createTime":1622014202000,"deviceId":17,"id":44236,"value":"1085"},{"changeTime":1622014198000,"createTime":1622014197000,"deviceId":17,"id":44235,"value":"1088"}],"createTime":1620989343000,"delTf":0,"deviceName":"Smart-H","deviceNo":"34:14:B5:BB:D1:8E","deviceType":2,"id":17,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/02C8066DCC7727D35CCC5CE5A6AF965D.png","status":1,"typeLink":"/pages/breatheZone/breatheZone","typeStr":"呼吸带","userId":32}
     */

    private UserBean user;
    private DeviceBean device;
    private List<HistroyDeviceBean> histroyDevice;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public DeviceBean getDevice() {
        return device;
    }

    public void setDevice(DeviceBean device) {
        this.device = device;
    }

    public List<HistroyDeviceBean> getHistroyDevice() {
        return histroyDevice;
    }

    public void setHistroyDevice(List<HistroyDeviceBean> histroyDevice) {
        this.histroyDevice = histroyDevice;
    }

    public static class UserBean implements Serializable {
        /**
         * avatar : https://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEKOrllZAeqwpvMV8wr8nQZyyqVLsc6oKpb4Ric16tDDpdL5xrqLLkMXwlqSktzOheRSOGxh5FTZDtQ/132
         * birthday : 1991-03-03
         * createTime : 1620988552000
         * defaultShow : 2
         * delTf : 0
         * envelopesSum : 0
         * height : 180
         * id : 32
         * inviteCode : 701658
         * loginPswd : 609CEA5B599F2A4212BF3182B4CF85BB
         * nickName : 呆
         * openId : oZsyM5EovxdlMZIdlN4jmdW9YUDw
         * refererId : 0
         * region : 浙江 衢州 中国
         * saltValue : OIa9GkF1Ku
         * sex : 1
         * status : 1
         * weight : 48.1
         */

        private String avatar;
        private String birthday;
        private long createTime;
        private int defaultShow;
        private int delTf;
        private int envelopesSum;
        private double height;
        private int id;
        private String inviteCode;
        private String loginPswd;
        private String nickName;
        private String openId;
        private int refererId;
        private String region;
        private String saltValue;
        private int sex;
        private int status;
        private double weight;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public int getDefaultShow() {
            return defaultShow;
        }

        public void setDefaultShow(int defaultShow) {
            this.defaultShow = defaultShow;
        }

        public int getDelTf() {
            return delTf;
        }

        public void setDelTf(int delTf) {
            this.delTf = delTf;
        }

        public int getEnvelopesSum() {
            return envelopesSum;
        }

        public void setEnvelopesSum(int envelopesSum) {
            this.envelopesSum = envelopesSum;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInviteCode() {
            return inviteCode;
        }

        public void setInviteCode(String inviteCode) {
            this.inviteCode = inviteCode;
        }

        public String getLoginPswd() {
            return loginPswd;
        }

        public void setLoginPswd(String loginPswd) {
            this.loginPswd = loginPswd;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public int getRefererId() {
            return refererId;
        }

        public void setRefererId(int refererId) {
            this.refererId = refererId;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getSaltValue() {
            return saltValue;
        }

        public void setSaltValue(String saltValue) {
            this.saltValue = saltValue;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }

    public static class DeviceBean implements Serializable {
        /**
         * changeList : [{"changeTime":1622014234000,"createTime":1622014234000,"deviceId":17,"id":44244,"value":"1073"},{"changeTime":1622014231000,"createTime":1622014231000,"deviceId":17,"id":44243,"value":"1083"},{"changeTime":1622014227000,"createTime":1622014227000,"deviceId":17,"id":44242,"value":"1073"},{"changeTime":1622014223000,"createTime":1622014223000,"deviceId":17,"id":44241,"value":"1086"},{"changeTime":1622014219000,"createTime":1622014219000,"deviceId":17,"id":44240,"value":"1086"},{"changeTime":1622014215000,"createTime":1622014215000,"deviceId":17,"id":44239,"value":"1091"},{"changeTime":1622014211000,"createTime":1622014211000,"deviceId":17,"id":44238,"value":"1089"},{"changeTime":1622014207000,"createTime":1622014206000,"deviceId":17,"id":44237,"value":"1085"},{"changeTime":1622014202000,"createTime":1622014202000,"deviceId":17,"id":44236,"value":"1085"},{"changeTime":1622014198000,"createTime":1622014197000,"deviceId":17,"id":44235,"value":"1088"}]
         * createTime : 1620989343000
         * delTf : 0
         * deviceName : Smart-H
         * deviceNo : 34:14:B5:BB:D1:8E
         * deviceType : 2
         * id : 17
         * imgUrl : https://elastech.nbqichen.com/zhongkeyan/upload/02C8066DCC7727D35CCC5CE5A6AF965D.png
         * status : 1
         * typeLink : /pages/breatheZone/breatheZone
         * typeStr : 呼吸带
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
        private List<ChangeListBean> changeList;

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

        public List<ChangeListBean> getChangeList() {
            return changeList;
        }

        public void setChangeList(List<ChangeListBean> changeList) {
            this.changeList = changeList;
        }

        public static class ChangeListBean implements Serializable {
            /**
             * changeTime : 1622014234000
             * createTime : 1622014234000
             * deviceId : 17
             * id : 44244
             * value : 1073
             */

            private long changeTime;
            private long createTime;
            private int deviceId;
            private int id;
            private String value;

            public long getChangeTime() {
                return changeTime;
            }

            public void setChangeTime(long changeTime) {
                this.changeTime = changeTime;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
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
