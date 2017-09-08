package com.ejar.fastbedroom.register.model;

import com.ejar.fastbedroom.register.bean.CodeBean;
import com.ejar.fastbedroom.register.bean.ConfirmRegisterBean;
import com.ejar.fastbedroom.register.bean.RegisterCodeBean;
import com.ejar.fastbedroom.register.bean.SchoolBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by json on 2017/8/15.
 */

public interface RegisterApi {

    /**
     * 获取验证码
     * @param userTel
     * @return
     */
    @FormUrlEncoded
    @POST("getVerifyCode")
    Observable<RegisterCodeBean> getRegisterCode(@Field("tel") String userTel);


    /**
     * 验证注册码
     * @param userTel
     * @param userCode
     * @return
     */
    @FormUrlEncoded
    @POST("regUsertel")
    Observable<CodeBean> provingTel(@Field("tel") String userTel, @Field("verifyCode") String userCode);


    /**
     * 确认注册
     * @param userTel
     * @param userSchool
     * @param startYearSchool
     * @param pwd
     * @param confirmPwd
     * @param userName
     * @param userSex
     * @return
     */
    @FormUrlEncoded
    @POST("regUser")
    Observable<ConfirmRegisterBean> doRegister(@Field("tel") String userTel,
                                               @Field("schoolid") int userSchool,
                                               @Field("intSlyear") String startYearSchool,
                                               @Field("password1") String pwd,
                                               @Field("password2") String confirmPwd,
                                               @Field("name") String userName,
                                               @Field("sex") int userSex);

    /**
     * 查询学校列表
     * @param time
     * @return
     */
    @FormUrlEncoded
    @POST("querySchool")
    Observable<SchoolBean> querySchool(@Field("time") String time);


}

