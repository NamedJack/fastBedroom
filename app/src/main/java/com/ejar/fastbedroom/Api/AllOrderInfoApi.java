package com.ejar.fastbedroom.Api;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by json on 2017/9/14.
 * 个人中心里面的  未支付  未接单  已支付  已完成四个点解跳转界面 接口
 */

public interface AllOrderInfoApi {

    /***********快递领取***************/

    /**
     * 未付款
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/usernpayOrderlist")
    Observable<ResponseBody> getNotPayMailOrder(@Field("token") String token);

    /**
     * 未接单
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/userpayOrderlist")
    Observable<ResponseBody> getNotSendOrder(@Field("token") String token);

    /**
     * 待签收 （已支付）
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/userhaveOrderlist")
    Observable<ResponseBody> getUserHaveOrder(@Field("token") String token);




    /***********自营超市***************/




    /***********外卖配送***************/

}
