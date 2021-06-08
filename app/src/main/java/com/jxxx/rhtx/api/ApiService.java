package com.jxxx.rhtx.api;


import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.base.ResultList;
import com.jxxx.rhtx.bean.AddChangeList;
import com.jxxx.rhtx.bean.AddOrderData;
import com.jxxx.rhtx.bean.DeviceAddBean;
import com.jxxx.rhtx.bean.DeviceDetailsBaen;
import com.jxxx.rhtx.bean.DeviceTypeListAll;
import com.jxxx.rhtx.bean.DeviceUseLogList;
import com.jxxx.rhtx.bean.HistroyDeviceLogBean;
import com.jxxx.rhtx.bean.HomeInfoBean;
import com.jxxx.rhtx.bean.LoginBean;
import com.jxxx.rhtx.bean.ParamValueBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiService {
    /**
     * 获取token
     *
     * @return
     */
    @GET("api/v1/user/getSecuritytoken")
    Observable<Result> getSecuritytoken();


    /**
     * 获取短信验证码
     * @return type:类型0注册1修改密码2登录
     */
    @GET("api/v1/user/verify/getMobileCode")
    Observable<Result> getVerifyCode(@Query("mobile") String mobile, @Query("verifyType") int verifyType);


    /**
     * 手机注册
     */
    @POST("api/v1/user/verify/register")
    Observable<Result> postRegister(@Query("mobile") String mobile,
                                     @Query("loginPswd") String loginPswd,
                                     @Query("mobileCode")  String mobileCode);

    /**
     * 手机登录
     */
    @POST("api/v1/user/verify/login")
    Observable<Result<LoginBean>> postLogin(@Query("mobile") String mobile,
                                            @Query("loginPswd") String loginPswd);


    /**
     * 忘记密码
     */
    @POST("api/v1/user/verify/forgetPswd")
    Observable<Result> verifyForgetPswd(@Query("mobile") String mobile,
                                            @Query("loginPswd") String loginPswd,
                                        @Query("mobileCode") String mobileCode);


    /**
     * 更新用户信息
     */
    @POST("api/v1/user/update")
    Observable<Result> updateUserInfo(@Query("avatar") String avatar,
                                            @Query("birthday") String birthday,
                                            @Query("sex") int sex,
                                            @Query("weight") String weight,
                                            @Query("height") String height,
                                        @Query("nickName") String nickName,
                                        @Query("region") String region);


    /**
     * 添加设备
     */
    @POST("api/v1/user/device/add")
    Observable<Result<AddOrderData>> userAddDevice(@Body AddOrderData addOrderData);

    /**
     * 开始使用设备
     */
    @POST("api/v1/user/device/startUse")
    Observable<Result> startUseDevice(@Query("deviceId") int deviceId);

    /**
     * 结束使用设备
     */
    @POST("api/v1/user/device/endUse")
    Observable<Result> endUseDevice(@Query("deviceId") int deviceId);

    /**
     *
     * 用户首页
     */
    @GET("api/v1/user/index/home")
    Observable<Result<HomeInfoBean>> getHome();
    /**
     *
     * 用户首页背景图
     */
    @GET("api/v1/paramter/appointParamValue")
    Observable<Result<ParamValueBean>> appointParamValue();

    /**
     *
     * 用户设备
     */
    @GET("api/v1/deviceType/listAll")
    Observable<ResultList<DeviceTypeListAll>> getDeviceTypeListAll();

    /**
     *
     * 获取设备列表
     */
    @GET("api/v1/user/device/list")
    Observable<Result<HistroyDeviceLogBean>> getUseLogList(@Query("pageSize") int pageSize);

    /**
     *
     * 用户设备详情
     */
    @GET("api/v1/deviceType/details")
    Observable<Result<DeviceDetailsBaen>> getDeviceDetails(@Query("id") int id);

    /**
     *
     * 用户设备历史记录
     */
    @GET("api/v1/user/device/useLogList")
    Observable<Result<DeviceUseLogList>> getDeviceUseLogList(@Query("pageSize") int pageSize);

    /**
     *
     * 用户详情
     */
    @GET("api/v1/user/getDetail")
    Observable<Result<LoginBean>> getDetail();

    /**
     * 设置默认设备
     */
    @POST("api/v1/user/updateDefaultShow")
    Observable<Result> updateDefaultShow(@Query("defaultShow") String defaultShow);



    /**
     * 上传文件
     *
     * @return
     */
    @Multipart
    @POST("api/v1/files")
    Observable<Result> submitFiles(@Part MultipartBody.Part[] file, @PartMap Map<String, RequestBody> map);

}
