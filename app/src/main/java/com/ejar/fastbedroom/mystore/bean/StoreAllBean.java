package com.ejar.fastbedroom.mystore.bean;

/**
 * Created by json on 2017/9/8.
 */

public class StoreAllBean {
    private BannerBean banner;
    private RecommendBean recommend;
    private TabCenterBean tabCenter;

    public BannerBean getBanner() {
        return banner;
    }

    public void setBanner(BannerBean banner) {
        this.banner = banner;
    }

    public RecommendBean getRecommend() {
        return recommend;
    }

    public void setRecommend(RecommendBean recommend) {
        this.recommend = recommend;
    }

    public TabCenterBean getTabCenter() {
        return tabCenter;
    }

    public void setTabCenter(TabCenterBean tabCenter) {
        this.tabCenter = tabCenter;
    }
}
