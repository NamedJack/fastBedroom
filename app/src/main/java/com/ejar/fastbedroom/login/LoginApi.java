package com.ejar.fastbedroom.login;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by json on 2017/8/15.
 */

public interface LoginApi {

    @FormUrlEncoded
    @POST("userlogin")
    Observable<UserBean> postLogin(@Field("tel") String userName, @Field("password") String userPwd);

}
