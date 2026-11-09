package org.example;

public class TreatmentRooms {
    private final Semaphore semaphore;

    public TreatmentRooms() {
        semaphore = new Semaphore(2);
    }

    public boolean tryAcquire(long timeout) throws InterruptedException {
        return semaphore.tryAcquire(timeout);
    }

    public void release() {
        semaphore.release();
    }
}