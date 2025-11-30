package factory;

import board.SudokuBoard;
import checker.BoxChecker;
import checker.ColumnChecker;
import checker.RowChecker;
import result.ResultCollector;

import java.util.ArrayList;
import java.util.List;

public class CheckerFactory {


    public static List<Thread> createCheckers(int mode, SudokuBoard board, ResultCollector results) {
        List<Thread> list = new ArrayList<>();

        if (mode == 0) {
            Thread seq = new Thread(() -> {
                // rows
                for (int i = 0; i < 9; i++) results.check("ROW", i, board.getRow(i));
                // cols
                for (int i = 0; i < 9; i++) results.check("COL", i, board.getColumn(i));
                // boxes
                for (int i = 0; i < 9; i++) results.check("BOX", i, board.getBox(i));
            }, "SequentialChecker");
            list.add(seq);
        } else if (mode == 3) {
            Thread rows = new Thread(() -> {
                for (int i = 0; i < 9; i++) results.check("ROW", i, board.getRow(i));
            }, "RowsChecker");
            Thread cols = new Thread(() -> {
                for (int i = 0; i < 9; i++) results.check("COL", i, board.getColumn(i));
            }, "ColsChecker");
            Thread boxes = new Thread(() -> {
                for (int i = 0; i < 9; i++) results.check("BOX", i, board.getBox(i));
            }, "BoxesChecker");
            list.add(rows);
            list.add(cols);
            list.add(boxes);
        } else { // mode == 27
            for (int i = 0; i < 9; i++) list.add(new RowChecker(i, board, results));
            for (int i = 0; i < 9; i++) list.add(new ColumnChecker(i, board, results));
            for (int i = 0; i < 9; i++) list.add(new BoxChecker(i, board, results));
        }

        return list;
    }
}
