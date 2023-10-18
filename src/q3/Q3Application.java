package q3;

import java.util.ArrayList;
import java.util.List;


public class Q3Application {
    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                synchronized (list) {
                    list.add((int) (Math.random() * 1000));
                    System.out.println(Thread.currentThread().getName() + " added an item");
                    list.notify();
                }
            }
        });

        Thread consumer = new Thread(() -> {//this thread is going to starve to death! never gets the chance to gain access to the lock!
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            while (list.size() < 100) {
                for (int i = 0; i < 5; i++) {
                    synchronized (list) {

                        list.remove(0);
                        System.out.println(Thread.currentThread().getName() + " removed an item");
                    }
                }
            }
        });
        producer.start();
        consumer.start();

    }
}
