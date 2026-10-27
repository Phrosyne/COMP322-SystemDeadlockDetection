import java.util.*;

public class Project2 {
    private static final String NL = "\n";
    private static Scanner input;
    private static Relation[] process;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        int n;
        int m;

        try {
            System.out.println("Enter the number of processes:");
            String output = input.nextLine().trim();
            n = Integer.parseInt(output);

            if (n < 1) {
                System.out.println("Invalid input.");
                return;
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
}
