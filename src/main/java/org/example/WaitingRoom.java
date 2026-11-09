package org.example;

import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {
    private final Queue<Patient> patients;

    private final Semaphore emptySlots;
    private final Semaphore fullSlots;
    private final Semaphore mutex;

    private int currentPatients;
    private final int capacity;

    public WaitingRoom(int capacity) {
        this.capacity = capacity;
        currentPatients = 0;
        patients = new LinkedList<>();
        emptySlots = new Semaphore(capacity);
        fullSlots = new Semaphore(0);
        mutex = new Semaphore(1);
    }

    public void addPatient(Patient patient) throws InterruptedException {
        emptySlots.acquire();
        mutex.acquire();
        try {
            patients.add(patient);
            currentPatients++;
            System.out.println("P" + patient.getPatientId() + " enter the waiting room.");
        }
        finally {
            mutex.release();
        }
        fullSlots.release();
    }
    public Patient removePatient() throws InterruptedException{
        Patient p;
        fullSlots.acquire();
        mutex.acquire();
        try {
           p = patients.poll();
           currentPatients--;
            System.out.println("P" + p.getPatientId() + " left the waiting room.");
        } finally {
            mutex.release();
        }
        emptySlots.release();

        return p;
    }

    public boolean isFull() throws InterruptedException {
        mutex.acquire();
        try {
            return currentPatients == capacity;
        } finally {
            mutex.release();
        }
    }

}
