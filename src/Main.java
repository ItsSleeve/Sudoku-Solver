import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;

public class Main {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 600;
    public static int[] input = new int[3]; // [x, y, entry]

    public static void main(String[] args) {
        // data
        Puzzle inputPuzzle = new Puzzle();
        Puzzle outputPuzzle = new Puzzle();

        // graphics FOR PUZZLE
        JFrame puzzleFrame = new JFrame("Sudoku Solver");
        DrawPanel panel = new DrawPanel(inputPuzzle.getMatrix(), outputPuzzle.getMatrix());
        setupPuzzleFrame(puzzleFrame, panel, inputPuzzle);
       
        // graphics FOR CONTROL
        JFrame controlFrame = new JFrame("Control");
        JPanel controlPanel = new JPanel();
        setupControlFrame(controlFrame, controlPanel, panel, inputPuzzle, outputPuzzle);

    }

    public static void setupPuzzleFrame(JFrame puzzleFrame, DrawPanel panel, Puzzle inputPuzzle) {
    
        puzzleFrame.setSize(WIDTH, HEIGHT); 
        puzzleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        puzzleFrame.add(panel);
        puzzleFrame.setVisible(true);
    }

    public static void setupControlFrame(JFrame controlFrame, JPanel controlPanel, DrawPanel panel, Puzzle inputPuzzle, Puzzle outputPuzzle) {
        controlFrame.setSize(400, 400);
        controlFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the main panel layout to GridBagLayout to center everything in the window

        
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints mainGbc = new GridBagConstraints();
        mainGbc.insets = new Insets(10, 10, 10, 10); // Add padding around components
        mainGbc.fill = GridBagConstraints.NONE;
        mainGbc.anchor = GridBagConstraints.CENTER; // Center everything in the window

        // Create a sub-panel with BoxLayout for vertically stacking buttons and text fields
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.Y_AXIS));
        subPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add buttons to the sub-panel
        JButton findSolutionButton = new JButton("Find Solution");
        JButton enterValueButton = new JButton("Enter Value");
        JButton clearBoardButton = new JButton("Clear Board");
        JButton enterFileButton = new JButton("Enter Filepath");

        // Ensure buttons are left-aligned within the sub-panel
        findSolutionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        enterValueButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        clearBoardButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        enterFileButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        subPanel.add(findSolutionButton);
        subPanel.add(Box.createVerticalStrut(10)); // Add spacing between buttons
        subPanel.add(enterValueButton);
        subPanel.add(Box.createVerticalStrut(10));
        subPanel.add(clearBoardButton);
        subPanel.add(Box.createVerticalStrut(10));
        subPanel.add(enterFileButton);

        // Add space between buttons and text fields
        subPanel.add(Box.createVerticalStrut(20));

        // Create a panel for labels and text fields with GridBagLayout
        JPanel textFieldPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        // Add x label and text field
        JLabel xLabel = new JLabel("x:");
        xLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JTextField xField = createPlaceholderTextField("enter a number 0-9");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Align label and field to the left
        textFieldPanel.add(xLabel, gbc);
        gbc.gridx = 1;
        textFieldPanel.add(xField, gbc);

        // Add y label and text field
        JLabel yLabel = new JLabel("y:");
        yLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JTextField yField = createPlaceholderTextField("enter a number 0-9");
        gbc.gridx = 0;
        gbc.gridy = 1;
        textFieldPanel.add(yLabel, gbc);
        gbc.gridx = 1;
        textFieldPanel.add(yField, gbc);

        // Add entry label and text field
        JLabel entryLabel = new JLabel("entry:");
        entryLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        JTextField entryField = createPlaceholderTextField("enter a number 0-9");
        gbc.gridx = 0;
        gbc.gridy = 2;
        textFieldPanel.add(entryLabel, gbc);
        gbc.gridx = 1;
        textFieldPanel.add(entryField, gbc);

        // Add filepath label and text field
        JLabel filepathLabel = new JLabel("filepath:");
        JTextField filepathField = createPlaceholderTextField("enter filepath");
        gbc.gridx = 0;
        gbc.gridy = 3;
        textFieldPanel.add(filepathLabel, gbc);
        gbc.gridx = 1;
        textFieldPanel.add(filepathField, gbc);

        // Ensure text fields are left-aligned within the sub-panel
        textFieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Add the text field panel to the sub-panel
        JPanel textFieldWrapper = new JPanel();
        textFieldWrapper.setLayout(new BoxLayout(textFieldWrapper, BoxLayout.Y_AXIS));
        textFieldWrapper.add(textFieldPanel);
        textFieldWrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        subPanel.add(textFieldWrapper);

        // Declare variables to store the values
        int[] values = new int[2]; 

        // Add ActionListener to "Enter Value" button
        enterValueButton.addActionListener(e -> {
            try {
                // Parse the values from the text fields
                int x = Integer.parseInt(xField.getText().trim());
                int y = Integer.parseInt(yField.getText().trim());
                int entry = Integer.parseInt(entryField.getText().trim());

                // Check if the numbers are within the valid range
                if (x < 0 || x > 9 || y < 0 || y > 9 || entry < 0 || entry > 9) {
                    JOptionPane.showMessageDialog(
                        controlFrame,
                        "Please enter numbers between 0 and 9.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Store the values
                input[0] = x;
                input[1] = y;
                input[2] = entry;

                inputPuzzle.add(x-1, y-1, entry);
                outputPuzzle.add(x-1, y-1, entry);
                panel.repaint();

                // Confirm values in a dialog or print them
                JOptionPane.showMessageDialog(
                    controlFrame,
                    "Values Entered:\nX: " + x + "\nY: " + y + "\nEntry: " + entry,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException ex) {
                // Handle invalid input
                JOptionPane.showMessageDialog(
                    controlFrame,
                    "Please enter valid numbers.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Add ActionListener to "Clear board" button
        clearBoardButton.addActionListener(e -> {
            try {
                Puzzle.solved = false;
                inputPuzzle.clear();
                outputPuzzle.clear();
                panel.repaint();
            }
            finally {}
        });

        // Add ActionListener to "Find solution" button
        findSolutionButton.addActionListener(e -> {
            try {
                Puzzle solutionPuzzle = Solver.solve(inputPuzzle, outputPuzzle, panel);
                if (solutionPuzzle == null) {
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            outputPuzzle.add(i, j, inputPuzzle.get(i, j) );
                        }
                    }
                    panel.repaint();
                    Puzzle.solved = false;
                    JOptionPane.showMessageDialog(
                    controlFrame,
                    "No solution found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                    );
                }
                else {
                    Puzzle.solved = true;
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            outputPuzzle.add(i, j, solutionPuzzle.get(i, j) );
                        }
                    }
                    JOptionPane.showMessageDialog(
                        controlFrame,
                "Solution found!",
                  "Success",
                        JOptionPane.INFORMATION_MESSAGE
                        );
                    panel.repaint();
                } 
                
            }
            catch (Error error) {
                JOptionPane.showMessageDialog(
                    controlFrame,
                    "No solution found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // Add ActionListener to "Enter File" button
        enterFileButton.addActionListener(e -> {
            try {
                String filePath = filepathField.getText();
                int[][] matrix = new int[9][9];

                try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    for (int i = 0; i < 9; i++) { // Read each line
                        line = reader.readLine();
                        String[] stringNumbers = line.split(" ");

                        int[] numbers = new int[stringNumbers.length];
                        for (int j = 0; j < stringNumbers.length; j++) {
                            numbers[j] = Integer.parseInt(stringNumbers[j]);
                        }
                        matrix[i] = numbers;
                    }
                    if (inputPuzzle.addMatrix(matrix) == -1) {
                        JOptionPane.showMessageDialog(
                        controlFrame,
                "Invalid input",
                  "Error",
                        JOptionPane.ERROR_MESSAGE
                        );
                    }
                    outputPuzzle.addMatrix(matrix);
                    panel.repaint();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(
                    controlFrame,
                    "Error reading file " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                }
            }
            finally{}
        });

        // Add the sub-panel to the control panel
        mainGbc.gridx = 0;
        mainGbc.gridy = 0;
        controlPanel.add(subPanel, mainGbc);

        // Add the control panel to the frame
        controlFrame.add(controlPanel);

        // Center the frame on the screen
        controlFrame.setLocationRelativeTo(null);

        // Make the second window visible
        controlFrame.setVisible(true); 
    }

     // Method to create a JTextField with placeholder text
    private static JTextField createPlaceholderTextField(String placeholder) {
        JTextField textField = new JTextField(10); // Set the preferred width
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

        // Add focus listener to handle placeholder text
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });

        return textField;
    }

    
}