package com.ejar.fastbedroom.Api;

import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.register.bean.CodeBean;
import com.ejar.fastbedroom.register.bean.ConfirmRegisterBean;
import com.ejar.fastbedroom.register.bean.PointBean;
import com.ejar.fastbedroom.register.bean.RegisterCodeBean;
import com.ejar.fastbedroom.register.bean.SchoolBean;

import io.reactivex.Observable;
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





    /**
     * 获取学校代理点
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("allshowagent")
    Observable<PointBean> querySchoolPoint(@Field("id") int id);


    /**
     * 忘记密码 获取验证码
     *
     * @param tel
     * @return
     */
    @FormUrlEncoded
    @POST("getVerifyCodeForgetPas")
    Observable<BaseBean> forgotPwd(@Field("tel") String tel);

    /**
     * 忘记密码验证验证码
     *
     * @param tel
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("forgetpastel")
    Observable<BaseBean> provideCode(@Field("tel") String tel,
                                     @Field("verifyCode") String code);

    /**
     * 确认修改密码
     * @param tel
     * @param password
     * @param password1
     * @return
     */
    @FormUrlEncoded
    @POST("forgetPas")
    Observable<BaseBean> confirmChangePwd(@Field("tel") String tel,
                                          @Field("password") String password,
                                          @Field("password1") String password1);

}

