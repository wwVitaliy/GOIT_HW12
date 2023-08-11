/**
 * Prints to console how many milliseconds passed from the program start each second.
 * Prints to console "Минуло 5 секунд" each 5 second.
 */
public class Task1 {
    public static void main(String[] args) throws InterruptedException {

        long startTime = System.currentTimeMillis();

        // Create and start second thread
        new Thread(() -> {
            // Prints to console "Минуло 5 секунд" each 5 second.
            while (true) {
                try {
                    Thread.sleep(5000);
                    System.out.println("Минуло 5 секунд");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

        // Each second prints to console how many milliseconds passed from the program start.
        while (true) {
            Thread.sleep(1000);
            System.out.println(System.currentTimeMillis() - startTime);
        }
    }
}
