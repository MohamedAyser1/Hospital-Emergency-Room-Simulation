package org.example;

import java.sql.Timestamp;

public class Semaphore {
    private int permits;

    public Semaphore(int permits){
        if (permits < 0) {
            throw new IllegalArgumentException("Permits cannot be negative");
        }
        this.permits = permits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits == 0){
            wait();
        }
        permits--;
    }

    public synchronized void release() {
        permits++;
        notifyAll();
    }

    public synchronized boolean tryAcquire(long timeout) throws InterruptedException {

        long startTime = System.nanoTime();
        timeout = timeout * 1000000;

        while (permits == 0){
        long elapsedTime = System.nanoTime() - startTime;
        long remainingTime = timeout - elapsedTime;

        if(remainingTime<=0)
            return false;

        long millis = remainingTime / 1000000;
        millis = millis > 0 ? millis : 1;
        wait(millis);
        }

        permits--;
        return true;

    }
}
