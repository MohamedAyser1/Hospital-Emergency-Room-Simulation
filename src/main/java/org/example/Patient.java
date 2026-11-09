package org.example;

public class Patient extends Thread{
    private final int patientId;
    private final WaitingRoom waitingRoom;


    public Patient(int patientId, WaitingRoom waitingRoom) {
        this.patientId = patientId;
        this.waitingRoom = waitingRoom;
    }

    public int getPatientId() {
        return patientId;
    }

    @Override
    public void run() {
        System.out.println("Patient " + patientId + " arrived.");

        try {
            if (waitingRoom.isFull()) {
                System.out.println("The waiting room is full.");
                System.out.println("P" + patientId + " is waiting outside for an available seat.");
            }

            waitingRoom.addPatient(this);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


}
