import java.util.*;

public class TokenRing {

    static boolean[] alive;
    static int n;
    static int messageCount = 0;

    static void election(int start) {
        System.out.println("\nProcess " + start + " starts election");

        int i = start;
        List<Integer> participants = new ArrayList<>();

        // ELECTION message circulates in ring
        do {
            if (alive[i]) {
                System.out.println("ELECTION message passed: " + i);
                participants.add(i);
                messageCount++;
            }
            i = (i + 1) % n;
        } while (i != start);

        // Find highest ID
        int leader = Collections.max(participants);

        System.out.println("\n>>> Process " + leader + " becomes COORDINATOR");

        // COORDINATOR message circulates
        i = leader;
        do {
            if (alive[i]) {
                System.out.println("COORDINATOR message: " + leader + " -> " + i);
                messageCount++;
            }
            i = (i + 1) % n;
        } while (i != leader);
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
        System.out.println("3. ELECT leader (Token Ring)");
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

                    messageCount = 0;
                    election(p);

                    // Time complexity
                    System.out.println("\nTotal messages exchanged = " + messageCount);
                    System.out.println("Time Complexity ≈ O(n)");

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