package com.jxxx.rhtx.bean;

import java.io.Serializable;

public class DeviceDetailsBaen implements Serializable {

    /**
     * createTime : 1620985432000
     * delTf : 0
     * deviceName : 韧盒
     * id : 8
     * imgUrl : https://elastech.nbqichen.com/zhongkeyan/upload/E99E344BEDA2E21E05B5D34D833A722A.png
     * linkUrl : /pages/toughBox/toughBox
     * parentId : 9
     * sortName : HC-08
     * status : 1
     */

    private long createTime;
    private int delTf;
    private String deviceName;
    private String details;
    private int id;
    private String imgUrl;
    private String linkUrl;
    private int parentId;
    private String sortName;
    private String remark;
    private int status;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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
