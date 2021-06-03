package com.jxxx.rhtx.bean;

import java.io.Serializable;
import java.util.List;

public class DeviceTypeListAll implements Serializable {

    /**
     * children : [{"createTime":1621475163000,"delTf":0,"details":"","deviceName":"跳舞毯","id":16,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/0265B7F9630C2424814A78C974E6ADC8.png","linkUrl":"/pages/toughBox/toughBox","parentId":9,"sortName":"HC-08","status":1},{"createTime":1621475157000,"delTf":0,"deviceName":"瑜伽垫","id":15,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/8124600BB573BA6D812C101315400215.png","parentId":9,"sortName":"","status":1},{"createTime":1621475050000,"delTf":0,"details":"<p>111222<\/p>","deviceName":"I箭靶","id":12,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/512A665B88D4922ABCC175C23FA7A9EB.png","parentId":9,"sortName":"","status":1},{"createTime":1621475039000,"delTf":0,"details":"","deviceName":"划船机","id":11,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/2C81C36C213911B85E68B262287187F9.png","parentId":9,"sortName":"","status":1},{"createTime":1621475031000,"delTf":0,"deviceName":"小韧智能沙袋","id":10,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/1BF76A2EF924A6B9B3375CF3810F79DC.png","parentId":9,"sortName":"","status":1},{"createTime":1620985432000,"delTf":0,"deviceName":"韧盒","id":8,"imgUrl":"https://elastech.nbqichen.com/zhongkeyan/upload/E99E344BEDA2E21E05B5D34D833A722A.png","linkUrl":"/pages/toughBox/toughBox","parentId":9,"sortName":"HC-08","status":1}]
     * createTime : 1620985434000
     * delTf : 0
     * deviceName : 智慧体育
     * id : 9
     * imgUrl : https://elastech.nbqichen.com/zhongkeyan/upload/D7165AA9DCA4B7AAA95C4AA1332BE63E.jpg
     * parentId : 0
     * status : 1
     */

    private long createTime;
    private int delTf;
    private String deviceName;
    private int id;
    private String imgUrl;
    private int parentId;
    private int status;
    private List<ChildrenBean> children;

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ChildrenBean> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenBean> children) {
        this.children = children;
    }

    public static class ChildrenBean implements Serializable {
        /**
         * createTime : 1621475163000
         * delTf : 0
         * details :
         * deviceName : 跳舞毯
         * id : 16
         * imgUrl : https://elastech.nbqichen.com/zhongkeyan/upload/0265B7F9630C2424814A78C974E6ADC8.png
         * linkUrl : /pages/toughBox/toughBox
         * parentId : 9
         * sortName : HC-08
         * status : 1
         */

        private long createTime;
        private int delTf;
        private String details;
        private String deviceName;
        private int id;
        private String imgUrl;
        private String linkUrl;
        private int parentId;
        private String sortName;
        private int status;

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

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
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

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getSortName() {
            return sortName;
        }

        public void setSortName(String sortName) {
            this.sortName = sortName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
