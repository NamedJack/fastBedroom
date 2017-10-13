package com.ejar.fastbedroom.Api;


import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.mybook.bean.MailFinishOrderBean;
import com.ejar.fastbedroom.mybook.bean.MailPayOrderBean;
import com.ejar.fastbedroom.mybook.bean.OrderBean;
import com.ejar.fastbedroom.mybook.bean.StoreOrderBean;

import io.reactivex.Observable;
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
     * 未付款订单列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/usernpayOrderlist")
    Observable<OrderBean> getNotPayMailOrder(@Field("token") String token);

    /**
     * 未接单订单列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/userpayOrderlist")
    Observable<OrderBean> getNotSendOrder(@Field("token") String token);

    /**
     * 待签收 （已支付）订单列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/userhaveOrderlist")
    Observable<MailPayOrderBean> getUserHaveOrder(@Field("token") String token);



    /**
     * 取消未支付订单
     *
     * @param orderid
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/cancelOrder")
    Observable<BaseBean> userCancelNotPaidOrder(@Field("orderid") int orderid,
                                                @Field("token") String token);


    /**
     * 取消已支付订单
     * @param orderid
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/cancelpayOrder")
    Observable<BaseBean> userCancelPaidOrder(@Field("orderid") int orderid,
                                             @Field("token") String token);


    /**
     * 用户确认收货
     * @return
     */
    @FormUrlEncoded
    @POST("pub/overOrder")
    Observable<BaseBean> userConfirmOrder(@Field("orderid") int orderid,
                                            @Field("token") String token);

    /**
     * 已完成订单
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/useroverOrderlist")
    Observable<MailFinishOrderBean> mailFinishOrder(@Field("token") String token);

    /***********自营超市***************/

    /**
     * 订单列表获取
     * @param token
     * @param state
     * @return
     */
    @FormUrlEncoded
    @POST("pub/orderList")
    Observable<StoreOrderBean> getUserOrder(@Field("token") String token,
                                            @Field("state") int state,
                                            @Field("pageNo") int pageNo);

//    确认收货
    @FormUrlEncoded
    @POST("pub/confirm")
    Observable<BaseBean> storeConfirmOrder(@Field("token") String token,
                                           @Field("id") int id);

    //取消订单
    @FormUrlEncoded
    @POST("pub/cancel")
    Observable<BaseBean> storeCancelOder(@Field("token") String token,
                                         @Field("id") int id);


    /***********外卖配送***************/

}
