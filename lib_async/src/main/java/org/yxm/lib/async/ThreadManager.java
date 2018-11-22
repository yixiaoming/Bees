package org.yxm.lib.async;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

/**
 * 线程池管理类
 * 用于App中全局管理thread
 * @author yixiaoming
 */
public final class ThreadManager {

    private static volatile ThreadManager instance;

    private static final int DEFAULT_POOL_SIZE = 8;
    private static final int DEFAULT_PRIORITY = android.os.Process.THREAD_PRIORITY_BACKGROUND;

    private ExecutorService mDefaultExcutor;
    private ExecutorService mIoExecutor;
    private ExecutorService mSingleExecutor;
    private ExecutorService mScheduleExecutor;

    private ThreadManager() {
        ThreadFactory threadFactory = new DefaultThreadFactory();
        mDefaultExcutor = Executors.newFixedThreadPool(DEFAULT_POOL_SIZE, threadFactory);
    }

    public static ThreadManager getInstance() {
        if (instance == null) {
            synchronized (ThreadManager.class) {
                if (instance == null) {
                    return new ThreadManager();
                }
            }
        }
        return instance;
    }

    public void close() {
        if (mDefaultExcutor != null) {
            mDefaultExcutor.shutdownNow();
        }
        if (mIoExecutor != null) {
            mIoExecutor.shutdownNow();
        }
        if (mSingleExecutor != null) {
            mSingleExecutor.shutdownNow();
        }
        if (mScheduleExecutor != null) {
            mScheduleExecutor.shutdownNow();
        }
    }

    public void run(Runnable runnable) {
        mDefaultExcutor.submit(runnable);
    }

    public <T> Future<T> call(Callable<T> callable) {
        return mDefaultExcutor.submit(callable);
    }

    public void runIo(Runnable runnable) {
        initIoExecutor();
        mIoExecutor.submit(runnable);
    }

    public <T> Future<T> callIo(Callable<T> callable) {
        initIoExecutor();
        return mIoExecutor.submit(callable);
    }


    public void runSingle(Runnable runnable) {
        initSingleExecutor();
        mSingleExecutor.submit(runnable);
    }

    public <T> Future<T> callSingle(Callable<T> callable) {
        initSingleExecutor();
        return mSingleExecutor.submit(callable);
    }


    public void runSchedule(Runnable runnable) {
        initScheduleExecutor();
        mScheduleExecutor.submit(runnable);
    }

    public <T> Future<T> callSchedule(Callable<T> callable) {
        initScheduleExecutor();
        return mScheduleExecutor.submit(callable);
    }

    private synchronized void initIoExecutor() {
        if (mIoExecutor == null) {
            mIoExecutor = Executors.newCachedThreadPool();
        }
    }

    private synchronized void initSingleExecutor() {
        if (mSingleExecutor == null) {
            mSingleExecutor = Executors.newSingleThreadExecutor();
        }
    }

    private synchronized void initScheduleExecutor() {
        if (mScheduleExecutor == null) {
            mScheduleExecutor = Executors.newScheduledThreadPool(DEFAULT_POOL_SIZE);
        }
    }

    private static class DefaultThreadFactory implements ThreadFactory {
        private int threadNum;

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable, "Bees-Thread-" + threadNum) {
                @Override
                public void run() {
                    android.os.Process.setThreadPriority(DEFAULT_PRIORITY);
                    super.run();
                }
            };
            ++threadNum;
            return thread;
        }
    }
}
