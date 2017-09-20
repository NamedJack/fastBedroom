package com.ejar.fastbedroom.Api;

import com.ejar.fastbedroom.BaseBean;
import com.ejar.fastbedroom.fastmail.bean.MailWayWarrper;
import com.ejar.fastbedroom.fastmail.bean.PostMailBean;
import com.ejar.fastbedroom.pay.bean.SignBean;
import com.ejar.fastbedroom.pay.bean.SignWxBean;
import com.ejar.fastbedroom.useraddr.bean.AddressBean;
import com.ejar.fastbedroom.useraddr.bean.SchoolAreaBean;
import com.tencent.mm.sdk.modelbase.BaseReq;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * Created by json on 2017/9/8.
 */

public interface UserCenterApi {
    /*************************用户地址************************************/
    /**
     * 获取用户的地址列表
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/useraddress")
    Observable<AddressBean> getUserAddress(@Field("token") String token);

    /**
     * 修改用户地址
     *
     * @param id
     * @param name
     * @param tel
     * @param addr
     * @param area
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/updateaddress")
    Observable<BaseBean> changeUserAddress(@Field("id") int id,
                                           @Field("name") String name,
                                           @Field("tel") String tel,
                                           @Field("receivesite") String addr,
                                           @Field("dpareaid") int area,
                                           @Field("token") String token);

    /**
     * 删除用户收货地址
     *
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("pub/deleteaddress")
    Observable<BaseBean> deleteUserAddress(@Field("token") String token, @Field("id") int id);

    /**
     * 设置默认的收货地址
     *
     * @param token
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("pub/updateaddressstate")
    Observable<BaseBean> setDefaultUserAddress(@Field("token") String token, @Field("id") int id);


    //    用户所在学校的所以区域
    @FormUrlEncoded
    @POST("pub/dpareainfo")
    Observable<SchoolAreaBean> getSchoolAreaAddress(@Field("token") String token);


    /**
     * 用户添加收货地址
     *
     * @param name
     * @param tel
     * @param receivesite
     * @param dpareaid
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/addaddress")
    Observable<BaseBean> addUserAddress(@Field("name") String name,
                                        @Field("tel") String tel,
                                        @Field("receivesite") String receivesite,
                                        @Field("dpareaid") int dpareaid,
                                        @Field("token") String token);


    /**
     * 快递公司获取
     *
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/express")
    Observable<MailWayWarrper> getMailWay(@Field("token") String token);

    /**
     * 快递订单提交
     *
     * @param userAddressid
     * @param expressname
     * @param expressorders
     * @param remarks
     * @param token
     * @return
     */
    @FormUrlEncoded
    @POST("pub/submitOrder")
    Observable<PostMailBean> postMailOrder(@Field("userAddressid") int userAddressid,
                                           @Field("expressname") String expressname,
                                           @Field("expressorders") String[] expressorders,
                                           @Field("remarks") String remarks,
                                           @Field("token") String token);


    /**
     * 支付宝获取签名
     *
     * @return
     */
    @FormUrlEncoded
    @POST("pub/alipay")
    Observable<SignBean> getZfbSign(@Field("token") String token,
                                 @Field("orderId") String orderId);


    /**
     * 支付宝获取签名
     *
     * @return
     */
    @FormUrlEncoded
    @POST("pub/wxPrePay")
    Observable<SignWxBean> getWxSign(@Field("token") String token,
                                     @Field("orderId") String orderId);

    /**
     * 余额支付接口
     * @param token
     * @param orderId
     * @return
     */
    @FormUrlEncoded
    @POST("pub/payOrderIntegral")
    Observable<BaseBean> payByYu_e(@Field("token") String token,
                                     @Field("orderid") int orderId);

    /**
     * 成为代理人
     * @param schoolid
     * @param intSlyear
     * @param name
     * @param tel
     * @param qq
     * @param position
     * @return
     */
    @FormUrlEncoded
    @POST("applyAgent")
    Observable<BaseBean> toBeAgent(@Field("schoolid") int schoolid,
                                   @Field("intSlyear") String intSlyear,
                                   @Field("name") String name,
                                   @Field("tel") String tel,
                                   @Field("qq") String qq   ,
                                   @Field("position") String position);


    /**
     * 实名认证
     * @param userMaps
     * @return
     */
    @Multipart
    @POST("pub/realNameAuthentication")
    Observable<BaseBean> postCertification(@QueryMap Map<String , String> userMaps,
                                         @Part List<MultipartBody.Part> files);


    /**
     * 修改用户头像
     * @param userMaps
     * @param avatar
     * @return
     */
    @Multipart
    @POST("pub/updateimg")
    Observable<BaseBean> changUserImg(@QueryMap Map<String , String> userMaps,
            @Part MultipartBody.Part avatar);
}