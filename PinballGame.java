import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PinballGame extends JPanel implements ActionListener, KeyListener {
    private int ballX, ballY, ballDX, ballDY, ballSize = 20;
    private int paddleX = 140, paddleWidth = 80, paddleHeight = 10;
    private int paddleSpeed = 100; // Increased paddle movement speed
    private Timer timer;
    private Random random = new Random();

    public PinballGame() {
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.BLACK);
        resetBall();
        timer = new Timer(10, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
    }

    private void resetBall() {
        ballX = random.nextInt(300) + 50;
        ballY = random.nextInt(150) + 50;
        ballDX = random.nextBoolean() ? 4 : -4;
        ballDY = random.nextBoolean() ? 4 : -4;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillOval(ballX, ballY, ballSize, ballSize);
        g.fillRect(paddleX, getHeight() - 30, paddleWidth, paddleHeight);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ballX += ballDX;
        ballY += ballDY;

        if (ballX <= 0 || ballX >= getWidth() - ballSize) {
            ballDX = -ballDX;
        }
        if (ballY <= 0) {
            ballDY = -ballDY;
        }
        if (ballY >= getHeight() - 30 - ballSize && ballX + ballSize >= paddleX && ballX <= paddleX + paddleWidth) {
            ballDY = -Math.abs(ballDY) - 2; // Increase upward speed when hitting the paddle
        }
        if (ballY > getHeight()) {
            resetBall();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && paddleX > 0) {
            paddleX -= paddleSpeed;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddleX < getWidth() - paddleWidth) {
            paddleX += paddleSpeed;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pinball Game");
        PinballGame game = new PinballGame();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
