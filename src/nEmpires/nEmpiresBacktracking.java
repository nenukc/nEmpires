package nEmpires;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class nEmpiresBacktracking {
    private static final int BOARD_SIZE = 10;
    private static final int EMPTY = 0;
    private static final int EMPEROR = 1;

    private final int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
    private final boolean[] usedColumns = new boolean[BOARD_SIZE];
    private final boolean[] usedMajorDiagonals = new boolean[BOARD_SIZE * 2 - 1];
    private final boolean[] usedMinorDiagonals = new boolean[BOARD_SIZE * 2 - 1];
    private final List<int[]> placed = new ArrayList<>();

    private int[][] bestBoard = new int[BOARD_SIZE][BOARD_SIZE];
    private int bestCount = 0;

    public static void main(String[] args) {
        nEmpiresBacktracking solver = new nEmpiresBacktracking();
        solver.solve(0);
        solver.printResult();

        if (!GraphicsEnvironment.isHeadless()) {
            SwingUtilities.invokeLater(() -> solver.showBoard());
        }
    }

    private void solve(int row) {
        if (placed.size() + (BOARD_SIZE - row) <= bestCount) {
            return;
        }

        if (row == BOARD_SIZE) {
            saveBestIfNeeded();
            return;
        }

        for (int column = 0; column < BOARD_SIZE; column++) {
            if (canPlace(row, column)) {
                place(row, column);
                solve(row + 1);
                remove(row, column);
            }
        }

        solve(row + 1);
    }

    private boolean canPlace(int row, int column) {
        if (usedColumns[column]) {
            return false;
        }

        if (usedMajorDiagonals[majorDiagonal(row, column)]) {
            return false;
        }

        if (usedMinorDiagonals[minorDiagonal(row, column)]) {
            return false;
        }

        for (int[] emperor : placed) {
            int rowDiff = Math.abs(row - emperor[0]);
            int columnDiff = Math.abs(column - emperor[1]);

            if ((rowDiff == 1 && columnDiff == 2) || (rowDiff == 2 && columnDiff == 1)) {
                return false;
            }
        }

        return true;
    }

    private void place(int row, int column) {
        board[row][column] = EMPEROR;
        usedColumns[column] = true;
        usedMajorDiagonals[majorDiagonal(row, column)] = true;
        usedMinorDiagonals[minorDiagonal(row, column)] = true;
        placed.add(new int[] { row, column });
    }

    private void remove(int row, int column) {
        board[row][column] = EMPTY;
        usedColumns[column] = false;
        usedMajorDiagonals[majorDiagonal(row, column)] = false;
        usedMinorDiagonals[minorDiagonal(row, column)] = false;
        placed.remove(placed.size() - 1);
    }

    private int majorDiagonal(int row, int column) {
        return row - column + BOARD_SIZE - 1;
    }

    private int minorDiagonal(int row, int column) {
        return row + column;
    }

    private void saveBestIfNeeded() {
        if (placed.size() <= bestCount) {
            return;
        }

        bestCount = placed.size();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                bestBoard[row][column] = board[row][column];
            }
        }
    }

    private void printResult() {
        System.out.println("Best placement: " + bestCount + " emperors on a "
                + BOARD_SIZE + "x" + BOARD_SIZE + " board");
        System.out.println();

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                System.out.print(bestBoard[row][column]);
            }
            System.out.println();
        }
    }

    private void showBoard() {
        JFrame frame = new JFrame("nEmpires - queen + knight backtracking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BoardPanel boardPanel = new BoardPanel(bestBoard, bestCount);
        JTextArea notes = new JTextArea(summaryText());
        notes.setEditable(false);
        notes.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.add(new JScrollPane(notes), BorderLayout.SOUTH);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private String summaryText() {
        return "Emperor = queen + knight\n"
                + "Best found: " + bestCount + " emperors\n"
                + "for a " + BOARD_SIZE + "x" + BOARD_SIZE
                + " board\n";
    }

    private static class BoardPanel extends JPanel {
        private static final int CELL_SIZE = 54;
        private final int[][] board;
        private final int count;

        BoardPanel(int[][] board, int count) {
            this.board = board;
            this.count = count;
            setPreferredSize(new Dimension(BOARD_SIZE * CELL_SIZE, BOARD_SIZE * CELL_SIZE + 34));
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            super.paintComponent(graphics);

            Graphics2D g = (Graphics2D) graphics;
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g.setColor(new Color(35, 35, 35));
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            g.drawString("Best placement: " + count + " emperors", 14, 23);

            int top = 34;
            for (int row = 0; row < BOARD_SIZE; row++) {
                for (int column = 0; column < BOARD_SIZE; column++) {
                    int x = column * CELL_SIZE;
                    int y = top + row * CELL_SIZE;

                    g.setColor((row + column) % 2 == 0 ? new Color(241, 217, 181) : new Color(181, 136, 99));
                    g.fillRect(x, y, CELL_SIZE, CELL_SIZE);

                    if (board[row][column] == EMPEROR) {
                        g.setColor(new Color(80, 22, 22));
                        g.fillOval(x + 10, y + 10, CELL_SIZE - 20, CELL_SIZE - 20);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
                        g.drawString("E", x + 20, y + 35);
                    }
                }
            }
        }
    }
}
