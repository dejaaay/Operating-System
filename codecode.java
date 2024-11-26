import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

//Perfected code with cancel-close button
public class codecode {

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
        JLabel titleLabel = new JLabel("Disk Scheduling Algorithms", JLabel.CENTER);
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
            String headInput = JOptionPane.showInputDialog("Enter the current head position:");
            if (headInput == null) return;

            int head = Integer.parseInt(headInput);

            String trackSizeInput = JOptionPane.showInputDialog("Enter the track size:");
            if (trackSizeInput == null) return;

            int trackSize = Integer.parseInt(trackSizeInput);

            String nInput = JOptionPane.showInputDialog("Enter the number of requests:");
            if (nInput == null) return;

            int n = Integer.parseInt(nInput);
            n = Math.min(n, 10); // Limit the number of requests to 10

            int[] request = new int[n];
            for (int i = 0; i < n; i++) {
                String requestInput = JOptionPane.showInputDialog("Enter request " + (i + 1) + ":");
                if (requestInput == null) return;

                request[i] = Integer.parseInt(requestInput);
                if (request[i] >= trackSize) {
                    JOptionPane.showMessageDialog(null, "Invalid request! Cannot exceed track size.");
                    i--; // Retry input for invalid request
                }
            }

            String seekRateInput = JOptionPane.showInputDialog("Enter the seek rate (ms per seek):");
            if (seekRateInput == null) return;

            int seekRate = Integer.parseInt(seekRateInput);
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
            String numProcessesInput = JOptionPane.showInputDialog("Enter the number of processes:");
            if (numProcessesInput == null) return;

            int numProcesses = Integer.parseInt(numProcessesInput);

            int[] burstTimes = new int[numProcesses];
            int[] arrivalTimes = new int[numProcesses];

            for (int i = 0; i < numProcesses; i++) {
                String arrivalTimeInput = JOptionPane.showInputDialog("Enter Arrival Time for Process " + (i + 1) + ":");
                if (arrivalTimeInput == null) return;

                arrivalTimes[i] = Integer.parseInt(arrivalTimeInput);

                String burstTimeInput = JOptionPane.showInputDialog("Enter Burst Time for Process " + (i + 1) + ":");
                if (burstTimeInput == null) return;

                burstTimes[i] = Integer.parseInt(burstTimeInput);
            }

            String quantumInput = JOptionPane.showInputDialog("Enter Time Quantum:");
            if (quantumInput == null) return;

            int quantum = Integer.parseInt(quantumInput);
            calculateRoundRobin(numProcesses, burstTimes, arrivalTimes, quantum);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid input! Please enter numeric values.");
        }
    }

    private static void calculateRoundRobin(int numProcesses, int[] burstTimes, int[] arrivalTimes, int quantum) {
        StringBuilder result = new StringBuilder();
        result.append("Round Robin Execution Complete!").append("\n\n");
        result.append("Process details would be shown here...\n");
        JOptionPane.showMessageDialog(null, result.toString());
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
