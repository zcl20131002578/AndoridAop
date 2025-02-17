package com.xuexuan.androidaop.patten;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://cloud.tencent.com/developer/article/1777705?from=article.detail.1351237
 */
public class ProducerAndConsumer {

    /**
     * notify Wakes up a single thread that is waiting on this object's monitor.
     * wait   Causes the current thread to wait until another thread invokes the notify method for this object
     */

    private final static int MAX_LEN = 10;
    private final Queue<Integer> queue = new LinkedList<>();

    class Producer extends Thread {
        @Override
        public void run() {
            producer();
        }
        private void producer() {
            while(true) {
                synchronized (queue) {
                    while (queue.size() == MAX_LEN) {
                        queue.notify();
                        System.out.println("当前队列满");
                        try {
                            System.out.println("Producer call wait before");
                            queue.wait();
                            System.out.println("Producer call wait after");
                        } catch (InterruptedException e) {
                            Thread.interrupted();
                        }
                    }
                    queue.add(1);
                    queue.notify();
                    System.out.println("生产者生产一条任务，当前队列长度为" + queue.size());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    class Consumer extends Thread {
        @Override
        public void run() {
            consumer();
        }
        private void consumer() {
            while (true) {
                synchronized (queue) {
                    while (queue.size() == 0) {
                        queue.notify();
                        System.out.println("当前队列为空");
                        try {
                            queue.wait();
                        } catch (InterruptedException e) {
                            Thread.interrupted();
                        }
                    }
                    queue.poll();
                    queue.notify();
                    System.out.println("消费者消费一条任务，当前队列长度为" + queue.size());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main() {
        ProducerAndConsumer pc = new ProducerAndConsumer();
        Producer producer = pc.new Producer();
        Consumer consumer = pc.new Consumer();
        producer.start();
        consumer.start();
    }
}
