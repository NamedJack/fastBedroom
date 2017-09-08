package com.ejar.fastbedroom.mystore;


import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.buycar.bean.BuyCarBean;
import com.ejar.fastbedroom.mystore.bean.BannerBean;
import com.ejar.fastbedroom.mystore.bean.OrderPostBean;
import com.ejar.fastbedroom.mystore.bean.RecommendBean;
import com.ejar.fastbedroom.mystore.bean.RowBean;
import com.ejar.fastbedroom.mystore.bean.SecondStoreRvBean;
import com.ejar.fastbedroom.mystore.bean.TabCenterBean;

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
    Observable<BuyCarBean> getBuyCar(@Field("token") String token, @Field("pageNo") int pageNo);

}
