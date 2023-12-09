package org.example.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ThreadMain {

    private static final Logger log = Logger.getLogger("main logger");
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MyThread mt = new MyThread(i);
            mt.start();
            threads.add(mt);
        }

        for (Thread t : threads) {
            t.join();
        }
        log.info("main end");
        log.info("count : {" + count + "}");
    }

    synchronized static void add() {
        for (int i = 0; i < 100000; i++) {
            count++;
        }
    }

    static class MyThread extends Thread {

        final Logger log;
        final int seq;

        public MyThread(int seq) {
            this.log = Logger.getLogger(seq + " logger");
            this.seq = seq;
        }

        @Override
        public void run() {
            log.info(seq + " start");

            add();

            log.info(seq + " end");
        }
    }
}
