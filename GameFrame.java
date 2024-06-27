
package javaapplication44;

import javax.swing.*;

public class GameFrame extends JFrame {
    private GamePanel gamePanel;
    public static int winningScore;

    public GameFrame() {
        setTitle("Racing Car Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        String input = JOptionPane.showInputDialog(null,
                "Enter the winning score:\n\nNote: Your car is \u00A9light blue\u00A9.", "Game setup",
                JOptionPane.QUESTION_MESSAGE);
        int winningScore;
        try {
            winningScore = Integer.parseInt(input);
            if (winningScore < 1) {
                winningScore = 20; // default value
                JOptionPane.showMessageDialog(null, "Invalid input. Using default value of 20.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            winningScore = 20; // default value
            JOptionPane.showMessageDialog(null, "Invalid input. Using default value of 20.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        GamePanel.winningScore = winningScore;

        gamePanel = new GamePanel();
        add(gamePanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
