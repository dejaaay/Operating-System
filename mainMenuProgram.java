import java.util.Scanner;
import RoundRobin.RoundRobinScheduling;
import SSTF.SSTF;


// Main class to run the Main Menu program
public class mainMenuProgram {
    public static void main(String[] args) {
        mainMenu mainMenu = new mainMenu();
        mainMenu.executeMenu();  // Start execution of the program
    }
}




class mainMenu {
    public void executeMenu() {

        Scanner scanner = new Scanner(System.in);

        // Prompt user to choose the scenario (same or different arrival time)
        System.out.println("Choose a Scheduling Algorithm to Run: \n(1) Shortest Seek Time First (SSTF)\n(2) Round Robin\n(3) Priority Scheduling (Non-Preemptive)");
        int userChoice = scanner.nextInt();  // Read user choice


            //switch case
            switch(userChoice) {
                case 1:     // (1) Shortest Seek Time First (SSTF)
                  // code block
                  break;

                case 2:     // (2) Round Robin
                    System.out.println("Executing Round Robin Scheduling...");
                    RoundRobinScheduling roundRobinProgram = new RoundRobinScheduling();
                    roundRobinProgram.execute();  // Start execution of the program
                  break;

                case 3:     // (3) Round Robin
                  // code block
                  break;

                default:    // Invalid choice
                System.out.println("Invalid choice. Please restart the program.");
            }
        scanner.close();  // Close the scanner after use

    }
}