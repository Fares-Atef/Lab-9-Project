package util;

import board.SudokuBoard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {


    public static SudokuBoard load(String filepath) throws Exception {
        List<int[]> rows = new ArrayList<>(9);

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("[,\\s]+");
                if (parts.length == 0) continue;
                if (parts.length != 9) throw new IllegalArgumentException("Each row must have 9 values: \"" + line + "\"");
                int[] r = new int[9];
                for (int i = 0; i < 9; i++) {
                    try {
                        r[i] = Integer.parseInt(parts[i]);
                    } catch (NumberFormatException nfe) {
                        throw new IllegalArgumentException("Non-integer value found: " + parts[i]);
                    }
                    if (r[i] < 1 || r[i] > 9) throw new IllegalArgumentException("Values must be in range 1-9");
                }
                rows.add(r);
            }
        }

        if (rows.size() != 9) throw new IllegalArgumentException("File must contain 9 non-empty rows");

        int[][] grid = new int[9][9];
        for (int i = 0; i < 9; i++) grid[i] = rows.get(i);

        return new SudokuBoard(grid);
    }
}
