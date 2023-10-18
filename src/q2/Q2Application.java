package q2;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Q2Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        int input=0;
        boolean isValidInteger = false;
        while (!isValidInteger) {/// getting a valid integer from user
            System.out.println("Enter an Integer: ");
            try {
                input = scanner.nextInt();
                scanner.nextLine();
                isValidInteger = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! please enter and Integer: ");
                scanner.nextLine();
            }
        }

        List<Integer> list = new ArrayList<>();

        int finalInput = input;
        Thread evenInserterThread = new Thread(() -> {
            for (int i = 0; i <= finalInput; i += 2) {
                if (i != finalInput) {
                    synchronized (list) {
                        list.add(i);
                        System.out.println("evenInserterThread added " + i);
                        try {
                            list.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //list.notifyAll();
                    }
                } else {
                    System.out.println("evenInserterThread added " + i);
                    list.add(i);
                }
            }

        });

        Thread oddInserterThread = new Thread(() -> {
            for (int i = 1; i <= finalInput; i += 2) {
                synchronized (list) {
                    list.add(i);
                    System.out.println("oddInserterThread added " + i);
                    list.notify();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
//                    try {
//                        list.wait();
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
                }
            }
        });
        evenInserterThread.start();
        oddInserterThread.start();
        try {
            evenInserterThread.join();
            oddInserterThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("List: " + list);
    }
}