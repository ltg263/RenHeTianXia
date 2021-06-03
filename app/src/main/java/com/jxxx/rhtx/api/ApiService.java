package com.jxxx.rhtx.api;


import com.jxxx.rhtx.base.Result;
import com.jxxx.rhtx.bean.AddChangeList;
import com.jxxx.rhtx.bean.AddOrderData;
import com.jxxx.rhtx.bean.LoginBean;

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
     * 忘记密码
     */
    @POST("api/v1/user/update")
    Observable<Result> updateUserInfo(@Query("avatar") String avatar,
                                            @Query("sex") String sex,
                                            @Query("age") String age,
                                            @Query("weight") String weight,
                                        @Query("height") String mobileCode);


    /**
     * 添加设备
     */
    @POST("api/v1/user/device/add")
    Observable<Result<AddOrderData>> userAddDevice(@Body AddOrderData addOrderData);

    /**
     * 添加设备记录列表
     */
    @POST("api/v1/user/device/addChangeList")
    Observable<Result> addChangeList(@Body AddChangeList addOrderData);

    /**
     *
     * 用户详情
     */
    @GET("api/v1/user/getDetail")
    Observable<Result<LoginBean>> getDetail();

    /**
     *
     * 用户详情
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
