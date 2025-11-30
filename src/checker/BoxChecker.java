package checker;

import board.SudokuBoard;
import result.ResultCollector;

public class BoxChecker extends Checker {
    private final int boxIndex;

    public BoxChecker(int boxIndex, SudokuBoard board, ResultCollector results) {
        super(board, results);
        this.boxIndex = boxIndex;
        setName("BoxChecker-" + (boxIndex+1));
    }

    @Override
    public void run() {
        int[] box = board.getBox(boxIndex);
        results.check("BOX", boxIndex, box);
    }
}
