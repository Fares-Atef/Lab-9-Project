package checker;

import board.SudokuBoard;
import result.ResultCollector;

public class ColumnChecker extends Checker {
    private final int colIndex;

    public  ColumnChecker(int colIndex, SudokuBoard board, ResultCollector results) {
        super(board, results);
        this.colIndex = colIndex;
        setName("ColChecker-" + (colIndex+1));
    }

    @Override
    public void run() {
        int[] col = board.getColumn(colIndex);
        results.check("COL", colIndex, col);
    }
}
