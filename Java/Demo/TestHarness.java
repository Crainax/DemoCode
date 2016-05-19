package com.crainax.concurrency;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Crainax on 2016/5/19.
 */
public class TestHarness {

    //测试线程中用时最大值
    private final static int SLEEP_TIME_BOUND = 5000;
    //测试线程总数
    private static final int TASK_TEST = 5;

    public static long timeTasks(int nThreads, final Runnable task) throws InterruptedException {

        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {

                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            //无论如何都要让endGate计数减一,以防后面发生死锁现象.
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }


            };
            t.start();
        }


        long startTime = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long endTime = System.nanoTime();

        //返回的时间是纳米,故除10^6,获取微秒.
        return (endTime - startTime) / 1000000;

    }

    public static void main(String[] args) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //随机睡眠一段时间
                int time = new Random().nextInt(SLEEP_TIME_BOUND);
                System.out.println(Thread.currentThread() + " will sleep:" + time + "ms");
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    //恢复中断,run方法中不能抛出异常.故可以使用这种方法通知上层.
                    Thread.currentThread().interrupt();
                }
            }
        };

        try {
            long totalTime = timeTasks(TASK_TEST, runnable);
            System.out.println("total time:" + totalTime);
        } catch (InterruptedException e) {
            System.out.println("Something wrong!");
        }

    }

}
