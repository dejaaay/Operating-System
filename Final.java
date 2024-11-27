import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Final {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Disk Scheduling Algorithms");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Set background color of the frame
        frame.getContentPane().setBackground(new Color(240, 248, 255)); // Light blue background

        // Title and Introduction Panel
        JPanel introPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("ICS26012 Final Project", JLabel.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setForeground(new Color(22, 160, 133)); // Blue text color

        JLabel introLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h2>Welcome user!</h2>"
                + "This program allows you to explore three different disk scheduling algorithms: "
                + "Shortest Seek Time First (SSTF), Round Robin, and Priority Scheduling (Non-Preempt). "
                + "Click on any button below to begin!</div></html>", JLabel.CENTER);
        introLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        introLabel.setForeground(new Color(0, 0, 0)); // Black text color

        introPanel.add(titleLabel, BorderLayout.NORTH);
        introPanel.add(introLabel, BorderLayout.CENTER);
        frame.add(introPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 5, 5)); // Adjust spacing for smaller buttons

        // Create buttons with custom colors
        JButton sstfButton = new JButton("Shortest Seek Time First (SSTF)");
        sstfButton.setBackground(new Color(208, 211, 212)); // Tomato red
        sstfButton.setOpaque(true); // Ensure the background color is visible
        sstfButton.setBorderPainted(false); // Optional: Hides the border for a cleaner look
        sstfButton.setForeground(Color.black);

        JButton roundRobinButton = new JButton("Round Robin");
        roundRobinButton.setBackground(new Color(208, 211, 212)); // Tomato red
        roundRobinButton.setOpaque(true); // Ensure the background color is visible
        roundRobinButton.setBorderPainted(false); // Optional: Hides the border for a cleaner look
                roundRobinButton.setForeground(Color.black);

        JButton priorityButton = new JButton("Priority Scheduling (Non-Preempt)");
        priorityButton.setBackground(new Color(208, 211, 212)); // Tomato red
        priorityButton.setOpaque(true); // Ensure the background color is visible
        priorityButton.setBorderPainted(false); // Optional: Hides the border for a cleaner look
                priorityButton.setForeground(Color.black);
        buttonPanel.add(sstfButton);
        buttonPanel.add(roundRobinButton);
        buttonPanel.add(priorityButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        // Add Action Listeners to Buttons
        sstfButton.addActionListener(e -> runSSTF());
        roundRobinButton.addActionListener(e -> runRoundRobin());
        priorityButton.addActionListener(e -> runPriorityScheduling());

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
            int[] remainingBurstTimes = new int[numProcesses]; // To track remaining burst times for each process
    
            for (int i = 0; i < numProcesses; i++) {
                arrivalTimes[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter Arrival Time for Process " + (i + 1) + ":"));
                burstTimes[i] = Integer.parseInt(JOptionPane.showInputDialog("Enter Burst Time for Process " + (i + 1) + ":"));
                remainingBurstTimes[i] = burstTimes[i]; // Initialize remaining burst times to the original burst times
            }
    
            int quantum = Integer.parseInt(JOptionPane.showInputDialog("Enter Time Quantum:"));
            calculateRoundRobin(numProcesses, burstTimes, arrivalTimes, remainingBurstTimes, quantum);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter numeric values.");
        }
    }
    
    private static void calculateRoundRobin(int numProcesses, int[] burstTimes, int[] arrivalTimes, int[] remainingBurstTimes, int quantum) {
        int[] completionTime = new int[numProcesses];
        int[] turnaroundTime = new int[numProcesses];
        int[] waitingTime = new int[numProcesses];
        int currentTime = 0;
        boolean[] isCompleted = new boolean[numProcesses];
        int completedProcesses = 0;
    
        // Round Robin Execution Loop
        while (completedProcesses < numProcesses) {
            for (int i = 0; i < numProcesses; i++) {
                if (!isCompleted[i] && remainingBurstTimes[i] > 0) {
                    if (remainingBurstTimes[i] > quantum) {
                        currentTime += quantum;
                        remainingBurstTimes[i] -= quantum;
                    } else {
                        currentTime += remainingBurstTimes[i];
                        completionTime[i] = currentTime;
                        remainingBurstTimes[i] = 0;
                        isCompleted[i] = true;
                        completedProcesses++;
                    }
                }
            }
        }
    
        // Calculate turnaround time and waiting time
        for (int i = 0; i < numProcesses; i++) {
            turnaroundTime[i] = completionTime[i] - arrivalTimes[i];
            waitingTime[i] = turnaroundTime[i] - burstTimes[i];
        }
    
        // Prepare the HTML table for results
        StringBuilder result = new StringBuilder();
        result.append("<html><table border='1' style='border-collapse: collapse;'>");
        result.append("<tr><th>Process</th><th>Arrival</th><th>Burst</th><th>Completion</th><th>Turnaround</th><th>Waiting</th></tr>");
    
        // Add rows for each process
        for (int i = 0; i < numProcesses; i++) {
            result.append("<tr>");
            result.append("<td>").append(i + 1).append("</td>");
            result.append("<td>").append(arrivalTimes[i]).append("</td>");
            result.append("<td>").append(burstTimes[i]).append("</td>");
            result.append("<td>").append(completionTime[i]).append("</td>");
            result.append("<td>").append(turnaroundTime[i]).append("</td>");
            result.append("<td>").append(waitingTime[i]).append("</td>");
            result.append("</tr>");
        }
    
        // Calculate average turnaround and waiting time
        double avgTurnaroundTime = Arrays.stream(turnaroundTime).average().orElse(0);
        double avgWaitingTime = Arrays.stream(waitingTime).average().orElse(0);
    
        result.append("</table>");
        result.append("<br><br><b>Average Turnaround Time:</b> ").append(String.format("%.2f", avgTurnaroundTime));
        result.append("<br><b>Average Waiting Time:</b> ").append(String.format("%.2f", avgWaitingTime));
        result.append("</html>");
    
        // Display results in a dialog
        JOptionPane.showMessageDialog(null, result.toString(), "Round Robin Scheduling Results", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Priority Scheduling Implementation
    private static void runPriorityScheduling() {
        try {
            String numProcessesInput = JOptionPane.showInputDialog("Enter the number of processes:");
            if (numProcessesInput == null) return;

            int numProcesses = Integer.parseInt(numProcessesInput);
            Process[] processes = new Process[numProcesses];

            for (int i = 0; i < numProcesses; i++) {
                String arrivalTimeInput = JOptionPane.showInputDialog("Enter Arrival Time for Process " + (i + 1) + ":");
                if (arrivalTimeInput == null) return;

                int at = Integer.parseInt(arrivalTimeInput);

                String burstTimeInput = JOptionPane.showInputDialog("Enter Burst Time for Process " + (i + 1) + ":");
                if (burstTimeInput == null) return;

                int bt = Integer.parseInt(burstTimeInput);

                String priorityInput = JOptionPane.showInputDialog("Enter Priority for Process " + (i + 1) + ":");
                if (priorityInput == null) return;

                int pr = Integer.parseInt(priorityInput);

                processes[i] = new Process(i + 1, at, bt, pr);
            }

            Arrays.sort(processes, Comparator.comparingInt(p -> p.pr)); // Sort by priority
            findPrioritySchedule(processes);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter numeric values.");
        }
    }

    private static void findPrioritySchedule(Process[] processes) {
        int n = processes.length;
    
        // Arrays for storing completion, turnaround, and waiting times
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];
    
        // Calculate completion time
        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (currentTime < processes[i].at) {
                currentTime = processes[i].at; // Wait for the process to arrive
            }
            currentTime += processes[i].bt; // Execute the process
            completionTime[i] = currentTime;
        }
    
        // Calculate turnaround time and waiting time
        for (int i = 0; i < n; i++) {
            turnaroundTime[i] = completionTime[i] - processes[i].at;
            waitingTime[i] = turnaroundTime[i] - processes[i].bt;
        }
    
        // Prepare the HTML table for results
        StringBuilder result = new StringBuilder();
        result.append("<html><table border='1' style='border-collapse: collapse;'>");
        result.append("<tr><th>Process</th><th>Arrival</th><th>Burst</th><th>Priority</th><th>Turnaround</th><th>Waiting</th></tr>");
    
        // Add rows for each process
        for (int i = 0; i < n; i++) {
            result.append("<tr>");
            result.append("<td>").append(processes[i].pno).append("</td>");
            result.append("<td>").append(processes[i].at).append("</td>");
            result.append("<td>").append(processes[i].bt).append("</td>");
            result.append("<td>").append(processes[i].pr).append("</td>");
            result.append("<td>").append(turnaroundTime[i]).append("</td>");
            result.append("<td>").append(waitingTime[i]).append("</td>");
            result.append("</tr>");
        }
    
        // Calculate average turnaround and waiting time
        double avgTurnaroundTime = Arrays.stream(turnaroundTime).average().orElse(0);
        double avgWaitingTime = Arrays.stream(waitingTime).average().orElse(0);
    
        result.append("</table>");
        result.append("<br><br><b>Average Turnaround Time:</b> ").append(String.format("%.2f", avgTurnaroundTime));
        result.append("<br><b>Average Waiting Time:</b> ").append(String.format("%.2f", avgWaitingTime));
        result.append("</html>");
    
        // Display results in a dialog
        JOptionPane.showMessageDialog(null, result.toString(), "Priority Scheduling Results", JOptionPane.INFORMATION_MESSAGE);
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


