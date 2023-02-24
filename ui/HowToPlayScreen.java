package com.group8project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class HowToPlayScreen extends JPanel implements KeyListener {
    /**
     * The HowToPlayScreen's parent. Used to switch back the to main menu
     */
    private ScreenManager screenManager;
    /**
     * The JLabel used to display the background image holding the game instructions
     */
    private JLabel backgroundLabel;
    /**
     * The JLabel used to display the back button to go back to main menu
     */
    private JLabel backBtnLabel;

    /**
     * Creates a new HowToPlayScreen
     * @param screenManager the parent ScreenManager of this HowToPlayScreen object
     */
    HowToPlayScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(ScreenManager.width, ScreenManager.height));

        // makes the window focusable so that key bindings work
        setFocusable(true);

        // Add a key listener to listen to the backspace key binding to go back
        addKeyListener(this);

        // Initialize the components on the HowToPlayScreen
        initComponents();
    }

    /**
     * Initialize the components to be displayed on the HowToPlayScreen
     */
    private void initComponents() {
        // Initialize the JLabels that hold the components of the HowToPlayScreen
        backgroundLabel = new JLabel();
        backBtnLabel = new JLabel();

        // Set the Icon/image for the background
        backgroundLabel.setIcon(new ImageIcon(getClass().getResource("/assets/background/background_howtoplay.jpg")));
        backgroundLabel.setBounds(0, 0, ScreenManager.width, ScreenManager.height);

        // Set the Icon/image for the back button
        backBtnLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_back_bef.png")));
        // Add mouse listener to make the button react to mouse actions
        backBtnLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backBtnLabelMouseExited(evt);
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backBtnLabelMouseEntered(evt);
            }

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backBtnLabelMouseClicked(evt);
            }
        });
        backBtnLabel.setBounds(65, 40, 75, 75);

        // Attach each JLabel component to the JPanel object
        this.add(backBtnLabel);
        this.add(backgroundLabel);
    }

    /**
     * Handles the behaviour of the button when mouse is hovered over them
     * @param evt corresponds a mouse event
     */
    private void backBtnLabelMouseEntered(java.awt.event.MouseEvent evt) {
        backBtnLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/after/button_back_af.png")));
    }

    /**
     * Handles the behaviour of the button when mouse exits the hover state
     * @param evt corresponds a mouse event
     */
    private void backBtnLabelMouseExited(java.awt.event.MouseEvent evt) {
        backBtnLabel.setIcon(new ImageIcon(getClass().getResource("/assets/buttons/before/button_back_bef.png")));
    }

    /**
     * Handles the behaviour of the buttons when clicked
     * @param evt corresponds a mouse event
     */
    private void backBtnLabelMouseClicked(java.awt.event.MouseEvent evt) {
        this.screenManager.mainMenuRender();
    }

    /**
     * Handles the behaviour of the backspace key is pressed
     * @param keyEvent corresponds a key event
     */
    public void keyPressed(KeyEvent keyEvent) {
        int inputKey = keyEvent.getKeyCode();
        if (inputKey == KeyEvent.VK_BACK_SPACE) {
            this.screenManager.mainMenuRender();
        }
    }
    public void keyTyped(KeyEvent keyEvent) { }

    public void keyReleased(KeyEvent keyEvent) { }
}
