package PriorityScheduling;
import java.util.*;

class Process {
    int at, bt, pr, pno;

    Process(int pno, int at, int bt, int pr) {
        this.pno = pno;
        this.at = at;
        this.bt = bt;
        this.pr = pr;
    }
}

public class PriorityScheduling {
    static int totalprocess;
    static Process[] proc;

    static void get_wt_time(int[] wt) {
        int[] service = new int[totalprocess];
        service[0] = proc[0].at;
        wt[0] = 0;

        for (int i = 1; i < totalprocess; i++) {
            service[i] = proc[i - 1].bt + service[i - 1];
            wt[i] = service[i] - proc[i].at;
            if (wt[i] < 0) {
                wt[i] = 0;
            }
        }
    }

    static void get_tat_time(int[] tat, int[] wt) {
        for (int i = 0; i < totalprocess; i++) {
            tat[i] = proc[i].bt + wt[i];
        }
    }

    static void findgc() {
        int[] wt = new int[totalprocess];
        int[] tat = new int[totalprocess];
        double wavg = 0, tavg = 0;

        get_wt_time(wt);
        get_tat_time(tat, wt);

        int[] stime = new int[totalprocess];
        int[] ctime = new int[totalprocess];

        stime[0] = proc[0].at;
        ctime[0] = stime[0] + tat[0];

        for (int i = 1; i < totalprocess; i++) {
            stime[i] = ctime[i - 1];
            ctime[i] = stime[i] + tat[i] - wt[i];
        }

        System.out.println("Process_no\tStart_time\tComplete_time\tTurn_Around_Time\tWaiting_Time");

        for (int i = 0; i < totalprocess; i++) {
            wavg += wt[i];
            tavg += tat[i];

            System.out.println(proc[i].pno + "\t\t" + stime[i] + "\t\t" + ctime[i] + "\t\t" + tat[i] + "\t\t\t" + wt[i]);
        }

        System.out.println("Average waiting time is : " + wavg / totalprocess);
        System.out.println("Average turnaround time : " + tavg / totalprocess);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("Enter the number of processes: ");
            totalprocess = scanner.nextInt();
            proc = new Process[totalprocess];

            for (int i = 0; i < totalprocess; i++) {
                System.out.println("Enter details for process " + (i + 1) + ": ");
                System.out.print("Arrival Time: ");
                int at = scanner.nextInt();
                System.out.print("Burst Time: ");
                int bt = scanner.nextInt();
                System.out.print("Priority: ");
                int pr = scanner.nextInt();

                proc[i] = new Process(i + 1, at, bt, pr);
            }

            Arrays.sort(proc, (a, b) -> {
                if (a.at == b.at) {
                    return a.pr - b.pr;
                } else {
                    return a.at - b.at;
                }
            });

            findgc();

            System.out.print("Do you want to continue? (yes/no): ");
            scanner.nextLine(); // Consume newline
            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("no")) {
                System.out.println("Exiting the program.");
                break;
            }
        }

        scanner.close();
    }
}
