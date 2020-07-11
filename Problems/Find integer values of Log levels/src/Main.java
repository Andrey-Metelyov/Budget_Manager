import java.util.Scanner;
import java.util.logging.Level;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);
        String[] levels = scanner.nextLine().split(" ");
        int total = 0;
        for (String level : levels) {
            level = level.toUpperCase();
            if (level.equals(Level.INFO.toString())) {
                total += Level.INFO.intValue();
            } else if (level.equals(Level.SEVERE.toString())) {
                total += Level.SEVERE.intValue();
            } else if (level.equals(Level.CONFIG.toString())) {
                total += Level.CONFIG.intValue();
            } else if (level.equals(Level.FINE.toString())) {
                total += Level.FINE.intValue();
            } else if (level.equals(Level.FINER.toString())) {
                total += Level.FINER.intValue();
            } else if (level.equals(Level.FINEST.toString())) {
                total += Level.FINEST.intValue();
            } else if (level.equals(Level.WARNING.toString())) {
                total += Level.WARNING.intValue();
            }
        }
        System.out.println(total);
    }
}