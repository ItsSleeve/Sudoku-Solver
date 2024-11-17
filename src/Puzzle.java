public class Puzzle {
    private int[][] matrix; 
    public static boolean solved = false;

    public Puzzle() {
        matrix = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                matrix[i][j] = 0;
            }
        }
    }

    public Puzzle(Puzzle p) {
        matrix = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                matrix[i][j] = p.getMatrix()[i][j];
            }
        }
    }

    public int[][] getMatrix() {
        return this.matrix;
    }

    public int add(int x, int y, int entry) {
        if (x < 0 || x > 9 || y < 0 || y > 9) return -1; // invalid indices
        if (entry < 0 || entry > 9) return -2; // invalid entry

        else {
            matrix[x][y] = entry;
            return 1;
        }
    }

    public int addMatrix(int[][] newMatrix) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (newMatrix[i][j] < 0 || newMatrix[i][j] > 9) return -1; // invalid entry

            }
        }
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                matrix[i][j] = newMatrix[i][j];
            }
        }
        return 0;
    }

    public int get(int x, int y) {
        return matrix[x][y];
    }

    public void clear() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                matrix[i][j] = 0;
            }
        }
    }
}
