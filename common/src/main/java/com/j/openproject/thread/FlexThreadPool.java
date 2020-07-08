package com.j.openproject.thread;

import java.util.concurrent.*;

/**
 * @author Joyuce
 * @Type FlexThreadPool
 * @Desc 弹性线程池
 * @date 2019年11月24日
 * @Version V1.0
 */
public class FlexThreadPool extends ThreadPoolExecutor {

    /**
     * 贪心线程池
     */
    public FlexThreadPool(int maxSize) {
        super(maxSize, maxSize, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        super.allowCoreThreadTimeOut(true);
    }

    public FlexThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        super.allowCoreThreadTimeOut(true);
    }

    public FlexThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        super.allowCoreThreadTimeOut(true);
    }

    public FlexThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        super.allowCoreThreadTimeOut(true);
    }

    public FlexThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        super.allowCoreThreadTimeOut(true);
    }
}
