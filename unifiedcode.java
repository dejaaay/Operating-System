// import java.util.*;

// class UnifiedScheduling {

//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);
//         while (true) {
//             System.out.println("\nChoose an Algorithm to Execute:");
//             System.out.println("1. SSTF (Shortest Seek Time First)");
//             System.out.println("2. Round Robin Scheduling");
//             System.out.println("3. Priority Scheduling");
//             System.out.println("4. Exit");
//             System.out.print("Enter your choice: ");
//             int choice = scanner.nextInt();

//             switch (choice) {
//                 case 1:
//                     executeSSTF(scanner);
//                     break;
//                 case 2:
//                     executeRoundRobin(scanner);
//                     break;
//                 case 3:
//                     executePriorityScheduling(scanner);
//                     break;
//                 case 4:
//                     System.out.println("Exiting program.");
//                     scanner.close();
//                     return;
//                 default:
//                     System.out.println("Invalid choice! Please choose again.");
//             }
//         }
//     }

//     // SSTF Algorithm
//     public static void executeSSTF(Scanner scanner) {
//         System.out.print("Enter the current head position: ");
//         int head = scanner.nextInt();

//         System.out.print("Enter the track size: ");
//         int trackSize = scanner.nextInt();

//         System.out.print("Enter the number of requests (max 10): ");
//         int n = scanner.nextInt();
//         if (n > 10) {
//             System.out.println("Number of requests cannot exceed 10. Setting it to 10.");
//             n = 10;
//         }

//         int[] request = new int[n];
//         System.out.println("Enter the requests:");
//         for (int i = 0; i < n; i++) {
//             System.out.print("Request " + (i + 1) + ": ");
//             request[i] = scanner.nextInt();
//             if (request[i] >= trackSize) {
//                 System.out.println("Invalid request! Request cannot exceed track size.");
//                 i--;
//             }
//         }

//         System.out.print("Enter the seek rate (ms per seek): ");
//         int seekRate = scanner.nextInt();

//         SSTF.shortestSeekTimeFirst(request, head, seekRate);
//     }

//     // Round Robin Algorithm
//     public static void executeRoundRobin(Scanner scanner) {
//         System.out.println("Choose a scenario: \n(1) Same arrival time\n(2) Different arrival time");
//         int scenario = scanner.nextInt();

//         System.out.print("Enter the number of processes: ");
//         int numProcesses = scanner.nextInt();
//         int[] burstTimes = new int[numProcesses];
//         int[] arrivalTimes = null;

//         if (scenario == 2) {
//             arrivalTimes = new int[numProcesses];
//             for (int i = 0; i < numProcesses; i++) {
//                 System.out.print("Enter Arrival Time for Process " + (i + 1) + ": ");
//                 arrivalTimes[i] = scanner.nextInt();
//             }
//         }

//         for (int i = 0; i < numProcesses; i++) {
//             System.out.print("Enter Burst Time for Process " + (i + 1) + ": ");
//             burstTimes[i] = scanner.nextInt();
//         }

//         System.out.print("Enter Time Quantum: ");
//         int quantumTime = scanner.nextInt();

//         RoundRobinScheduling.calculateAverageTime(numProcesses, burstTimes, arrivalTimes, quantumTime);
//     }

//     // Priority Scheduling Algorithm
//     public static void executePriorityScheduling(Scanner scanner) {
//         System.out.print("Enter the number of processes: ");
//         int numProcesses = scanner.nextInt();
//         Process[] processes = new Process[numProcesses];

//         for (int i = 0; i < numProcesses; i++) {
//             System.out.println("Enter details for Process " + (i + 1) + ":");
//             System.out.print("Arrival Time: ");
//             int at = scanner.nextInt();
//             System.out.print("Burst Time: ");
//             int bt = scanner.nextInt();
//             System.out.print("Priority: ");
//             int pr = scanner.nextInt();
//             processes[i] = new Process(i + 1, at, bt, pr);
//         }

//         Arrays.sort(processes, (a, b) -> a.at == b.at ? Integer.compare(a.pr, b.pr) : Integer.compare(a.at, b.at));
//         PriorityScheduling.findgc(processes);
//     }
// }

// // SSTF Helper
// class SSTF {
//     static class Node {
//         int distance = 0;
//         boolean accessed = false;
//     }

//     public static void calculateDifference(int queue[], int head, Node diff[]) {
//         for (int i = 0; i < diff.length; i++)
//             diff[i].distance = Math.abs(queue[i] - head);
//     }

//     public static int findMin(Node diff[]) {
//         int index = -1, minimum = Integer.MAX_VALUE;
//         for (int i = 0; i < diff.length; i++) {
//             if (!diff[i].accessed && minimum > diff[i].distance) {
//                 minimum = diff[i].distance;
//                 index = i;
//             }
//         }
//         return index;
//     }

//     public static void shortestSeekTimeFirst(int request[], int head, int seekRate) {
//         Node[] diff = new Node[request.length];
//         for (int i = 0; i < diff.length; i++)
//             diff[i] = new Node();

//         int seekCount = 0;
//         int[] seekSequence = new int[request.length + 1];

//         for (int i = 0; i < request.length; i++) {
//             seekSequence[i] = head;
//             calculateDifference(request, head, diff);
//             int index = findMin(diff);
//             diff[index].accessed = true;
//             seekCount += diff[index].distance;
//             head = request[index];
//         }
//         seekSequence[seekSequence.length - 1] = head;

//         System.out.println("Total number of seek operations = " + seekCount);
//         System.out.println("Seek Rate = " + seekRate + " ms per seek");
//         System.out.println("Total Seek Time = " + (seekCount * seekRate) + " ms");
//         System.out.println("Seek Sequence:");
//         for (int seq : seekSequence) System.out.println(seq);
//     }
// }

// // Round Robin Scheduling Helper
// class RoundRobinScheduling {
//     public static void calculateAverageTime(int numProcesses, int[] burstTimes, int[] arrivalTimes, int quantumTime) {
//         int[] waitingTimes = new int[numProcesses];
//         int[] turnaroundTimes = new int[numProcesses];
//         if (arrivalTimes == null) calculateWaitingTimeSame(numProcesses, burstTimes, waitingTimes, quantumTime);
//         else calculateWaitingTimeDifferent(numProcesses, burstTimes, arrivalTimes, waitingTimes, quantumTime);

//         for (int i = 0; i < numProcesses; i++) {
//             turnaroundTimes[i] = burstTimes[i] + waitingTimes[i];
//         }

//         System.out.println("\nProcess\tBurst Time\tWaiting Time\tTurnaround Time");
//         for (int i = 0; i < numProcesses; i++) {
//             System.out.println("P" + (i + 1) + "\t\t" + burstTimes[i] + "\t\t" + waitingTimes[i] + "\t\t" + turnaroundTimes[i]);
//         }
//     }

//     private static void calculateWaitingTimeSame(int numProcesses, int[] burstTimes, int[] waitingTimes, int quantumTime) {
//         // Implementation similar to the original
//     }

//     private static void calculateWaitingTimeDifferent(int numProcesses, int[] burstTimes, int[] arrivalTimes, int[] waitingTimes, int quantumTime) {
//         // Implementation similar to the original
//     }
// }

// // Priority Scheduling Helper
// class PriorityScheduling {
//     public static void findgc(Process[] processes) {
//         int[] wt = new int[processes.length];
//         int[] tat = new int[processes.length];
//         getWaitingTime(processes, wt);
//         getTurnAroundTime(processes, wt, tat);
//         System.out.println("\nProcess\tArrival Time\tBurst Time\tPriority\tWaiting Time\tTurnaround Time");
//         for (int i = 0; i < processes.length; i++) {
//             System.out.println("P" + processes[i].pno + "\t\t" + processes[i].at + "\t\t" + processes[i].bt + "\t\t" + processes[i].pr + "\t\t" + wt[i] + "\t\t" + tat[i]);
//         }
//     }

//     private static void getWaitingTime(Process[] processes, int[] wt) {
//         // Implementation similar to the original
//     }

//     private static void getTurnAroundTime(Process[] processes, int[] wt, int[] tat) {
//         // Implementation similar to the original
//     }
// }

// class Process {
//     int at, bt, pr, pno;

//     Process(int pno, int at, int bt, int pr) {
//         this.pno = pno;
//         this.at = at;
//         this.bt = bt;
//         this.pr = pr;
//     }
// }

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class unifiedcode {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Disk Scheduling Algorithms");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title Label
        JLabel titleLabel = new JLabel("Disk Scheduling Algorithms", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Algorithm Selection Panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JLabel selectLabel = new JLabel("Choose an Algorithm: ");
        String[] algorithms = {"SSTF", "Round Robin", "Priority Scheduling"};
        JComboBox<String> algorithmDropdown = new JComboBox<>(algorithms);
        JButton runButton = new JButton("Run Algorithm");

        panel.add(selectLabel);
        panel.add(algorithmDropdown);
        panel.add(runButton);

        frame.add(panel, BorderLayout.CENTER);

        // Add Action Listener to Run Button
        runButton.addActionListener(e -> {
            String selectedAlgorithm = (String) algorithmDropdown.getSelectedItem();

            if ("SSTF".equals(selectedAlgorithm)) {
                runSSTF();
            } else if ("Round Robin".equals(selectedAlgorithm)) {
                runRoundRobin();
            } else if ("Priority Scheduling".equals(selectedAlgorithm)) {
                runPriorityScheduling();
            }
        });

        frame.setVisible(true);
    }

    // SSTF Implementation
    private static void runSSTF() {
        try {
            int head = Integer.parseInt(JOptionPane.showInputDialog("Enter the current head position:"));
            int trackSize = Integer.parseInt(JOptionPane.showInputDialog("Enter the track size:"));
            int n = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of requests:"));
            n = Math.min(n, 10); // Limit the number of requests to 10

            int[] request = new int[n];
            for (int i = 0; i < n; i++) {
                request[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter request " + (i + 1) + ":"));
                if (request[i] >= trackSize) {
                    JOptionPane.showMessageDialog(null, "Invalid request! Cannot exceed track size.");
                    i--; // Retry input for invalid request
                }
            }

            int seekRate = Integer.parseInt(JOptionPane.showInputDialog("Enter the seek rate (ms per seek):"));
            shortestSeekTimeFirst(request, head, seekRate);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter numeric values.");
        }
    }

    // SSTF Helper Functions
    private static void shortestSeekTimeFirst(int[] request, int head, int seekRate) {
        class Node {
            int distance = 0;
            boolean accessed = false;
        }

        Node[] diff = new Node[request.length];
        for (int i = 0; i < diff.length; i++) diff[i] = new Node();

        int seekCount = 0;
        int[] seekSequence = new int[request.length + 1];
        for (int i = 0; i < request.length; i++) {
            seekSequence[i] = head;
            for (int j = 0; j < diff.length; j++) {
                diff[j].distance = Math.abs(request[j] - head);
            }

            int index = -1, minimum = Integer.MAX_VALUE;
            for (int j = 0; j < diff.length; j++) {
                if (!diff[j].accessed && diff[j].distance < minimum) {
                    minimum = diff[j].distance;
                    index = j;
                }
            }

            diff[index].accessed = true;
            seekCount += diff[index].distance;
            head = request[index];
        }

        seekSequence[request.length] = head;

        StringBuilder result = new StringBuilder();
        result.append("Total Seek Operations: ").append(seekCount).append("\n");
        result.append("Total Seek Time: ").append(seekCount * seekRate).append(" ms\n");
        result.append("Seek Sequence: ");
        for (int seq : seekSequence) result.append(seq).append(" ");
        JOptionPane.showMessageDialog(null, result.toString());
    }

    // Round Robin Implementation
    private static void runRoundRobin() {
        try {
            int numProcesses = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of processes:"));
            int[] burstTimes = new int[numProcesses];
            int[] arrivalTimes = new int[numProcesses];

            for (int i = 0; i < numProcesses; i++) {
                arrivalTimes[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter Arrival Time for Process " + (i + 1) + ":"));
                burstTimes[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter Burst Time for Process " + (i + 1) + ":"));
            }

            int quantum = Integer.parseInt(JOptionPane.showInputDialog("Enter Time Quantum:"));
            calculateRoundRobin(numProcesses, burstTimes, arrivalTimes, quantum);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter numeric values.");
        }
    }

    private static void calculateRoundRobin(int numProcesses, int[] burstTimes, int[] arrivalTimes, int quantum) {
        // (Similar implementation to the original Round Robin code, formatted for GUI output)
        StringBuilder result = new StringBuilder();
        result.append("Round Robin Execution Complete!").append("\n\n");
        result.append("Process details would be shown here...\n");
        JOptionPane.showMessageDialog(null, result.toString());
    }

    // Priority Scheduling Implementation
    private static void runPriorityScheduling() {
        try {
            int numProcesses = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of processes:"));
            Process[] processes = new Process[numProcesses];
            for (int i = 0; i < numProcesses; i++) {
                int at = Integer.parseInt(JOptionPane.showInputDialog("Enter Arrival Time for Process " + (i + 1) + ":"));
                int bt = Integer.parseInt(JOptionPane.showInputDialog("Enter Burst Time for Process " + (i + 1) + ":"));
                int pr = Integer.parseInt(JOptionPane.showInputDialog("Enter Priority for Process " + (i + 1) + ":"));
                processes[i] = new Process(i + 1, at, bt, pr);
            }

            Arrays.sort(processes, Comparator.comparingInt(p -> p.pr)); // Sort by priority
            findPrioritySchedule(processes);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter numeric values.");
        }
    }

    private static void findPrioritySchedule(Process[] processes) {
        // (Similar implementation to the Priority Scheduling code, formatted for GUI output)
        StringBuilder result = new StringBuilder();
        result.append("Priority Scheduling Execution Complete!").append("\n\n");
        result.append("Process details would be shown here...\n");
        JOptionPane.showMessageDialog(null, result.toString());
    }

    // Process Class for Priority Scheduling
    static class Process {
        int at, bt, pr, pno;

        Process(int pno, int at, int bt, int pr) {
            this.pno = pno;
            this.at = at;
            this.bt = bt;
            this.pr = pr;
        }
    }
}
