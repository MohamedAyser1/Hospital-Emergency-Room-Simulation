package org.example;

import java.util.Random;

public class Doctor extends Thread {
    private final int doctorId;
    private final WaitingRoom waitingRoom;
    private final TreatmentRooms treatingRooms;
    private final Semaphore completedPatients;

    public Doctor(int doctorId, WaitingRoom waitingRoom, TreatmentRooms treatingRooms, Semaphore completedPatients) {
        this.doctorId = doctorId;
        this.waitingRoom = waitingRoom;
        this.treatingRooms = treatingRooms;
        this.completedPatients = completedPatients;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()){

        Patient patient;

        try {
            patient = waitingRoom.removePatient();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        try {
            if (treatingRooms.tryAcquire(3000)) {
                try {
                    System.out.println("Doctor " + doctorId + " acquired a treatment room.");
                    System.out.println("Doctor " + doctorId + " begins treating " + patient.getPatientId() + ".");

                    Random random = new Random();
                    int randomNumber = random.nextInt(5) + 1;
                    sleep(randomNumber * 1000L);

                    System.out.println("Doctor " + doctorId + " finished treating " + patient.getPatientId() + ".");
                    System.out.println("Patient " + patient.getPatientId() + " left hospital.");

                } finally {
                    treatingRooms.release();
                    System.out.println("Doctor " + doctorId + " released the treatment room.");
                }
                completedPatients.release();
            } else {
                waitingRoom.addPatient(patient);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
}
