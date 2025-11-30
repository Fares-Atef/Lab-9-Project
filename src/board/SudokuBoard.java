package board;

public class SudokuBoard {
    private final int[][] grid = new int[9][9];

    public SudokuBoard(int[][] values) {
        if (values.length != 9) throw new IllegalArgumentException("Board must have 9 rows");
        for (int i = 0; i < 9; i++) {
            if (values[i].length != 9) throw new IllegalArgumentException("Each row must have 9 columns");
            System.arraycopy(values[i], 0, grid[i], 0, 9);
        }
    }

    public int get(int r, int c) { return grid[r][c]; }

    public int[] getRow(int r) {
        int[] row = new int[9];
        System.arraycopy(grid[r], 0, row, 0, 9);
        return row;
    }

    public int[] getColumn(int c) {
        int[] col = new int[9];
        for (int i = 0; i < 9; i++) col[i] = grid[i][c];
        return col;
    }

    public int[] getBox(int boxIndex) {
        int[] box = new int[9];
        int rowStart = (boxIndex / 3) * 3;
        int colStart = (boxIndex % 3) * 3;
        int k = 0;
        for (int r = rowStart; r < rowStart + 3; r++) {
            for (int c = colStart; c < colStart + 3; c++) {
                box[k++] = grid[r][c];
            }
        }
        return box;
    }
}
