import board.SudokuBoard;
import factory.CheckerFactory;
import result.ResultCollector;
import util.CSVReader;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String filepath = "C:\\Users\\NewVision\\IdeaProjects\\Lab-9-Project\\out\\artifacts\\Lab_9_Project_jar\\board.csv";
        int mode;

        if (args.length != 2) {
            System.out.println("[INFO] No arguments detected, using default CSV path.");
            while (true) {
                System.out.print("Choose mode (0 = Sequential, 3 = 3 threads, 27 = 27 threads): ");
                try {
                    mode = Integer.parseInt(sc.nextLine().trim());
                    if (mode == 0 || mode == 3 || mode == 27) break;
                    else System.out.println("[ERROR] Invalid mode. Enter 0, 3, or 27.");
                } catch (NumberFormatException e) {
                    System.out.println("[ERROR] Invalid input. Enter a number: 0, 3, or 27.");
                }
            }
        } else {
            filepath = args[0];
            try {
                mode = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("[ERROR] The Mode must be a number. Given: " + args[1]);
                printUsage();
                return;
            }
        }

        try {
            File f = new File(filepath);
            if (!f.exists()) {
                System.err.println("[ERROR] File not found: " + f.getAbsolutePath());
                return;
            }

            System.out.println("[INFO] Loading board from: " + filepath);
            SudokuBoard board = CSVReader.load(filepath);

            ResultCollector results = ResultCollector.getInstance();
            results.reset();

            System.out.println("[INFO] Running mode " + mode + "...");

            List<Thread> threads = CheckerFactory.createCheckers(mode, board, results);

            if (threads.isEmpty()) {
                System.out.println("[INFO] Sequential mode detected, running checks directly...");
                for (int i = 0; i < 9; i++) results.check("ROW", i, board.getRow(i));
                for (int i = 0; i < 9; i++) results.check("COL", i, board.getColumn(i));
                for (int i = 0; i < 9; i++) results.check("BOX", i, board.getBox(i));
            } else {
                for (Thread t : threads) t.start();
                for (Thread t : threads) t.join();
            }

            System.out.println();
            results.printFinalResult();

        } catch (Exception e) {
            System.err.println("[UNEXPECTED ERROR] " + e.getMessage());
            e.printStackTrace();
        } finally {
            sc.close();
        }
    }

    private static void printUsage() {
        System.out.println("Sudoku Checker CLI");
        System.out.println("-------------------");
        System.out.println("Usage:");
        System.out.println("  java -jar Sudoku.jar <path-to-csv> <mode>");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  java -jar Sudoku.jar board.csv 27");
        System.out.println();
        System.out.println("Modes:");
        System.out.println("  0  → Sequential (main thread only)");
        System.out.println("  3  → 3 Threads (Rows, Columns, Boxes)");
        System.out.println(" 27  → 27 Threads (one per Row, Column, Box)");
        System.out.println();
    }
}
