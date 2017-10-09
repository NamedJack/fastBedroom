package com.ejar.fastbedroom.Api;

import com.ejar.fastbedroom.deliver.RvBean;
import com.ejar.fastbedroom.message.bean.MessageBean;
import com.ejar.fastbedroom.personal.bean.LogoutBean;
import com.ejar.fastbedroom.personal.bean.ResultBean;
import com.ejar.fastbedroom.personal.bean.UserInfoBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by json on 2017/8/24.
 */

public interface HomeAtyApi {

    /**
     * 快到寝
     */

    @FormUrlEncoded
    @POST("pub/quickMessagelist")
    Observable<RvBean> getRvList(@Field("token") String token);



    /**
     * 消息推送
     */


    /*********************** 个人中心****************************************/

    /**
     * logout
     *
     */
    @FormUrlEncoded
    @POST("pub/loginOut")
    Observable<LogoutBean> logout(@Field("token") String token);

    /**
     * 用户信息获取
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/userinfo")
    Observable<UserInfoBean> getPersonInfo(@Field("token") String token);

    /**
     * 改昵称
     * @return
     */
    @FormUrlEncoded
    @POST("pub/updatename")
    Observable<ResultBean> changedName(@Field("name") String userName, @Field("token") String token);

    /**
     * 验证密码
     * @return
     */
    @FormUrlEncoded
    @POST("pub/updatepas")
    Observable<ResultBean> provingPwd(@Field("password") String pwd, @Field("token") String token);

    /**
     * 修改密码
     * @return
     */
    @FormUrlEncoded
    @POST("pub/updatepassword")
    Observable<ResultBean> changePwd(@Field("password") String pwd, @Field("password1") String pwd1,@Field("token") String token);

    /**
     * 验证电话号码
     * @return
     */
    @FormUrlEncoded
    @POST("pub/updatetelpas")
    Observable<ResultBean> provingPhone(@Field("tel") String tel, @Field("password") String pwd, @Field("token") String token);

    /**
     * 修改电话号码
     * @param tel
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/editTel")
    Observable<ResultBean> changePhone(@Field("tel") String tel,  @Field("token") String token);

    /**
     * message
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/postlist")
    Observable<MessageBean> getMessage(@Field("token") String token);




}
