package com.dtdream.DtRecommender.server.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2016/8/18.
 */
public class ThreadPoolUtils {
    private static final int THREAD_NUM = 3;
    private static ThreadPoolUtils init; // 全局单例

    private ExecutorService fixedThreadPool;

    private ThreadPoolUtils() {
        fixedThreadPool = Executors.newFixedThreadPool(THREAD_NUM);
    }

    public static ThreadPoolUtils getInit() {
        if (null == init) {
            init = new ThreadPoolUtils();
        }

        return init;
    }

    public void execute(Runnable r) {
        fixedThreadPool.execute(r);
    }
}
