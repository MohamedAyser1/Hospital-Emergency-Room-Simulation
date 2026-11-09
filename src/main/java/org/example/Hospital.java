
package org.example;

import java.util.Scanner;

public class Hospital {

    private final Scanner scanner;

    private WaitingRoom waitingRoom;
    private TreatmentRooms treatmentRooms;
    private Semaphore completedPatients;

    private Doctor[] doctors;
    private Patient[] patients;

    private int capacity;
    private int numberOfDoctors;
    private int numberOfPatients;

    public Hospital() {
        scanner = new Scanner(System.in);
    }

    private void readInput() {
        System.out.print("Enter waiting room capacity: ");
        capacity = scanner.nextInt();

        System.out.print("Enter number of doctors: ");
        numberOfDoctors = scanner.nextInt();

        System.out.print("Enter number of patients: ");
        numberOfPatients = scanner.nextInt();
    }

    private void initializeResources() {
        waitingRoom = new WaitingRoom(capacity);
        treatmentRooms = new TreatmentRooms();
        completedPatients = new Semaphore(0);
    }

    private void createDoctors() {
        doctors = new Doctor[numberOfDoctors];

        for (int i = 0; i < numberOfDoctors; i++) {
            doctors[i] = new Doctor(
                    i + 1,
                    waitingRoom,
                    treatmentRooms,
                    completedPatients
            );
        }
    }

    private void createPatients() {
        patients = new Patient[numberOfPatients];

        for (int i = 0; i < numberOfPatients; i++) {
            patients[i] = new Patient(i + 1, waitingRoom);
        }
    }

    private void startDoctors() {
        for (Doctor doctor : doctors) {
            doctor.start();
        }
    }

    private void startPatients() {
        for (Patient patient : patients) {
            patient.start();
        }
    }

    private void waitForPatients() throws InterruptedException {
        for (int i = 0; i < numberOfPatients; i++) {
            completedPatients.acquire();
        }

        System.out.println("\nAll patients have been treated.");
    }

    private void stopDoctors() {
        for (Doctor doctor : doctors) {
            doctor.interrupt();
        }
    }

    private void waitForDoctors() throws InterruptedException {
        for (Doctor doctor : doctors) {
            doctor.join();
        }
    }

    private void waitForPatientThreads() throws InterruptedException {
        for (Patient patient : patients) {
            patient.join();
        }
    }

    public void startSimulation() throws InterruptedException {
        readInput();
        initializeResources();
        createDoctors();
        createPatients();
        startDoctors();
        startPatients();
        waitForPatients();
        stopDoctors();
        waitForDoctors();
        waitForPatientThreads();

        System.out.println("Hospital simulation finished.");

        scanner.close();
    }
}
