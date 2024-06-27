
package javaapplication44;




import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;


public  class GamePanel extends JPanel implements ActionListener {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int CAR_SIZE = 70;
    private static final int OBSTACLE_SIZE = 20;
    private static final int BONUS_SIZE = 20;
    private static final int OBSTACLE_SPEED = 5;
    private static final int BONUS_SPEED = 3;
    public static int winningScore;

    private List<Car> cars;
    private List<Obstacle> obstacles;
    private List<Bonus> bonuses;
    private int user1Score;
    private int user2Score;
    private ImageIcon roadImage;
    private Timer timer;
    private boolean gameOver;

    public GamePanel() {

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);


        cars = new ArrayList<>();
        obstacles = new ArrayList<>();
        bonuses = new ArrayList<>();
        user1Score = 0;
        user2Score = 0;
        gameOver = false;


        createCars();
        createObstacles();
        createBonuses();
        startCarControlThreads();

        roadImage = new ImageIcon("build\\classes\\javaapplication44\\moving_road.gif");

        timer = new Timer(10, this);
        timer.start();
    }
    public boolean isGameOver() {
        return gameOver;
    }

    private void createCars() {
        ImageIcon car1Image = new ImageIcon("src\\javaapplication44\\NicePng_delorean-png_1621402.png");
        ImageIcon car2Image = new ImageIcon("src\\javaapplication44\\kindpng_1509122.png");
        ImageIcon car3Image = new ImageIcon("src\\javaapplication44\\kindpng_1509150.png");
        ImageIcon car4Image = new ImageIcon("src\\javaapplication44\\kindpng_3828606.png");
        ImageIcon car5Image = new ImageIcon("src\\javaapplication44\\top-car-view-png-34876.png");
        ImageIcon car6Image = new ImageIcon("src\\javaapplication44\\top-car-view-png-34877.png");

        cars.add(new Car(car1Image, 100, 100,800,600));
        cars.add(new Car(car2Image, 100, 200,800,600));
        cars.add(new Car(car3Image, 200, 100,800,600));
        cars.add(new Car(car4Image, 200, 200,800,600));
        cars.add(new Car(car5Image, 300, 100,800,600));
        cars.add(new Car(car6Image, 300, 200,800,600));
    }

    private void createObstacles() {
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(WIDTH - OBSTACLE_SIZE);
            int y = -random.nextInt(HEIGHT);
            obstacles.add(new Obstacle(x, y, OBSTACLE_SPEED));
        }
    }

    private void createBonuses() {
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int x = random.nextInt(WIDTH - BONUS_SIZE);
            int y = -random.nextInt(HEIGHT);
            bonuses.add(new Bonus(x, y, BONUS_SPEED));
        }
    }

    private void startCarControlThreads() {
        Thread thread1 = new Thread(new CarControlThread(cars.get(0), KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, this));
        Thread thread2 = new Thread((new RandomCarControlThread(cars.get(1), this)));
        Thread thread3 = new Thread(new RandomCarControlThread(cars.get(2), this));
        Thread thread4 = new Thread(new RandomCarControlThread(cars.get(3),  this));
        Thread thread5 = new Thread(new RandomCarControlThread(cars.get(4),  this));
        Thread thread6 = new Thread(new RandomCarControlThread(cars.get(5),  this));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
    }

    public void updateCarPosition(Car car, int keyCode) {
        int newX = car.getX();
        int newY = car.getY();

        switch (keyCode) {
        case KeyEvent.VK_W:
            newY -= car.getSpeed();
            break;
        case KeyEvent.VK_S:
            newY += car.getSpeed();
            break;
        case KeyEvent.VK_A:
            newX -= car.getSpeed();
            break;
        case KeyEvent.VK_D:
            newX += car.getSpeed();
            break;
        case KeyEvent.VK_UP:
            newY -= car.getSpeed();
            break;
        case KeyEvent.VK_DOWN:
            newY += car.getSpeed();
            break;
        case KeyEvent.VK_LEFT:
            newX -= car.getSpeed();
            break;
        case KeyEvent.VK_RIGHT:
            newX += car.getSpeed();
            break;
    }

    // Check if the new position is within the frame
    if (newX >= 0 && newX + CAR_SIZE <= getWidth()) {
        car.setX(newX);
    }
    if (newY >= 0 && newY + CAR_SIZE <= getHeight()) {
        car.setY(newY);
    }

    checkCollisions(car);
    checkBonuses(car);

    repaint();
    }

    private void checkEndGame() {
        for (Car car : cars) {
            if (car.getScore() >= winningScore) {
                gameOver = true;
                showGameOverMessage(car);
                break;
            }
        }
    }

private void respawnCar(Car car) {
    Random random = new Random();
    int x = random.nextInt(WIDTH - CAR_SIZE);
    int y = random.nextInt(HEIGHT - CAR_SIZE);
    
    while (isCarColliding(x, y)) {
        x = random.nextInt(WIDTH - CAR_SIZE);
        y = random.nextInt(HEIGHT - CAR_SIZE);
    }
    
    car.setX(x);
    car.setY(y);
}
private boolean isCarColliding(int x, int y) {
    Rectangle carRect = new Rectangle(x, y, CAR_SIZE, CAR_SIZE);

    for (Car car : cars) {
        Rectangle otherCarRect = new Rectangle(car.getX(), car.getY(), CAR_SIZE, CAR_SIZE);
        if (carRect.intersects(otherCarRect)) {
            return true;
        }
    }

    return false;
}

private void showGameOverMessage(Car winner) {
    StringBuilder message = new StringBuilder();
    message.append("Game Over\n");
    message.append("Winner: User ").append(cars.indexOf(winner) + 1).append("\n");
    message.append("User 1 Score: ").append(cars.get(0).getScore()).append("\n");
    JOptionPane.showMessageDialog(this, message.toString(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
    System.exit(0);
}


private void checkCollisions(Car car) {
    Rectangle carRect = new Rectangle(car.getX(), car.getY(), CAR_SIZE, CAR_SIZE);

    for (Obstacle obstacle : obstacles) {
        Rectangle obstacleRect = new Rectangle(obstacle.getX(), obstacle.getY(), OBSTACLE_SIZE, OBSTACLE_SIZE);
        if (carRect.intersects(obstacleRect)) {
            
                respawnCar(car);
                obstacle.setY(-OBSTACLE_SIZE); // Move the obstacle above the frame
            
        }
    }
}



private void checkBonuses(Car car) {
    Rectangle carRect = new Rectangle(car.getX(), car.getY(), CAR_SIZE, CAR_SIZE);

    List<Bonus> collectedBonuses = new ArrayList<>();

    for (Bonus bonus : bonuses) {
        Rectangle bonusRect = new Rectangle(bonus.getX(), bonus.getY(), BONUS_SIZE, BONUS_SIZE);
        if (carRect.intersects(bonusRect)) {
            car.incrementScore();
            collectedBonuses.add(bonus);
            checkEndGame();
        }
    }

    // Remove the collected bonuses
    bonuses.removeAll(collectedBonuses);

    // Respawn the collected bonuses
    respawnBonuses(collectedBonuses.size());
}

private void respawnBonuses(int numBonuses) {
    Random random = new Random();

    for (int i = 0; i < numBonuses; i++) {
        int x = random.nextInt(WIDTH - BONUS_SIZE);
        int y = -random.nextInt(HEIGHT);

        bonuses.add(new Bonus(x, y, BONUS_SPEED));
    }
}


    @Override
    public void actionPerformed(ActionEvent e) {
    if (!gameOver) {
        for (Obstacle obstacle : obstacles) {
            obstacle.move();
            if (obstacle.getY() > HEIGHT) {
                Random random = new Random();
                obstacle.setX(random.nextInt(WIDTH - OBSTACLE_SIZE));
                obstacle.setY(-random.nextInt(HEIGHT));
            }
        }

        bonuses.stream().map(bonus -> {
            bonus.move();
            return bonus;
        }).filter(bonus -> (bonus.getY() > HEIGHT)).forEach(bonus -> {
            Random random = new Random();
            bonus.setX(random.nextInt(WIDTH - BONUS_SIZE));
            bonus.setY(-random.nextInt(HEIGHT));
        });

        cars.stream().map(car -> {
            if (!car.isUserControlled()) {
                checkCollisions(car);
            }
            return car;
        }).forEachOrdered(car -> {
            checkBonuses(car);
        });

        repaint();
    }
}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw road background
        Image roadImg = roadImage.getImage();
        g.drawImage(roadImg, 0, 0, getWidth(), getHeight(), null);

        for (Car car : cars) {
            Image carImage = car.getImage().getImage();
            g.drawImage(carImage, car.getX(), car.getY(), CAR_SIZE, CAR_SIZE, null);
        }

        g.setColor(Color.RED);
        for (Obstacle obstacle : obstacles) {
            g.fillRect(obstacle.getX(), obstacle.getY(), OBSTACLE_SIZE, OBSTACLE_SIZE);
        }

        g.setColor(Color.GREEN);
        for (Bonus bonus : bonuses) {
            g.fillRect(bonus.getX(), bonus.getY(), BONUS_SIZE, BONUS_SIZE);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("User 1 Score: " + cars.get(0).getScore(), 10, 30);

    }
}
