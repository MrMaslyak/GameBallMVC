
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Mouse implements MouseListener {
    private ArrayList<Ball> balls;
    private View.BallCountLabel labels;
    private View.GamePanel panelLine;

    public Mouse(ArrayList<Ball> balls, View.BallCountLabel labels, View.GamePanel panelLine) {
        this.balls = balls;
        this.labels = labels;
        this.panelLine = panelLine;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Random random = new Random();
        int x = e.getX();
        int y = e.getY();
        int width = random.nextInt(30) + 30;
        int height = random.nextInt(30) + 30;
        int xStep = random.nextInt(20) - 10;
        int yStep = random.nextInt(20) - 10;
        Color color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Ball ball = new Ball(x, y, xStep, yStep, color, width, height);
        balls.add(ball);
        labels.updateCount(balls.size());
        panelLine.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
