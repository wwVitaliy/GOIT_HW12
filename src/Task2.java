import java.util.*;

/**
 *
 */
public class Task2 {

    private static final Object MONITOR_1 = new Object();
    private static final Object MONITOR_2 = new Object();
    private static final Object MONITOR_3 = new Object();

    public static void main(String[] args) {

        // Read positive integer from console
        int n = inputCheck();

        // TreadA: print "fizz" if number is divisible by 3 and not 5
        Thread threadA = new Thread(() -> {
            synchronized (MONITOR_1) {
                try {
                    MONITOR_1.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 1; i <= n; i++) {
                    if (isFizz(i)) {
                        System.out.println("fizz");
                        notifyAllAndWait(MONITOR_1);
                    }
                }

            }
        });

        // TreadB: print "buzz" if number is divisible by 5 and not 3
        Thread threadB = new Thread(() -> {
            synchronized (MONITOR_2) {
                try {
                    MONITOR_2.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 1; i <= n; i++) {
                    if (isBuzz(i)) {
                        System.out.println("buzz");
                        notifyAllAndWait(MONITOR_2);
                    }
                }

            }
        });

        // TreadC: print "fizzbuzz" if number is divisible by 3 and 5
        Thread threadC = new Thread(() -> {
            synchronized (MONITOR_3) {
                try {
                    MONITOR_3.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                for (int i = 1; i <= n; i++) {
                    if (isFizzBuzz(i)) {
                        System.out.println("fizzbuzz");
                        notifyAllAndWait(MONITOR_3);
                    }
                }

            }
        });

        // ThreadD: print number if it is not divisible by 3 or 5
        Thread threadD = new Thread(() -> {

            for (int i = 1; i <= n; i++) {
                if (isFizz(i)) {
                    notifyAllAndWait(MONITOR_1);
                } else if (isBuzz(i)) {
                    notifyAllAndWait(MONITOR_2);
                } else if (isFizzBuzz(i)) {
                    notifyAllAndWait(MONITOR_3);
                } else {
                    System.out.println(i);
                }
            }
        });

        // Set ThreadA, ThreadB, ThreadC daemon to finish the program when ThreadD is finished
        threadA.setDaemon(true);
        threadB.setDaemon(true);
        threadC.setDaemon(true);

        // Start threads
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

    }

    /**
     * Wakes up all threads that are waiting on monitor and causes the current thread to wait
     *
     * @param monitor a monitor object
     */
    private static void notifyAllAndWait(Object monitor) {
        synchronized (monitor) {
            monitor.notifyAll();
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * Checks if number is divisible by 3 and not 5
     *
     * @param i A number to check
     * @return True if number is divisible by 3 and not 5
     * False if number is not divisible by 3 and not 5
     */
    private static boolean isFizz(int i) {
        return i % 3 == 0 && i % 5 != 0;
    }

    /**
     * Checks if number is divisible by 5 and not 3
     *
     * @param i A number to check
     * @return True if number is divisible by 5 and not 3
     * False if number is not divisible by 5 and not 3
     */
    private static boolean isBuzz(int i) {
        return i % 3 != 0 && i % 5 == 0;
    }

    /**
     * Checks if number is divisible by 3 and 5
     *
     * @param i A number to check
     * @return True if number is divisible by 3 and 5
     * False if number is not divisible by 3 or 5
     */
    private static boolean isFizzBuzz(int i) {
        return i % 3 == 0 && i % 5 == 0;
    }

    /**
     * Reads a positive integer from console.
     *
     * @return a positive integer
     */
    private static int inputCheck() {
        int res;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.print("Enter positive integer: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Wrong input!");
                System.out.print("Enter positive integer: ");
                scanner.next();
            }
            res = scanner.nextInt();
        } while (res <= 0);
        return res;
    }
}

