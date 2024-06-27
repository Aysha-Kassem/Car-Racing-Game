/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication44;

import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Car {
    private ImageIcon image;
    private int x, y;
    private int score;
    private int frameWidth;
    private int frameHeight;
    int width;
    int height;
    int speed;
    private static final int CAR_SIZE = 50;
    private boolean userControlled;
    private boolean removed;

    public Car(ImageIcon image, int x, int y, int frameWidth, int frameHeight) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.width = image.getIconWidth();
        this.height = image.getIconHeight();
        this.score = 0;
        this.speed = 12;
    }

    public ImageIcon getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }

    public boolean isUserControlled() {
        return userControlled;
    }

    public void moveUp() {
        y -= speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY) {
        this.y = newY;
    }

    int getHeight() {
        return height;
    }

    int getWidth() {
        return width;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, CAR_SIZE, CAR_SIZE);
    }

}
