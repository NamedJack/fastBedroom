package com.ejar.fastbedroom.Api;


import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.buycar.bean.BuyCarBean;
import com.ejar.fastbedroom.buycar.bean.PayOrder;
import com.ejar.fastbedroom.mystore.GoodsDetailAty;
import com.ejar.fastbedroom.mystore.bean.BannerBean;
import com.ejar.fastbedroom.mystore.bean.DetailBuyBean;
import com.ejar.fastbedroom.mystore.bean.FastBuyBean;
import com.ejar.fastbedroom.mystore.bean.GoodsDetailBean;
import com.ejar.fastbedroom.mystore.bean.OrderPostBean;
import com.ejar.fastbedroom.mystore.bean.RecommendBean;
import com.ejar.fastbedroom.mystore.bean.RowBean;
import com.ejar.fastbedroom.mystore.bean.SecondStoreRvBean;
import com.ejar.fastbedroom.mystore.bean.TabCenterBean;
import com.ejar.fastbedroom.pay.bean.StoreZfbOrderSign;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by json on 2017/8/29.
 */

public interface StoreApi {

    /**************************一级页面 点击首页自营超市进入**************************************/
    @FormUrlEncoded
    @POST("pub/wmimg")
    Observable<BannerBean> getBanner(@Field("token") String token);

    @FormUrlEncoded
    @POST("pub/goodsYes")
    Observable<RecommendBean> getRecommend(@Field("token") String token);


    @FormUrlEncoded
    @POST("pub/goodStype")
    Observable<TabCenterBean>  getRvTab(@Field("token") String token);


    /**************************二级页面 点击自营超市中间tab条目进入**************************************/

    @FormUrlEncoded
    @POST("pub/goodStype")
    Observable<TabCenterBean>  getUseTab(@Field("token") String token, @Field("pid") String pid);


    @FormUrlEncoded
    @POST("pub/goodsList")
    Observable<SecondStoreRvBean>  getUseRvList(@Field("token") String token, @Field("proId") String pid
            , @Field("pageNo") String pageNo);


    /**
     * 提交购物车
     * @param token
     * @param json
     * @return
     */
    @FormUrlEncoded
    @POST("pub/cartAdd")
    Observable<BaseBean> goodsPost(@Field("token") String token, @Field("josn") String json);


    /**
     * 获取购物车列表
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/cartList")
    Observable<BuyCarBean> getBuyCar(@Field("token") String token);

    /**
     * 结算物品生成订单号
     * @param token
     * @param totalMoney
     * @param jsonData
     * @param addressId
     * @return
     */
    @FormUrlEncoded
    @POST("pub/submitCart")
    Observable<PayOrder> payGoods(@Field("token") String token ,
                                  @Field("totalMoney") String totalMoney ,
                                  @Field("jsonData") String jsonData ,
                                  @Field("addressId") String addressId);


    /*****************商品详情页*********************/

    /**
     * 单个商品的详情页
     * @param token
     * @param goodsId
     * @return
     */
    @FormUrlEncoded
    @POST("pub/goodsOne\n")
    Observable<GoodsDetailBean> getGoodsDetail(@Field("token") String token ,
                                               @Field("id") int goodsId );

    /**
     * 详情界面 结算
     * @return
     */
    @FormUrlEncoded
    @POST("pub/submit_Order")
    Observable<DetailBuyBean> detailBuy(@Field("token") String token,
                                        @Field("goodsId") int goodsId,
                                        @Field("number") int number,
                                        @Field("addressId") int addressId,
                                        @Field("totalMoney") String totalMoney);

    /**
     * 推荐商品 立即购买  直接购买一个  number = 1
     * @param token
     * @param goodsId
     * @param number
     * @param addressId
     * @param totalMoney
     * @return
     */
    @FormUrlEncoded
    @POST("pub/submit_Order")
    Observable<FastBuyBean> singleBuy(@Field("token") String token,
                                      @Field("goodsId") int goodsId,
                                      @Field("number") int number,
                                      @Field("addressId") int addressId,
                                      @Field("totalMoney") String totalMoney);


    @FormUrlEncoded
    @POST("pub/submitNowOrder")
    Observable<PayOrder> multiGoodsOrder(@Field("token") String token,
                                             @Field("jsonData") String jsonData,
                                             @Field("totalMoney") String totalMoney,
                                             @Field("addressId") int addressId);



    /*************************支付******************************/
    /**
     * 支付宝
     * @param token
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("pub/alipay")
    Observable<StoreZfbOrderSign> getZfbSign(@Field("token") String token,
                                             @Field("orderId") String orderId);



    /****************购物车删除商品*********************/
    @FormUrlEncoded
    @POST("pub/cartDel")
    Observable<BaseBean> deleteGoods(@Field("token") String token,
                                             @Field("json") String json);
}
