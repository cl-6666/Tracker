package com.cl.tracker_cl.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 项目：Tracker
 * 版权：蒲公英公司 版权所有
 * 作者：Arry
 * 版本：1.0
 * 创建日期：2019-06-21
 * 描述：
 * 修订历史：
 */
public class ThreadPoolManager {

    //1.将请求任务放到请求队列中
    //通过阻塞式队列来存储任务
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    //添加任务
    public void execute(Runnable runnable) {
        if (runnable != null) {
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //2.把队列中的任务放到线程池
    private ThreadPoolExecutor threadPoolExecutor;

    private ThreadPoolManager() {
        threadPoolExecutor = new ThreadPoolExecutor(4, 20, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), rejectedExecutionHandler);
        //运行线程池
        threadPoolExecutor.execute(runnable);
    }

    //当线程数超过maxPoolSize或者keep-alive时间超时时执行拒绝策略
    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        /**
         * @param runnable 超时被线程池抛弃的线程
         * @param threadPoolExecutor
         */
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            //将该线程重新放入请求队列中
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    };
    //3.让他们开始工作起来
    //整个的工作线程
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                Runnable runnable = null;
                //不断从从请求队列中取出请求
                try {
                    runnable = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //如果不为空，放入线程池中执行
                if (runnable != null) {
                    threadPoolExecutor.execute(runnable);
                }
            }

        }
    };

    //单例模式
    private static ThreadPoolManager singleInstance = new ThreadPoolManager();

    public static ThreadPoolManager getSingleIntance() {
        return singleInstance;
    }

}
