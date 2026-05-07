import java.util.*;

public class Bully {

    static boolean[] alive;
    static int n;
    static int messageCount = 0; // for time complexity

    static void election(int p) {
        System.out.println("\nProcess " + p + " starts election");

        boolean higherAlive = false;

        for (int i = p + 1; i < n; i++) {
            if (alive[i]) {
                higherAlive = true;

                // ELECTION message
                System.out.println("ELECTION: " + p + " -> " + i);
                messageCount++;

                // OK message
                System.out.println("OK: " + i + " -> " + p);
                messageCount++;

                // Higher process takes over
                election(i);
                return;
            }
        }

        // No higher process alive → becomes leader
        System.out.println("\n>>> Process " + p + " becomes COORDINATOR");
        
        // Send COORDINATOR message
        for (int i = 0; i < n; i++) {
            if (i != p && alive[i]) {
                System.out.println("COORDINATOR: " + p + " -> " + i);
                messageCount++;
            }
        }
    }

    static void showStatus() {
        System.out.print("Status: ");
        for (int i = 0; i < n; i++) {
            System.out.print(i + (alive[i] ? "(UP) " : "(DOWN) "));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of processes: ");
        n = sc.nextInt();

        alive = new boolean[n];
        Arrays.fill(alive, true);

        int choice;

        System.out.println("\n========= MENU =========");
        System.out.println("1. UP process");
        System.out.println("2. DOWN process");
        System.out.println("3. ELECT leader");
        System.out.println("4. SHOW STATUS");
        System.out.println("5. EXIT");

        do {
            System.out.print("\nEnter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    System.out.print("Process to UP: ");
                    alive[sc.nextInt()] = true;
                    break;

                case 2:
                    System.out.print("Process to DOWN: ");
                    alive[sc.nextInt()] = false;
                    break;

                case 3:
                    System.out.print("Start election from process: ");
                    int p = sc.nextInt();

                    if (!alive[p]) {
                        System.out.println("Process is DOWN");
                        break;
                    }

                    messageCount = 0; // reset counter
                    election(p);

                    // Time complexity display
                    System.out.println("\nTotal messages exchanged = " + messageCount);
                    System.out.println("Time Complexity (Worst Case) ≈ O(n^2)");

                    break;

                case 4:
                    showStatus();
                    break;

                case 5:
                    System.out.println("Exit");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 5);
    }
}