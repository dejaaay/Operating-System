package SSTF;


// import java.util.Scanner;

// class node {
//     // Represent difference between head position and track number
//     int distance = 0;
//     // True if track has been accessed
//     boolean accessed = false;
// }

// public class SSTF {

//     // Calculates the difference of each track number with the head position
//     public static void calculateDifference(int queue[], int head, node diff[]) {
//         for (int i = 0; i < diff.length; i++)
//             diff[i].distance = Math.abs(queue[i] - head);
//     }

//     // Find unaccessed track which is at the minimum distance from the head
//     public static int findMin(node diff[]) {
//         int index = -1, minimum = Integer.MAX_VALUE;

//         for (int i = 0; i < diff.length; i++) {
//             if (!diff[i].accessed && minimum > diff[i].distance) {
//                 minimum = diff[i].distance;
//                 index = i;
//             }
//         }
//         return index;
//     }

//     public static void shortestSeekTimeFirst(int request[], int head) {
//         if (request.length == 0)
//             return;

//         // Create array of objects of class node
//         node diff[] = new node[request.length];

//         // Initialize array
//         for (int i = 0; i < diff.length; i++)
//             diff[i] = new node();

//         // Count total number of seek operations
//         int seek_count = 0;

//         // Store sequence in which disk access is done
//         int[] seek_sequence = new int[request.length + 1];

//         for (int i = 0; i < request.length; i++) {
//             seek_sequence[i] = head;
//             calculateDifference(request, head, diff);

//             int index = findMin(diff);

//             diff[index].accessed = true;

//             // Increase the total count
//             seek_count += diff[index].distance;

//             // Accessed track is now the new head
//             head = request[index];
//         }

//         // For the last accessed track
//         seek_sequence[seek_sequence.length - 1] = head;

//         System.out.println("Total number of seek operations = " + seek_count);

//         System.out.println("Seek Sequence is:");
//         // Print the sequence
//         for (int i = 0; i < seek_sequence.length; i++)
//             System.out.println(seek_sequence[i]);
//     }

//     public static void main(String[] args) {
//         Scanner scanner = new Scanner(System.in);

//         // Ask the user for inputs
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
//                 i--; // Ask again for a valid input
//             }
//         }

//         shortestSeekTimeFirst(request, head);

//         scanner.close();
//     }
// }

import java.util.Scanner;

class node {
    // Represent difference between head position and track number
    int distance = 0;
    // True if track has been accessed
    boolean accessed = false;
}

public class SSTF {

    // Calculates the difference of each track number with the head position
    public static void calculateDifference(int queue[], int head, node diff[]) {
        for (int i = 0; i < diff.length; i++)
            diff[i].distance = Math.abs(queue[i] - head);
    }

    // Find unaccessed track which is at the minimum distance from the head
    public static int findMin(node diff[]) {
        int index = -1, minimum = Integer.MAX_VALUE;

        for (int i = 0; i < diff.length; i++) {
            if (!diff[i].accessed && minimum > diff[i].distance) {
                minimum = diff[i].distance;
                index = i;
            }
        }
        return index;
    }

    public static void shortestSeekTimeFirst(int request[], int head, int seekRate) {
        if (request.length == 0)
            return;

        // Create array of objects of class node
        node diff[] = new node[request.length];

        // Initialize array
        for (int i = 0; i < diff.length; i++)
            diff[i] = new node();

        // Count total number of seek operations
        int seek_count = 0;

        // Store sequence in which disk access is done
        int[] seek_sequence = new int[request.length + 1];

        for (int i = 0; i < request.length; i++) {
            seek_sequence[i] = head;
            calculateDifference(request, head, diff);

            int index = findMin(diff);

            diff[index].accessed = true;

            // Increase the total count
            seek_count += diff[index].distance;

            // Accessed track is now the new head
            head = request[index];
        }

        // For the last accessed track
        seek_sequence[seek_sequence.length - 1] = head;

        System.out.println("Total number of seek operations = " + seek_count);
        System.out.println("Seek Rate = " + seekRate + " ms per seek");
        System.out.println("Total Seek Time = " + (seek_count * seekRate) + " ms");

        System.out.println("Seek Sequence is:");
        // Print the sequence
        for (int i = 0; i < seek_sequence.length; i++)
            System.out.println(seek_sequence[i]);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char choice;

        // Loop for multiple computations
        do {
            // Ask the user for inputs
            System.out.print("Enter the current head position: ");
            int head = scanner.nextInt();

            System.out.print("Enter the track size: ");
            int trackSize = scanner.nextInt();

            System.out.print("Enter the number of requests (max 10): ");
            int n = scanner.nextInt();
            if (n > 10) {
                System.out.println("Number of requests cannot exceed 10. Setting it to 10.");
                n = 10;
            }

            int[] request = new int[n];
            System.out.println("Enter the requests:");
            for (int i = 0; i < n; i++) {
                System.out.print("Request " + (i + 1) + ": ");
                request[i] = scanner.nextInt();
                if (request[i] >= trackSize) {
                    System.out.println("Invalid request! Request cannot exceed track size.");
                    i--; // Ask again for a valid input
                }
            }

            System.out.print("Enter the seek rate (ms per seek): ");
            int seekRate = scanner.nextInt();

            // Perform SSTF calculation
            shortestSeekTimeFirst(request, head, seekRate);

            // Ask the user if they want to perform another computation
            System.out.print("Do you want to perform another computation? (Y/N): ");
            choice = scanner.next().charAt(0);

        } while (choice == 'Y' || choice == 'y'); // Repeat if 'Y' or 'y' is entered

        System.out.println("Program terminated.");
        scanner.close();
    }
}
