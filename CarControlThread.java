/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication44;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 *
 * @author LENOVO
 */
public class CarControlThread implements Runnable {
    private Car car;
    private int moveUpKey;
    private int moveDownKey;
    private int moveLeftKey;
    private int moveRightKey;
    private GamePanel gamePanel;

    public CarControlThread(Car car, int moveUpKey, int moveDownKey, int moveLeftKey, int moveRightKey, GamePanel gamePanel) {
        this.car = car;
        this.moveUpKey = moveUpKey;
        this.moveDownKey = moveDownKey;
        this.moveLeftKey = moveLeftKey;
        this.moveRightKey = moveRightKey;
        this.gamePanel = gamePanel;
    }


    @Override
    public void run() {
        gamePanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == moveUpKey || keyCode == moveDownKey || keyCode == moveLeftKey || keyCode == moveRightKey) {
                     gamePanel.requestFocus();
                    gamePanel.updateCarPosition(car, keyCode);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }
}
    class RandomCarControlThread implements Runnable {
    private Car car;
    private GamePanel gamePanel;
    private Random random;

    public RandomCarControlThread(Car car, GamePanel gamePanel) {
        this.car = car;
        this.gamePanel = gamePanel;
        this.random = new Random();
    }

    @Override
    public void run() {
        gamePanel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        while (true) {
            int keyCode = getRandomKeyCode();
            gamePanel.updateCarPosition(car, keyCode);

            try {
                Thread.sleep(300); // Wait for 1 second before moving again
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private int getRandomKeyCode() {
        int[] keyCodes = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT};
        int randomIndex = random.nextInt(keyCodes.length);
        return keyCodes[randomIndex];
    }
}
