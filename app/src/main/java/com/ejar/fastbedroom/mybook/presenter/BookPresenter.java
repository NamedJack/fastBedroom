package com.ejar.fastbedroom.mybook.presenter;

import android.content.Context;

import com.ejar.fastbedroom.mybook.modle.IBook;

/**
 * Created by json on 2017/8/29.
 */

public class BookPresenter {
    private Context context;
    private IBook iBook;

    public BookPresenter(Context context, IBook iBook) {
        this.context = context;
        this.iBook = iBook;
    }
}
