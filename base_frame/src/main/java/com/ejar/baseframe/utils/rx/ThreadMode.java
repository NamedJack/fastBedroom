package com.ejar.baseframe.utils.rx;

/**
 * Created by json on 2017/8/10.
 */

public enum ThreadMode {
    /**
     * android main thread
     */
    MAIN,

    /**
     * new thread
     */
    NEW_THREAD,


    COMPUTATION,


    IO,


    TRAMPOLINE,


    CURRENT
}
