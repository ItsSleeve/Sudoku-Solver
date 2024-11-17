import javax.swing.*;
import java.awt.*;
public class DrawPanel extends JPanel {
    private int[][] inputMatrix;
    private int[][] outputMatrix;

    private static final int GRID_WIDTH = 300; // grid is a square so use it for width and height
    private static final int[] INPUT_TOPLEFT = {125, Main.HEIGHT-500};
    private static final int[] OUTPUT_TOPLEFT = {125 + GRID_WIDTH + 50, Main.HEIGHT - 500};

    private static final Color LIGHT_GRAY = new Color(210, 210, 210);
    private static final Color LIGHT_RED = new Color(255, 128, 128);
    private static final Color LIGHT_GREEN = new Color(128, 255, 128);

    public DrawPanel(int[][] inputMatrix, int[][] outputMatrix) {
        this.inputMatrix = inputMatrix;
        this.outputMatrix = outputMatrix;
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(5)); 

        // draw grid input
        g2d.setColor(LIGHT_GRAY);
        g2d.fillRoundRect(INPUT_TOPLEFT[0], INPUT_TOPLEFT[1], GRID_WIDTH, GRID_WIDTH, 30, 30);

        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(INPUT_TOPLEFT[0], INPUT_TOPLEFT[1], GRID_WIDTH, GRID_WIDTH, 30, 30);
        // large
        g2d.drawLine(INPUT_TOPLEFT[0] + GRID_WIDTH/3, INPUT_TOPLEFT[1], INPUT_TOPLEFT[0] + GRID_WIDTH/3, INPUT_TOPLEFT[1] + GRID_WIDTH);
        g2d.drawLine(INPUT_TOPLEFT[0] + GRID_WIDTH/3*2, INPUT_TOPLEFT[1], INPUT_TOPLEFT[0] + GRID_WIDTH/3*2, INPUT_TOPLEFT[1] + GRID_WIDTH);
        g2d.drawLine(INPUT_TOPLEFT[0], INPUT_TOPLEFT[1] + GRID_WIDTH/3, INPUT_TOPLEFT[0] + GRID_WIDTH, INPUT_TOPLEFT[1] + GRID_WIDTH/3);
        g2d.drawLine(INPUT_TOPLEFT[0], INPUT_TOPLEFT[1] + GRID_WIDTH/3*2, INPUT_TOPLEFT[0] + GRID_WIDTH, INPUT_TOPLEFT[1] + GRID_WIDTH/3*2);

        // small
        g2d.setStroke(new BasicStroke(3));
        for (int i = GRID_WIDTH/9; i < GRID_WIDTH - GRID_WIDTH/9; i += GRID_WIDTH/9) {
            g2d.drawLine(INPUT_TOPLEFT[0] + i, INPUT_TOPLEFT[1], INPUT_TOPLEFT[0] + i, INPUT_TOPLEFT[1] + GRID_WIDTH);
        }
        for (int i = GRID_WIDTH/9; i < GRID_WIDTH - GRID_WIDTH/9; i += GRID_WIDTH/9) {
            g2d.drawLine(INPUT_TOPLEFT[0], INPUT_TOPLEFT[1] + i, INPUT_TOPLEFT[0] + GRID_WIDTH, INPUT_TOPLEFT[1] + i);
        }

        // draw grid output
        g2d.setStroke(new BasicStroke(5)); 
        g2d.setColor(Puzzle.solved ? LIGHT_GREEN : LIGHT_RED);
        g2d.fillRoundRect(OUTPUT_TOPLEFT[0], OUTPUT_TOPLEFT[1], GRID_WIDTH, GRID_WIDTH, 30, 30);

        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(OUTPUT_TOPLEFT[0], OUTPUT_TOPLEFT[1], GRID_WIDTH, GRID_WIDTH, 30, 30);
        // large
        g2d.drawLine(OUTPUT_TOPLEFT[0] + GRID_WIDTH/3, OUTPUT_TOPLEFT[1], OUTPUT_TOPLEFT[0] + GRID_WIDTH/3, OUTPUT_TOPLEFT[1] + GRID_WIDTH);
        g2d.drawLine(OUTPUT_TOPLEFT[0] + GRID_WIDTH/3*2, OUTPUT_TOPLEFT[1], OUTPUT_TOPLEFT[0] + GRID_WIDTH/3*2, OUTPUT_TOPLEFT[1] + GRID_WIDTH);
        g2d.drawLine(OUTPUT_TOPLEFT[0], OUTPUT_TOPLEFT[1] + GRID_WIDTH/3, OUTPUT_TOPLEFT[0] + GRID_WIDTH, OUTPUT_TOPLEFT[1] + GRID_WIDTH/3);
        g2d.drawLine(OUTPUT_TOPLEFT[0], OUTPUT_TOPLEFT[1] + GRID_WIDTH/3*2, OUTPUT_TOPLEFT[0] + GRID_WIDTH, OUTPUT_TOPLEFT[1] + GRID_WIDTH/3*2);

        // small
        g2d.setStroke(new BasicStroke(3));
        for (int i = GRID_WIDTH/9; i < GRID_WIDTH - GRID_WIDTH/9; i += GRID_WIDTH/9) {
            g2d.drawLine(OUTPUT_TOPLEFT[0] + i, OUTPUT_TOPLEFT[1], OUTPUT_TOPLEFT[0] + i, OUTPUT_TOPLEFT[1] + GRID_WIDTH);
        }
        for (int i = GRID_WIDTH/9; i < GRID_WIDTH - GRID_WIDTH/9; i += GRID_WIDTH/9) {
            g2d.drawLine(OUTPUT_TOPLEFT[0], OUTPUT_TOPLEFT[1] + i, OUTPUT_TOPLEFT[0] + GRID_WIDTH, OUTPUT_TOPLEFT[1] + i);
        }

        // draw numbers
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        for (int i = 0; i <= 8; i++) {
            for (int j = 0; j <= 8; j++) {
                if (inputMatrix[i][j] == 0) {
                    g.drawString("-", INPUT_TOPLEFT[0] + i * GRID_WIDTH/9 + 10, INPUT_TOPLEFT[1] + j * GRID_WIDTH/9 + 25);
                }
                else {
                    g.drawString(String.valueOf(inputMatrix[i][j]), INPUT_TOPLEFT[0] + i * GRID_WIDTH/9 + 10, INPUT_TOPLEFT[1] + j * GRID_WIDTH/9 + 25);
                }
                if (outputMatrix[i][j] == 0) {
                    g.drawString("-", OUTPUT_TOPLEFT[0] + i * GRID_WIDTH/9 + 10, OUTPUT_TOPLEFT[1] + j * GRID_WIDTH/9 + 25);
                }
                else {
                    g.drawString(String.valueOf(outputMatrix[i][j]), OUTPUT_TOPLEFT[0] + i * GRID_WIDTH/9 + 10, OUTPUT_TOPLEFT[1] + j * GRID_WIDTH/9 + 25);               
                }
            }
        }

        // title
        g.setColor(LIGHT_GRAY);
        g.fillRect(0, 0, Main.WIDTH, 70);
        g.setColor(Color.BLACK);
        g.drawLine(0, 70, Main.WIDTH, 70);

        g.setFont(new Font("Arial", Font.PLAIN, 50));
        g.drawString("==Sudoku Solver==", Main.WIDTH/4, 50);

        // input output signs
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Input", INPUT_TOPLEFT[0] + GRID_WIDTH/3, INPUT_TOPLEFT[1] + GRID_WIDTH + 40);
        g.drawString("Output", OUTPUT_TOPLEFT[0] + GRID_WIDTH/3, OUTPUT_TOPLEFT[1] + GRID_WIDTH + 40);

    }

    
}