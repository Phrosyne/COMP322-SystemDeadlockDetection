import java.util.*;

public class Project2 {
    private static final String NL = "\n";
    private static Scanner input;
    private static Relation[] process;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        int n = -1;
        int m = -1;

        try {
            System.out.println("Enter the number of processes:");
            n = Integer.parseInt(input.nextLine().trim());

            if (n < 1) {
                System.out.println("Invalid input.");
                return;
            }

            System.out.println("Enter the number of resources:");
            m = Integer.parseInt(input.nextLine().trim());

            if (m < 1) {
                System.out.println("Invalid input.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }

        process = new Relation[n];

        for (int i = 0; i < n; i++) {
            process[i] = new Relation(m);
        }

        int selection = -1;
        while (selection != 4) {
            System.out.println(NL + "1) Print the process-to-resource relations\r\n" + //
                                "2) Change a process-to-resource relation\r\n" + //
                                "3) Detect the system for deadlock\r\n" + //
                                "4) Quit the program\r\n" + //
                                "Enter selection:");

            selection = Integer.parseInt(input.nextLine().trim());
            System.out.println(selection);
            switch (selection) {
                case 1:
                    System.out.println("printRelations()");
                    break;
                case 2:
                    System.out.println("changeRelation()");
                    break;
                case 3:
                    System.out.println("detectDeadlock()");
                    break;
                case 4:
                    System.out.println("Goodbye.");
                    break;
                default:
                    System.out.println("Invalid option, try again.");
                    break;
            }
        }
    }
}
