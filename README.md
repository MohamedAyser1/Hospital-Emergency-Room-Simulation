# Hospital Emergency Room Simulation

This project is a multithreaded simulation of a hospital's emergency room in Java, developed as an Operating Systems assignment. It demonstrates concurrency, resource management, and thread synchronization using custom Semaphores.

## 📌 Project Overview
The simulation models the interaction between Patients, Doctors, a Waiting Room, and Treatment Rooms. 

* **Patients** arrive at the hospital and attempt to enter the Waiting Room. If full, they wait outside.
* **Doctors** take patients from the Waiting Room to the Treatment Rooms.
* There are **2 Treatment Rooms** available. A doctor must acquire one before treating a patient.
* The system ensures that no resources are over-utilized or accessed simultaneously in an unsafe manner using custom-built semaphores.

## 🛠️ Key Components
The project consists of several core classes[cite: 3, 4, 5, 6, 7, 8, 9]:
* `Main.java`: The entry point that initializes and starts the hospital simulation[cite: 5].
* `Hospital.java`: Manages the overall simulation flow, initializes resources, reads user input, and handles the lifecycle of Doctor and Patient threads[cite: 4].
* `Doctor.java`: A Thread that removes patients from the waiting room, acquires a treatment room, treats the patient (simulated with a random delay), and releases resources[cite: 3].
* `Patient.java`: A Thread representing a patient arriving at the hospital and joining the waiting room[cite: 6].
* `WaitingRoom.java`: A thread-safe queue holding patients. It uses semaphores (`emptySlots`, `fullSlots`, `mutex`) to solve the Producer-Consumer problem[cite: 9].
* `TreatmentRooms.java`: Manages the 2 available treatment rooms using a semaphore, ensuring doctors wait if rooms are full[cite: 8].
* `Semaphore.java`: A custom implementation of a counting semaphore with `acquire()`, `release()`, and a custom timeout-based `tryAcquire()` method[cite: 7].

## ⚙️ How to Run
1. Ensure you have Java (JDK) installed on your system.
2. Clone this repository.
3. Compile the Java files.
4. Run the `Main` class.

Upon running, you will be prompted to enter:
1. Waiting room capacity.
2. Number of doctors.
3. Number of patients.

The program will then output the simulation events step-by-step in the console.

## 💻 Technical Highlights
* **Producer-Consumer Pattern:** Implemented in the `WaitingRoom` where Patients are producers and Doctors are consumers[cite: 3, 6, 9].
* **Thread Synchronization:** Custom `Semaphore` class utilizing Java's `wait()`, `notifyAll()`, and `synchronized` blocks to handle concurrent access safely[cite: 7].
* **Timeout Handling:** Doctors use a timeout mechanism (`tryAcquire` in `Semaphore`) when waiting for a treatment room. If they can't get a room in time, the patient goes back to the waiting room[cite: 3, 7].
