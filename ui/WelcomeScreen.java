package com.group8project.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WelcomeScreen extends JPanel implements KeyListener {
    /**
     * The WelcomeScreen's parent. Used to switch back to the main menu
     */
    private ScreenManager screenManager;
    /**
     * The JLabel used to display the background image holding the game instructions
     */
    private JLabel backgroundLabel;
    /**
     * The JLabel used to display the the text on the WelcomeScreen
     */
    private JLabel textLabel;

    /**
     * Creates a new WelcomeScreen
     * @param screenManager the parent ScreenManager of this WelcomeScreen object
     */
    WelcomeScreen(ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(ScreenManager.width, ScreenManager.height));

        // makes the window focusable so that key bindings work
        setFocusable(true);

        // Add a key listener to listen to the backspace key binding to go back
        addKeyListener(this);

        // Initialize the components on the WelcomeScreen
        initComponents();
    }

    /**
     * Initialize the components to be displayed on the WelcomeScreen
     */
    private void initComponents() {
        // Initialize the JLabels that hold the components of the WelcomeScreen
        backgroundLabel = new JLabel();
        textLabel = new JLabel();

        // Set the Icon/image for the background
        backgroundLabel.setIcon(new ImageIcon(getClass().getResource("/assets/background/background_welcome.jpg")));
        // Add mouse listener to make the screen react to mouse actions
        backgroundLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                screenAction();
            }
        });
        backgroundLabel.setBounds(0, 0, ScreenManager.width, ScreenManager.height);

        // Prepare the text label that displays the 'Press any key to continue' message
        textLabel.setFont(new java.awt.Font("Trebuchet MS", 0, 26));
        textLabel.setForeground(new java.awt.Color(153, 153, 153));
        textLabel.setText("PRESS ANY KEY TO CONTINUE");
        textLabel.setBounds(560, 750, 350, 31);

        // Attach each JLabel component to the JPanel object
        this.add(textLabel);
        this.add(backgroundLabel);
    }

    /**
     * Handles the behaviour of the backspace key is pressed
     * @param evt corresponds a key event
     */
    public void keyPressed(KeyEvent evt) { screenAction(); }

    public void keyTyped(KeyEvent keyEvent) { }

    public void keyReleased(KeyEvent keyEvent) { }

    /**
     * Handles the action when a key/mouse event is triggered
     */
    private void screenAction() {
        this.screenManager.mainMenuRender();
    }
}
