package checker;

import board.SudokuBoard;
import result.ResultCollector;

public class RowChecker extends Checker {
    private final int rowIndex;

    public RowChecker(int rowIndex, SudokuBoard board, ResultCollector results) {
        super(board, results);
        this.rowIndex = rowIndex;
        setName("RowChecker-" + (rowIndex+1));
    }

    @Override
    public void run() {
        int[] row = board.getRow(rowIndex);
        results.check("ROW", rowIndex, row);
    }
}
