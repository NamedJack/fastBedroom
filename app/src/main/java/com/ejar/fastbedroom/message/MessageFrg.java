package com.ejar.fastbedroom.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ejar.fastbedroom.utils.AppManager;
import com.ejar.baseframe.base.frg.BaseFragment;
import com.ejar.baseframe.baseAdapter.MyRecyclerViewAdapter;
import com.ejar.baseframe.baseAdapter.MyViewHolder;
import com.ejar.baseframe.utils.net.MyBaseObserver;
import com.ejar.baseframe.utils.net.NetRequest;
import com.ejar.fastbedroom.utils.TU;
import com.ejar.fastbedroom.Api.HomeAtyApi;
import com.ejar.fastbedroom.R;
import com.ejar.fastbedroom.application.APP;
import com.ejar.fastbedroom.config.UrlConfig;
import com.ejar.fastbedroom.databinding.FrgMessageBinding;
import com.ejar.fastbedroom.login.LoginActivity;
import com.ejar.fastbedroom.message.bean.MessageBean;
import com.ejar.fastbedroom.mystore.banner.GlideImageLoader;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by json on 2017/8/22.
 */

public class MessageFrg extends BaseFragment<FrgMessageBinding> {
    //    private Banner banner;
    public int setContent() {
        return R.layout.frg_message;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMessage();
    }

    private void getMessage() {
        NetRequest.getInstance(UrlConfig.baseUrl).create(HomeAtyApi.class)
                .getMessage(APP.token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyBaseObserver<MessageBean>(getContext(), true, "消息获取中...") {
                    @Override
                    public void _doNext(MessageBean messageBean) {
                        if (messageBean.getCode().equals("200")) {
                            initBannerView(messageBean.getData().getImgs());
                            initImgView(messageBean.getData().getPosts());
                        } else if (messageBean.getCode().equals(UrlConfig.logoutCodeOne)) {
                            AppManager.removeAllAty();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            TU.cT(messageBean.getMsg() + "");
                        }

                    }
                });
    }

    private void initImgView(List<MessageBean.DataBean.PostsBean> posts) {
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(getContext(), R.layout.item_message, posts) {
            @Override
            public void convert(MyViewHolder holder, int position) {
                try {
                    ImageView iv = holder.getView(R.id.message_item_rv);
//                Log.e("msg", "png++" + posts.get(position).getTitleimg());
                    String imgUrl = UrlConfig.baseUrl + posts.get(position).getTitleimg();
//                    Log.e("msg", "tupan" + imgUrl);
                    Glide.with(iv.getContext()).load(imgUrl)
                            .placeholder(R.drawable.img_message_default)
                            .into(iv);
                    holder.setOnClickListener(R.id.message_item_rv, v -> {
                        openNextActivity(posts.get(position).getId());
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        bindingView.messageRv.setLayoutManager(new LinearLayoutManager(getContext()));
        bindingView.messageRv.setAdapter(adapter);
    }

    private void openNextActivity(int id) {
        Intent intent = new Intent(getActivity(), MessageWebAty.class);
        intent.putExtra("tag", "list");
        intent.putExtra("webId", id);
        startActivity(intent);
    }

    private void initBannerView(List<MessageBean.DataBean.ImgsBean> imgs) {
        List<String> bannerImg = new ArrayList<>();
        if (imgs == null) {
            return;
        }
        //设置图片加载器
//        bindingView.messageBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);

        //设置图片集合
        for (int i = 0; i < imgs.size(); i++) {
//            Log.e("msg", "tupian" + data.get(i).getImg());
            bannerImg.add(UrlConfig.baseUrl + imgs.get(i).getImg());
        }
        bindingView.messageBanner.setImages(bannerImg);
        bindingView.messageBanner.setImageLoader(new GlideImageLoader());
//        bindingView.messageBanner.setIndicatorGravity(BannerConfig.RIGHT);


        //banner设置方法全部调用完毕时最后调用
        bindingView.messageBanner.start();
        bindingView.messageBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
//                TU.cT(position + "");
                int id =  imgs.get(position).getId();
                String url = imgs.get(position).getUrl();
                Intent intent = new Intent(getActivity(), MessageWebAty.class);
                intent.putExtra("webId", id);
                intent.putExtra("tag", "banner");
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
    }
}
