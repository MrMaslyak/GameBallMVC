package Objects;

import java.awt.*;
import Interface.*;

public class Ball implements doPaint {
    private double x, y;
    private double xStep, yStep;
    private int rad;
    private Color color;
    private int width, height;
    private final double mass = 1.0;
    private final double damping = 0.99;


    public Ball(int x, int y, double xStep, double yStep, Color color, int width, int height) {
        this.x = x;
        this.y = y;
        this.xStep = xStep;
        this.yStep = yStep;
        this.color = color;
        this.width = width;
        this.height = height;
        setRad(width / 2);


    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillOval((int) x, (int) y, width, height);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getxStep() {
        return xStep;
    }

    public void setxStep(double xStep) {
        this.xStep = xStep;
    }

    public double getyStep() {
        return yStep;
    }

    public void setyStep(double yStep) {
        this.yStep = yStep;
    }

    public int getRad() {
        return rad;
    }

    public void setRad(int rad) {
        this.rad = rad;
    }

    public double getMass() {
        return mass;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void updatePosition() {
        x += xStep;
        y += yStep;
    }

    public void applyDamping() {
        xStep *= damping;
        yStep *= damping;
    }
}
