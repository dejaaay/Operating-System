import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class codecode {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Disk Scheduling Algorithms");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Title and Introduction Panel
        JPanel introPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Disk Scheduling Algorithms", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel introLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<h2>Welcome user!</h2>"
                + "This program allows you to explore three different disk scheduling algorithms: "
                + "Shortest Seek Time First (SSTF), Round Robin, and Priority Scheduling. "
                + "Click on any button below to begin!</div></html>", JLabel.CENTER);
        introLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        introPanel.add(titleLabel, BorderLayout.NORTH);
        introPanel.add(introLabel, BorderLayout.CENTER);
        frame.add(introPanel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); // 3 rows, 1 column, spacing of 10 pixels

        JButton sstfButton = new JButton("SSTF");
        JButton roundRobinButton = new JButton("Round Robin");
        JButton priorityButton = new JButton("Priority Scheduling");

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
