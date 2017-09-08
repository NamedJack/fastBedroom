package com.ejar.fastbedroom.home;

import com.ejar.fastbedroom.deliver.RvBean;
import com.ejar.fastbedroom.personal.LogoutBean;
import com.ejar.fastbedroom.personal.UserInfoBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
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


    @FormUrlEncoded
    @POST("pub/userinfo")
    Observable<UserInfoBean> getPersonInfo(@Field("token") String token);

}
