package com.minmin.minoj.judge.sandbox;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {
    static volatile boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
//        testCountDownLatch();
//        testVolatile();
        testThreadPoolExecutor();

    }

    public static void testCountDownLatch() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + "end" + countDownLatch.getCount());
        }, "t1").start();
        new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + "end" + countDownLatch.getCount());
        }, "t2").start();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            countDownLatch.countDown();
            System.out.println(Thread.currentThread().getName() + "end" + countDownLatch.getCount());
        }, "t3").start();
        System.out.println("waiting");
        countDownLatch.await();
        System.out.println("潮水啊，我已归来!");
    }

    public static void testVolatile() {
        new Thread(() -> {
            while (!flag) {

            }
            System.out.println("hello");
        }).start();

        new Thread(() -> {
            flag = true;
        }).start();
    }

    public static void testThreadPoolExecutor() {
        LinkedBlockingDeque<Runnable> queue = new LinkedBlockingDeque<>(2);
        AtomicInteger count = new AtomicInteger(0);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 3, 2, TimeUnit.SECONDS, queue, new ThreadFactory() {
            @Override
            public Thread newThread(@NotNull Runnable r) {
                return new Thread(r, "t" + count.getAndIncrement());
            }
        }, new ThreadPoolExecutor.AbortPolicy());

        executor.submit(new MyRunnable());
        executor.submit(new MyRunnable());
        executor.submit(new MyRunnable());
        executor.submit(new MyRunnable());
        executor.submit(new MyRunnable());
        executor.submit(new MyRunnable());
    }

    static class MyRunnable implements Runnable{
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(36000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
