import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public final class Solver {

    public static Puzzle solve(Puzzle inputPuzzle, Puzzle outputPuzzle, DrawPanel panel) {
        Puzzle solution = new Puzzle(inputPuzzle);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (! isValidMove(solution, i, j, solution.get(i, j))) {
                    return null;
                }
            }
        }
        //Timer timer = new Timer(100, null);

        int[] cell = heuristic_MRV(solution);
        if (cell == null) return solution; // filled puzzle

        List<Integer> candidates = getValidNumbers(solution.getMatrix(), cell[0], cell[1]);
        if (candidates.isEmpty()) {
            return null;
        }
        // Try each number and recurse
        for (int num : candidates) {
            solution.add(cell[0], cell[1], num); // Place the number
            if (! isValidMove(solution, cell[0], cell[1], num)) {
                solution.add(cell[0], cell[1], 0);
                continue;
            }

            outputPuzzle.add(cell[0], cell[1], num);
            panel.repaint();

            Puzzle newP = solve(solution, outputPuzzle, panel);
           
            if (newP != null && isSolved(newP)) {
                return newP; // If a valid solution is found, return it
            }
            solution.add(cell[0], cell[1], 0); // Backtrack
            outputPuzzle.add(cell[0], cell[1], 0);
            panel.repaint();
        }   
        
        return null;
    }

    public static int[] heuristic_MRV(Puzzle p) {
        int[][] board = p.getMatrix();
        int minOptions = 10; // More than the maximum possible candidates (1-9)
        int[] bestCell = null;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == 0) { // Empty cell
                    List<Integer> candidates = getValidNumbers(board, i, j);
                    if (candidates.size() < minOptions) {
                        minOptions = candidates.size();
                        bestCell = new int[]{i, j};
                    }
                }
            }
        }

        return bestCell; 
    }

    public static List<Integer> getValidNumbers(int[][] matrix, int row, int col) {
        // Array to track used numbers (1-9)
        boolean[] used = new boolean[9 + 1]; // Index 0 is unused for convenience

        // Check numbers in the same row
        for (int c = 0; c < 9; c++) {
            if (matrix[row][c] != 0) {
                used[matrix[row][c]] = true;
            }
        }

        // Check numbers in the same column
        for (int r = 0; r < 9; r++) {
            if (matrix[r][col] != 0) {
                used[matrix[r][col]] = true;
            }
        }

        // Check numbers in the 3x3 subgrid
        int boxRowStart = (row / 3) * 3; // Starting row of the subgrid
        int boxColStart = (col / 3) * 3; // Starting column of the subgrid
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                int num = matrix[boxRowStart + r][boxColStart + c];
                if (num != 0) {
                    used[num] = true;
                }
            }
        }

        // Collect all unused numbers (valid candidates)
        List<Integer> validNumbers = new ArrayList<>();
        for (int num = 1; num <= 9; num++) {
            if (!used[num]) {
                validNumbers.add(num);
            }
        }

        return validNumbers;

    }

    public static boolean isValidMove(Puzzle puzzle, int row, int col, int num) {
        int[][] matrix = puzzle.getMatrix();
    
        // Check row
        for (int i = 0; i < 9; i++) {
            if (matrix[row][i] == 0) continue;
            else if (matrix[row][i] == num && i != col) return false;
        }
    
        // Check column
        for (int i = 0; i < 9; i++) {
            if (matrix[i][col] == 0) continue;
            else if (matrix[i][col] == num && i != row) return false;
        }
    
        // Check 3x3 box
        int boxRowStart = (row / 3) * 3;
        int boxColStart = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[boxRowStart + i][boxColStart + j] == 0) continue;
                else if (matrix[boxRowStart + i][boxColStart + j] == num && boxRowStart+i != row && boxColStart + j != col) return false;
            }
        }
    
        return true; // Valid placement
    }

    public static boolean isSolved(Puzzle puzzle) {
        int[][] matrix = puzzle.getMatrix();

        // check horizontal
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matrix[i][j] == 0) return false; // unfilled box
                else {
                    sum += matrix[i][j];
                }   
            }
            if (sum != 45) return false; // 1+2+...+9 = 45
            else sum = 0;
        }
        // check horizontal
        sum = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (matrix[j][i] == 0) return false; // unfilled box
                else {
                    sum += matrix[j][i];
                }
            }
            if (sum != 45) return false; // 1+2+...+9 = 45
            else sum = 0;
        }
        // check boxes
        sum = 0;
        for (int i = 0; i <= 6; i += 3) {
            for (int j = 0; j <= 6; j+= 3) {

                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        if (matrix[i + k][j + l] == 0) return false; // unfilled box
                        else sum += matrix[i + k][j + l];
                    }
                }
                if (sum != 45) return false;
                else sum = 0;

            }
        }
        return true;
    }

    public boolean isSolvable(Puzzle puzzle) {
        boolean solvable = true;
        return solvable;
    }
}
