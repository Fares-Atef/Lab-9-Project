import board.SudokuBoard;
import factory.CheckerFactory;
import result.ResultCollector;
import util.CSVReader;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String filepath;
        int mode;

        // -----------------------------
        //  DEFAULT RUN MODE (no args)
        // -----------------------------
        if (args.length < 2) {
            System.out.println("No arguments detected. Running with defaults:");
            filepath = "D:/Mohamed/Prog-2--Java--Assignments/Lab-9-Project/out/production/Lab-9-Project/board.csv";
            mode = 27;
        }

        else {
            filepath = args[0];
            mode = Integer.parseInt(args[1]);
        }

        try {
            SudokuBoard board = CSVReader.load(filepath);
            ResultCollector results = ResultCollector.getInstance();

            List<Thread> threads = CheckerFactory.createCheckers(mode, board, results);

            for (Thread t : threads) t.start();
            for (Thread t : threads) t.join();

            results.printFinalResult();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
