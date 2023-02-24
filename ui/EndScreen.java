package com.group8project.ui;

import com.group8project.Game;

import javax.swing.*;
import java.awt.*;

public class EndScreen extends JPanel {
    /**
     * The EndScreen's parent. Used to switch to main menu or exit the game
     */
    private ScreenManager screenManager;
    /**
     * The JLabel used to display the background image corresponding the result of the game
     */
    private JLabel backgroundLabel;
    /**
     * The JLabel used to display the play again button to start a new instance of the game
     */
    private JLabel playAgainLabel;
    /**
     * The JLabel used to display the exit button to exit the game
     */
    private JLabel exitLabel;
    /**
     * The JLabel used to display the final score (contents of finalScore variable)
     */
    private JLabel scoreLabel;
    /**
     * The JLabel used to display the time taken (contents of timeElapsed variable)
     */
    private JLabel timeTakenLabel;
    /**
     * The switch (True or False) to decide what background image to show on the screen
     */
    private boolean isAWin;
    /**
     * The final score of the player to display on the screen
     */
    private int finalScore;
    /**
     * The final time taken by the player to display on the screen
     */
    private long timeElapsed;

    /**
     * Creates a new EndScreen
     * @param screenManager the parent ScreenManager of this EndScreen object
     * @param isAWin the result of the game
     * @param finalScore the final score of the game
     * @param timeElapsed the total time taken until result
     */
    EndScreen(ScreenManager screenManager, boolean isAWin, int finalScore, long timeElapsed) {
        this.screenManager = screenManager;
        this.isAWin = isAWin;
        this.finalScore = finalScore;
        this.timeElapsed = timeElapsed;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(ScreenManager.width, ScreenManager.height));

        // Initialize the components on the EndScreen
        initComponents();
    }

    /**
     * Initialize the components to be displayed on the EndScreen
     */
    private void initComponents() {
        // Initialize the JLabels that hold the components of the end screen
        backgroundLabel = new JLabel();
        playAgainLabel = new JLabel();
        exitLabel = new JLabel();
        scoreLabel = new JLabel();
        timeTakenLabel = new JLabel();

        // Check the result of the game to decide the background image to display
        if (isAWin) {
            backgroundLabel.setIcon(new ImageIcon(getClass().getResource("/assets/background/background_win.jpg")));
        } else {
            backgroundLabel.setIcon(new ImageIcon(getClass().getResource("/assets/background/background_lose.jpg")));
        }
        backgroundLabel.setBounds(0, 0, ScreenManager.width, ScreenManager.height);

        // Set the Icon/image for the back button
        playAgainLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/buttons/before/button_playagain_bef.png")));
        // Add mouse listener to make the button react to mouse actions
        playAgainLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                playAgainLabelMouseExited(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                playAgainLabelMouseEntered(evt);
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playAgainLabelMouseClicked(evt);
            }
        });
        playAgainLabel.setBounds(585, 360, 286, 74);

        // Set the Icon/image for the back button
        exitLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_exit_bef.png")));
        // Add mouse listener to make the button react to mouse actions
        exitLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                exitLabelMouseExited(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                exitLabelMouseEntered(evt);
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exitLabelMouseClicked(evt);
            }
        });
        exitLabel.setBounds(925, 360, 286, 74);

        // Prepare the score label that displays final score
        scoreLabel.setFont(new java.awt.Font("SansSerif", 0, 35));
        scoreLabel.setForeground(new java.awt.Color(0, 0, 0));
        scoreLabel.setText(Integer.toString(finalScore));
        scoreLabel.setBounds(855, 540, 350, 31);

        // Prepare the time taken label that displays total time taken
        timeTakenLabel.setFont(new java.awt.Font("SansSerif", 0, 32));
        timeTakenLabel.setForeground(new java.awt.Color(0, 0, 0));
        long minutes = (timeElapsed / 1000) / 60;
        long seconds = (timeElapsed / 1000) % 60;
        timeTakenLabel.setText(minutes + " mins " + seconds + " secs");
        timeTakenLabel.setBounds(795, 640, 350, 31);

        // Attach each JLabel component to the JPanel object
        this.add(timeTakenLabel);
        this.add(scoreLabel);
        this.add(exitLabel);
        this.add(playAgainLabel);
        this.add(backgroundLabel);
    }

    /**
     * Handles the behaviour of the buttons when mouse is hovered over them
     * @param evt corresponds a mouse event
     */
    private void playAgainLabelMouseEntered(java.awt.event.MouseEvent evt) {
        playAgainLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/after/button_playagain_af.png")));
    }

    private void exitLabelMouseEntered(java.awt.event.MouseEvent evt) {
        exitLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/after/button_exit_af.png")));
    }

    /**
     * Handles the behaviour of the buttons when mouse exits the hover state
     * @param evt corresponds a mouse event
     */
    private void playAgainLabelMouseExited(java.awt.event.MouseEvent evt) {
        playAgainLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_playagain_bef.png")));
    }

    private void exitLabelMouseExited(java.awt.event.MouseEvent evt) {
        exitLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_exit_bef.png")));
    }

    /**
     * Handles the behaviour of the buttons when clicked
     * @param evt corresponds a mouse event
     */
    private void playAgainLabelMouseClicked(java.awt.event.MouseEvent evt) {
        if (this.isAWin) {
            this.screenManager.mainMenuRender();
        } else {
            this.screenManager.newGameRender(new Game());
        }

    }

    private void exitLabelMouseClicked(java.awt.event.MouseEvent evt) {
        System.exit(0);
    }
}