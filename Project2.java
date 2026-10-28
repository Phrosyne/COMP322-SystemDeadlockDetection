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
            return;
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

            try {
                selection = Integer.parseInt(input.nextLine().trim());            
            } catch (NumberFormatException e) {
                selection = -1;
            }

            switch (selection) {
                case 1:
                    printRelations();
                    break;
                case 2:
                    changeRelation();
                    break;
                case 3:
                    detectDeadlock();
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

    public static void printRelations() {
        int m = process[0].getResourceLength();

        for (int j = 0; j < m; j++) {
            System.out.print("\tr" + j);
        }

        System.out.println();

        for (int i = 0; i < process.length; i++) {
            System.out.print("p" + i);
            for (int j = 0; j < m; j++) {
                System.out.print("\t");
                if (process[i].getResource(j) != 0) {
                    System.out.print(process[i].getResource(j));
                }
            }
            System.out.println();
        }
    }

    public static void changeRelation() {
        int n = process.length;

        System.out.println("Enter the process index:");
        int pIndex = -1;
        try {
            pIndex = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (pIndex < 0 || pIndex >= process.length) {
            System.out.println("Invalid process index.");
            return;
        }

        System.out.println("Enter the resource index:");
        int rIndex = -1;

        try {
            rIndex = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (rIndex < 0 || rIndex >= process[0].getResourceLength()) {
            System.out.println("Invalid resource index.");
            return;
        }

        System.out.println("Enter the new relation (0: None, 1: P requests R, 2: P allocated R):");
        int newRelation = -1;

        try {
            newRelation = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
            return;
        }

        if (newRelation < 0 || newRelation > 2) {
            System.out.println("Invalid relation value.");
            return;
        }

        if (newRelation == 0) {
            process[pIndex].setResource(rIndex, 0);
            System.out.println("There is now no relation between process " + pIndex + "and resource " + rIndex + ".");
        }

        if (newRelation == 1) {
            process[pIndex].setResource(rIndex, newRelation);
            System.out.println("Process " + pIndex + " is now requesting to use resource " + rIndex + ".");
        }

        if (newRelation == 2) {
            for (int i = 0; i < n; i++) {
                if (i != pIndex && process[i].getResource(rIndex) == 2) {
                    System.out.println("Process " + pIndex + " cannot be allocated resource " + rIndex
                            + " because process " + i + " is using it.");
                    return;
                }
            }
            process[pIndex].setResource(rIndex, 2);
            System.out.println("Process " + pIndex + " has been allocated resource "
                    + rIndex + " for usage.");
        }

    }

    public static void detectDeadlock() {
        int n = process.length;
        int m = process[0].getResourceLength();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (process[i].getResource(j) == 2) {
                    for (int k = 0; k < n; k++) {
                        if (process[k].getResource(j) == 1) {
                            if (detectDeadlockRecursively(i, k)) {
                                System.out.println("Base:");
                                System.out.println("Process " + k + " is requesting resource " + j + ".");
                                System.out.println("Process " + i + " is allocated resource " + j + ".");
                                return;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("The system is not deadlocked, and is currently safe.");
    }

    public static boolean detectDeadlockRecursively(int target, int current) {
        int n = process.length;
        int m = process[0].getResourceLength();

        for (int j = 0; j < m; j++) {
            if (process[current].getResource(j) == 2) {
                for (int k = 0; k < n; k++) {
                    if (process[k].getResource(j) == 1) {
                        if (k == target) {
                            System.out.println("The system is deadlocked due to the following relations:");
                            System.out.println("Process " + k + " is requesting resource " + j + ".");
                            System.out.println("Process " + current + " is allocated resource " + j + ".");
                            return true;
                        }
                        
                        if (detectDeadlockRecursively(target, k)) {
                            System.out.println("Backtrack:");
                            System.out.println("Process " + k + " is requesting resource " + j + ".");
                            System.out.println("Process " + current + " is allocated resource " + j + ".");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
