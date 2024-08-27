import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe extends JFrame implements ActionListener {
    private JButton[] buttons = new JButton[9];
    private boolean isXTurn = true;
    private String playerXName;
    private String playerOName;
    private int playerXScore = 0;
    private int playerOScore = 0;
    private JLabel scoreLabel;
    private JLabel statusLabel;

    public TicTacToe() {
        setTitle("Tic-Tac-Toe");
        setSize(400, 450); // Slightly increased size to accommodate the score label
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize player names
        playerXName = JOptionPane.showInputDialog(this, "Enter Player X's Name:", "Player X");
        playerOName = JOptionPane.showInputDialog(this, "Enter Player O's Name:", "Player O");

        JPanel gridPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        gridPanel.setBackground(new Color(50, 50, 50));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Roboto", Font.BOLD, 60));
            buttons[i].setFocusPainted(false);
            buttons[i].setBackground(new Color(240, 240, 240));
            buttons[i].setForeground(new Color(0, 0, 0));
            buttons[i].setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 5));
            buttons[i].addActionListener(this);
            gridPanel.add(buttons[i]);
        }

        statusLabel = new JLabel(playerXName + "'s turn (X)", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Roboto", Font.PLAIN, 20));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(70, 70, 70));

        scoreLabel = new JLabel("Score: " + playerXName + " - 0 | " + playerOName + " - 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(new Color(50, 50, 50));

        add(gridPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.NORTH);
        add(scoreLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();

        if (!buttonClicked.getText().equals("")) {
            return; // Ignore click if button is already marked
        }

        if (isXTurn) {
            buttonClicked.setText("X");
            buttonClicked.setForeground(new Color(255, 87, 34));
            statusLabel.setText(playerOName + "'s turn (0)");
        } else {
            buttonClicked.setText("O");
            buttonClicked.setForeground(new Color(33, 150, 243));
            statusLabel.setText(playerXName + "'s turn (X)");
        }

        if (checkForWin()) {
            String winner = isXTurn ? playerXName : playerOName;
            JOptionPane.showMessageDialog(this,
                    winner + " wins!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
            highlightWinningCombo();
            updateScore(winner);
            promptForNextRound();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this,
                    "The game is a draw!",
                    "Game Over", JOptionPane.INFORMATION_MESSAGE);
            promptForNextRound();
        } else {
            isXTurn = !isXTurn; // Switch turns
        }
    }

    private boolean checkForWin() {
        // Winning combinations (rows, columns, diagonals)
        int[][] winningPositions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] positions : winningPositions) {
            if (buttons[positions[0]].getText().equals(buttons[positions[1]].getText()) &&
                    buttons[positions[1]].getText().equals(buttons[positions[2]].getText()) &&
                    !buttons[positions[0]].getText().equals("")) {
                return true;
            }
        }

        return false;
    }

    private void highlightWinningCombo() {
        // Highlight the winning combination
        int[][] winningPositions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] positions : winningPositions) {
            if (buttons[positions[0]].getText().equals(buttons[positions[1]].getText()) &&
                    buttons[positions[1]].getText().equals(buttons[positions[2]].getText()) &&
                    !buttons[positions[0]].getText().equals("")) {
                for (int pos : positions) {
                    buttons[pos].setBackground(new Color(144, 238, 144)); // Light green for the winning combination
                }
                break;
            }
        }
    }

    private boolean isBoardFull() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    private void resetBoard() {
        for (JButton button : buttons) {
            button.setText("");
            button.setBackground(new Color(240, 240, 240)); // Reset button color
        }
        isXTurn = true;
        statusLabel.setText(playerXName + "'s turn (X)");
    }

    private void updateScore(String winner) {
        if (winner.equals(playerXName)) {
            playerXScore++;
        } else {
            playerOScore++;
        }
        scoreLabel.setText("Score: " + playerXName + " - " + playerXScore + " | " + playerOName + " - " + playerOScore);
    }

    private void promptForNextRound() {
        int response = JOptionPane.showConfirmDialog(this,
                "Would you like to play another round?",
                "Next Round?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response == JOptionPane.YES_OPTION) {
            resetBoard();
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToe::new);
    }
}
