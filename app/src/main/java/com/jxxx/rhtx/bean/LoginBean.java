package com.jxxx.rhtx.bean;

import com.jxxx.rhtx.utils.StringUtil;

import java.math.BigDecimal;

public class LoginBean {

    /**
     * id : 20
     * nickName : 13111111111
     * realName : null
     * mobile : 13111111111
     * avatar : https://api.nbqichen.net/postcard/upload/avatar/defaultAvatar.jpg
     * saltValue : vWEvisrenm
     * loginPswd : 0558786C10D3F82F904C69597D44A706
     * payPswd : null
     * refererId : 0
     * openId : null
     * inviteCode : 203620
     * status : 1
     * delTf : 0
     * createTime : 2020-11-18T03:57:49.000+00:00
     * updateTime : null
     * infoFlag : null
     * userType : null
     * vipLevel : null
     * sex : null
     * birthday : null
     * region : null
     * height : null
     * weight : null
     * age : null
     * refererName : null
     * referCode : null
     * agentId : null
     * vipName1 : null
     * vipName2 : null
     * vipImgUrl : null
     * discount : null
     * nextCount : null
     * collectCount : null
     * refererAvatar : null
     * balance : null
     * Stringegral : null
     * imgType : null
     * clientType : null
     * userState : null
     * tokenId : 111ac498-1a96-4094-8183-6eabe4406961
     * sessionKey : null
     * userAgentId : null
     * envelopesSum : 0
     * createTimeStr : null
     * count : null
     * sumAmount : null
     * growthValue : null
     * maxGrowthValue : null
     * signCount : null
     * signFlag : false
     * agentType : null
     * messageNum : null
     */

    private String id;
    private String nickName;
    private String realName;
    private String mobile;
    private String avatar;
    private String saltValue;
    private String loginPswd;
    private String payPswd;
    private String refererId;
    private String openId;
    private String inviteCode;
    private String status;
    private String delTf;
    private String createTime;
    private String updateTime;
    private String infoFlag;
    private String userType;
    private String vipLevel;
    private int sex;
    private String birthday;
    private String region;
    private String height;
    private String weight;
    private String age;
    private String refererName;
    private String referCode;
    private String agentId;
    private String vipName1;
    private String vipName2;
    private String vipImgUrl;
    private String discount;
    private String nextCount;
    private String collectCount;
    private String refererAvatar;
    private String balance;
    private String Stringegral;
    private String imgType;
    private String clientType;
    private String userState;
    private String tokenId;
    private String sessionKey;
    private String userAgentId;
    private String envelopesSum;
    private String createTimeStr;
    private String count;
    private String sumAmount;
    private String growthValue;
    private String maxGrowthValue;
    private String signCount;
    private boolean signFlag;
    private String agentType;
    private String messageNum;
    private String defaultShow;

    public void setDefaultShow(String defaultShow) {
        this.defaultShow = defaultShow;
    }

    public String getDefaultShow() {
        return defaultShow;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSaltValue() {
        return saltValue;
    }

    public void setSaltValue(String saltValue) {
        this.saltValue = saltValue;
    }

    public String getLoginPswd() {
        return loginPswd;
    }

    public void setLoginPswd(String loginPswd) {
        this.loginPswd = loginPswd;
    }

    public String getPayPswd() {
        return payPswd;
    }

    public void setPayPswd(String payPswd) {
        this.payPswd = payPswd;
    }

    public String getRefererId() {
        return refererId;
    }

    public void setRefererId(String refererId) {
        this.refererId = refererId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDelTf() {
        return delTf;
    }

    public void setDelTf(String delTf) {
        this.delTf = delTf;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getInfoFlag() {
        return infoFlag;
    }

    public void setInfoFlag(String infoFlag) {
        this.infoFlag = infoFlag;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getHeight() {
        if(StringUtil.isBlank(height)){
            return "0";
        }
        BigDecimal value = new BigDecimal(height);
        BigDecimal noZeros = value.stripTrailingZeros();
        return noZeros.toPlainString();
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        if(StringUtil.isBlank(weight)){
            return "0";
        }
        BigDecimal value = new BigDecimal(weight);
        BigDecimal noZeros = value.stripTrailingZeros();
        return noZeros.toPlainString();
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRefererName() {
        return refererName;
    }

    public void setRefererName(String refererName) {
        this.refererName = refererName;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getVipName1() {
        return vipName1;
    }

    public void setVipName1(String vipName1) {
        this.vipName1 = vipName1;
    }

    public String getVipName2() {
        return vipName2;
    }

    public void setVipName2(String vipName2) {
        this.vipName2 = vipName2;
    }

    public String getVipImgUrl() {
        return vipImgUrl;
    }

    public void setVipImgUrl(String vipImgUrl) {
        this.vipImgUrl = vipImgUrl;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getNextCount() {
        return nextCount;
    }

    public void setNextCount(String nextCount) {
        this.nextCount = nextCount;
    }

    public String getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(String collectCount) {
        this.collectCount = collectCount;
    }

    public String getRefererAvatar() {
        return refererAvatar;
    }

    public void setRefererAvatar(String refererAvatar) {
        this.refererAvatar = refererAvatar;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getIntegral() {
        return Stringegral;
    }

    public void setIntegral(String Stringegral) {
        this.Stringegral = Stringegral;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getUserState() {
        return userState;
    }

    public void setUserState(String userState) {
        this.userState = userState;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUserAgentId() {
        return userAgentId;
    }

    public void setUserAgentId(String userAgentId) {
        this.userAgentId = userAgentId;
    }

    public String getEnvelopesSum() {
        return envelopesSum;
    }

    public void setEnvelopesSum(String envelopesSum) {
        this.envelopesSum = envelopesSum;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(String sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getGrowthValue() {
        return growthValue;
    }

    public void setGrowthValue(String growthValue) {
        this.growthValue = growthValue;
    }

    public String getMaxGrowthValue() {
        return maxGrowthValue;
    }

    public void setMaxGrowthValue(String maxGrowthValue) {
        this.maxGrowthValue = maxGrowthValue;
    }

    public String getSignCount() {
        return signCount;
    }

    public void setSignCount(String signCount) {
        this.signCount = signCount;
    }

    public boolean isSignFlag() {
        return signFlag;
    }

    public void setSignFlag(boolean signFlag) {
        this.signFlag = signFlag;
    }

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getMessageNum() {
        return messageNum;
    }

    public void setMessageNum(String messageNum) {
        this.messageNum = messageNum;
    }
}
