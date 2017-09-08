package com.ejar.fastbedroom.mybook.modle;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by json on 2017/8/29.
 * 订单api
 */

public interface BookApi {

    /**********未支付***************/

    /**
     * 自营超市
     * @param state 1:未支付 2：已支付 3：已配送 4：已送达 5：已完成
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/orderList")
    Observable<ResponseBody> notPaidStore(@Field("state") String state, @Field("token") String token);


    /**
     * 外卖配送
     * @param state 1:未付款 2:已支付(未配送) 3:已接单 4:已完成
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/busOrder")
    Observable<ResponseBody> notPaidDeliver(@Field("state") String state, @Field("token") String token);

    /**
     * 快递领取
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/usernpayOrderlist")
    Observable<ResponseBody> notPaidReceive(@Field("token") String token);






    /**********未接单***************/

    /**
     * 快递领取
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/userpayOrderlist")
    Observable<ResponseBody> notAccept(@Field("token") String token);

    /**********已支付***************/

    /**
     * 快递领取
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/userhaveOrderlist")
    Observable<ResponseBody> waitDeliver(@Field("token") String token);

    /**********已完成***************/

    /**
     *快递领取
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/useroverOrderlist")
    Observable<ResponseBody> overOrder(@Field("token") String token);
}
