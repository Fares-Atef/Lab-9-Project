package checker;

import board.SudokuBoard;
import result.ResultCollector;

public abstract class Checker extends Thread {
    protected final SudokuBoard board;
    protected final ResultCollector results;

    protected Checker(SudokuBoard board, ResultCollector results) {
        this.board = board;
        this.results = results;
    }
}
