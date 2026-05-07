import java.util.*;

public class TokenRing {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Number Of Nodes: ");
        int n = sc.nextInt();

        System.out.println("Ring:");
        for (int i = 0; i < n; i++)
            System.out.print(i + " -> ");
        System.out.println("0");

        int choice;
        int token = 0; // token starts at 0

        do {
            System.out.println("\nCurrent Token at Process: " + token);

            System.out.print("Enter Sender: "); int sender = sc.nextInt();
            System.out.print("Enter Receiver: "); int receiver = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Data: "); String data = sc.nextLine();

            // move token until it reaches sender
            System.out.println("\nToken Passing:");
            while (token != sender) {
                System.out.print(token + " -> ");
                token = (token + 1) % n;
            }
            System.out.println(sender);

            // critical section
            System.out.println("\nProcess " + sender + " ENTERS Critical Section");
            System.out.println("Sending Data: " + data);

            // data forwarding
            int i = sender;
            while (i != receiver) {
                System.out.println("Data forwarded By " + i + " To " + (i+1)%n);
                i = (i + 1) % n;
            }

            System.out.println("Receiver " + receiver + " received data: " + data);
            System.out.println("Process " + sender + " EXITS Critical Section");

            // pass token to next process
            token = (sender + 1) % n;

            System.out.print("\nEnter 1 to continue, 0 to stop: ");
            choice = sc.nextInt();

        } while (choice == 1);
    }
}