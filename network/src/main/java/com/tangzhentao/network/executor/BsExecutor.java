package com.tangzhentao.network.executor;

import android.annotation.SuppressLint;
import android.arch.core.executor.ArchTaskExecutor;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:线程类
 *
 * @author tangzhentao
 * @date 2019/5/16
 */
@SuppressLint("RestrictedApi")
public final class BsExecutor {
    private volatile Handler mMainHandler;

    private Executor mNetworkPool;

    private BsExecutor() {
        mMainHandler = new Handler(Looper.getMainLooper());
        mNetworkPool = Executors.newFixedThreadPool(5);
    }

    public static BsExecutor get() {
        return TztExecutorHolder.instance;
    }

    /**
     * run on disk io
     *
     * @param runnable runnable
     */
    public void executeOnDiskIO(@NonNull Runnable runnable) {
        ArchTaskExecutor.getInstance().executeOnDiskIO(runnable);
    }

    /**
     * run on network io
     *
     * @param runnable runnable
     */
    public void executeOnNetworkIO(@NonNull Runnable runnable) {
        mNetworkPool.execute(runnable);
    }

    /**
     * run on main thread
     *
     * @param runnable runnable
     */
    public void runOnUiThread(@NonNull Runnable runnable) {
        mMainHandler.post(runnable);
    }

    /**
     * run on main thread
     *
     * @param runnable runnable
     */
    public void runOnUiThreadDelayed(@NonNull Runnable runnable, long delayMillis) {
        mMainHandler.postDelayed(runnable, delayMillis);
    }

    public void removeUiThreadCallbacks(@NonNull Runnable runnable) {
        mMainHandler.removeCallbacks(runnable);
    }

    /**
     * get main thread executor
     *
     * @return main thread executor
     */
    @NonNull
    public Handler getUiThreadHandler() {
        return mMainHandler;
    }

    /**
     * get disk thread executor
     *
     * @return disk thread executor
     */
    @NonNull
    public Executor getDiskIOThreadExecutor() {
        return ArchTaskExecutor.getIOThreadExecutor();
    }

    /**
     * get network thread executor
     *
     * @return network thread executor
     */
    @NonNull
    public Executor getNetworkIOThreadExecutor() {
        return mNetworkPool;
    }

    /**
     * get queue thread executor
     *
     * @return queue thread executor
     */
    @NonNull
    public ExecutorService getQueueIOThreadExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * is main thread
     *
     * @return true:yes,false:no.
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    private static class TztExecutorHolder{
        private static BsExecutor instance = new BsExecutor();
    }
}
