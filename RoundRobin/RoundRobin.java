package RoundRobin;
import java.util.*;

// Main class to run the Round Robin Scheduling program
public class RoundRobin {
    public static void main(String[] args) {
        RoundRobinScheduling roundRobinProgram = new RoundRobinScheduling();
        roundRobinProgram.execute();  // Start execution of the program
    }
}

class RoundRobinScheduling {

    // Method that controls the execution of the program, where the user chooses the scenario
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        char repeat;

        do {
            System.out.println("Choose a scenario: \n(1) Same arrival time,\n(2) Different arrival time\n");
            int userChoice = scanner.nextInt();

            switch (userChoice) {
                case 1: {
                    System.out.println("\nExecuting Round Robin Scheduling: Same Arrival Times\n");
                    System.out.print("Enter the number of processes: ");
                    int numProcesses = scanner.nextInt();
                    int[] burstTimes = new int[numProcesses];

                    for (int i = 0; i < numProcesses; i++) {
                        System.out.print("Enter Burst Time for Process" + (i + 1) + ": ");
                        burstTimes[i] = scanner.nextInt();
                    }

                    System.out.print("Enter Time Quantum: ");
                    int quantumTime = scanner.nextInt();
                    calculateAverageTime(numProcesses, burstTimes, null, quantumTime);
                    break;
                }
                case 2: {
                    System.out.println("\nExecuting Round Robin Scheduling: Different Arrival Times\n");
                    System.out.print("Enter the number of processes: ");
                    int numProcesses = scanner.nextInt();
                    int[] burstTimes = new int[numProcesses];
                    int[] arrivalTimes = new int[numProcesses];

                    for (int i = 0; i < numProcesses; i++) {
                        System.out.print("Enter Arrival Time for Process" + (i + 1) + ": ");
                        arrivalTimes[i] = scanner.nextInt();
                        System.out.print("Enter Burst Time for Process" + (i + 1) + ": ");
                        burstTimes[i] = scanner.nextInt();
                    }

                    System.out.print("Enter Time Quantum: ");
                    int quantumTime = scanner.nextInt();
                    calculateAverageTime(numProcesses, burstTimes, arrivalTimes, quantumTime);
                    break;
                }
                default:
                    System.out.println("Invalid choice. Please restart the program.");
            }

            System.out.print("\nDo you want to input again? [y/n]: ");
            repeat = scanner.next().toLowerCase().charAt(0);  // Convert input to lowercase for consistency
        } while (repeat == 'y');

        System.out.println("Program terminated.");
        scanner.close();
    }



    // METHODS ------------------------------------------------------------------------------------------------------

    // Method to calculate and display the average waiting and turnaround times
    static void calculateAverageTime(int numProcesses, int[] burstTimes, int[] arrivalTimes, int quantumTime) {
        int[] waitingTimes = new int[numProcesses];  // Array to store waiting times
        int[] turnaroundTimes = new int[numProcesses];  // Array to store turnaround times
        int totalWaitingTime = 0;
        int totalTurnaroundTime = 0;

        if (arrivalTimes == null) {
            // If arrival times are null, it means it's the same arrival time scenario
            calculateWaitingTimeSame(numProcesses, burstTimes, waitingTimes, quantumTime);
        } else {
            // If arrival times are provided, it's the different arrival time scenario
            calculateWaitingTimeDifferent(numProcesses, burstTimes, arrivalTimes, waitingTimes, quantumTime);
        }

        // Calculate turnaround times
        for (int i = 0; i < numProcesses; i++) {
            turnaroundTimes[i] = burstTimes[i] + waitingTimes[i];  // Turnaround = Burst + Waiting
            totalWaitingTime += waitingTimes[i];  // Accumulate total waiting time
            totalTurnaroundTime += turnaroundTimes[i];  // Accumulate total turnaround time
        }

        // Display results
        System.out.println("\nProcess\tArrival Time\tBurst Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Process" + (i + 1) + "\t" +
                    (arrivalTimes == null ? "0" : arrivalTimes[i]) + "\t\t" +
                    burstTimes[i] + "\t\t" +
                    waitingTimes[i] + "\t\t" +
                    turnaroundTimes[i]);
        }

        // Display averages
        System.out.println("\nAverage Waiting Time: " + (float) totalWaitingTime / numProcesses);
        System.out.println("Average Turnaround Time: " + (float) totalTurnaroundTime / numProcesses);
    }

    // Method to calculate waiting times for the case where all processes have the same arrival time
    static void calculateWaitingTimeSame(int numProcesses, int[] burstTimes, int[] waitingTimes, int quantumTime) {
        int[] remainingBurstTimes = Arrays.copyOf(burstTimes, numProcesses);  // Copy of burst times to track remaining time
        int currentTime = 0;  // Keeps track of the total elapsed time

        while (true) {
            boolean done = true;  // Flag to check if all processes are done

            // Process each process in a round-robin fashion
            for (int i = 0; i < numProcesses; i++) {
                if (remainingBurstTimes[i] > 0) {  // If process still has remaining burst time
                    done = false;

                    if (remainingBurstTimes[i] > quantumTime) {
                        currentTime += quantumTime;  // Add time quantum to current time
                        remainingBurstTimes[i] -= quantumTime;  // Reduce the remaining burst time
                    } else {
                        currentTime += remainingBurstTimes[i];  // Add remaining time to current time
                        waitingTimes[i] = currentTime - burstTimes[i];  // Calculate waiting time
                        remainingBurstTimes[i] = 0;  // Mark the process as completed
                    }
                }
            }
            if (done) 
            break;  // If all processes are done, exit the loop
        }
    }

    // Method to calculate waiting times for different arrival times
    static void calculateWaitingTimeDifferent(int numProcesses, int[] burstTimes, int[] arrivalTimes, int[] waitingTimes, int quantumTime) {
        int[] remainingBurstTimes = Arrays.copyOf(burstTimes, numProcesses);  // Copy of burst times
        int currentTime = 0;  // Current time
        Queue<Integer> readyQueue = new LinkedList<>();  // Queue to manage ready processes
        boolean[] inQueue = new boolean[numProcesses];  // Array to track if process is in the ready queue
        int completed = 0;  // Counter for completed processes

        while (completed < numProcesses) {
            // Add newly arrived processes to the queue
            for (int i = 0; i < numProcesses; i++) {
                if (arrivalTimes[i] <= currentTime && !inQueue[i] && remainingBurstTimes[i] > 0) {
                    readyQueue.add(i);  // Add the process to the ready queue
                    inQueue[i] = true;  // Mark as in the ready queue
                }
            }

            if (readyQueue.isEmpty()) {
                currentTime++;  // If no process is ready, increment the time
                continue;
            }

            int currentProcess = readyQueue.poll();  // Get the next process from the ready queue
            if (remainingBurstTimes[currentProcess] > quantumTime) {
                currentTime += quantumTime;  // Add the time quantum to current time
                remainingBurstTimes[currentProcess] -= quantumTime;  // Reduce the remaining burst time
            } else {
                currentTime += remainingBurstTimes[currentProcess];  // Add remaining burst time
                waitingTimes[currentProcess] = currentTime - burstTimes[currentProcess] - arrivalTimes[currentProcess];  // Calculate waiting time
                remainingBurstTimes[currentProcess] = 0;  // Mark the process as completed
                completed++;  // Increment the completed process count
            }

            // Re-add process if not finished
            for (int i = 0; i < numProcesses; i++) {
                if (remainingBurstTimes[i] > 0 && arrivalTimes[i] <= currentTime && !inQueue[i]) {
                    readyQueue.add(i);  // Add to queue if it hasn't been added yet
                    inQueue[i] = true;  // Mark as in the queue
                }
            }

            if (remainingBurstTimes[currentProcess] > 0) readyQueue.add(currentProcess);  // If process is not finished, add it back to queue
        }
    }
}
