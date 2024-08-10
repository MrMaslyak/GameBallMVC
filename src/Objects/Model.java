package Objects;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;


public class Model {
    private ArrayList<Ball> balls;
    private View.HoleView hole;
    private View.BallStrikeCountLabel ballStrikeCount;
    private View.BallCountLabel ballCount;
    private Thread updateThread;

    public Model(ArrayList<Ball> balls, View.HoleView hole, View.BallStrikeCountLabel ballStrikeCount, View.BallCountLabel ballCount) {
        this.balls = balls;
        this.hole = hole;
        this.ballStrikeCount = ballStrikeCount;
        this.ballCount = ballCount;
        ballCount.updateCount(balls.size());
        startUpdateThread();
    }

    private void startUpdateThread() {
        new Thread(() -> {
            while (true) {
                try {
                    updateBalls();
                    handleCollisions();
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void updateBalls() {
        for (Ball ball : balls) {
            ball.updatePosition();
            ball.applyDamping();
            checkCollision();

        }
    }
    public void checkCollision() {
        int x = 0, y = 0, width = 800, height = 550;

        balls.forEach(ball -> {
            if (ball.getX() < x) {
                ball.setxStep(-ball.getxStep());
                ball.setX(x);
            } else if (ball.getX() > width - ball.getRad()) {
                ball.setxStep(-ball.getxStep());
                ball.setX(width - ball.getRad());
            }
            if (ball.getY() < y) {
                ball.setyStep(-ball.getyStep());
                ball.setY(y);
            } else if (ball.getY() > height - ball.getRad()) {
                ball.setyStep(-ball.getyStep());
                ball.setY(height - ball.getRad());
            }

            ball.applyDamping();
        });
    }



    public void handleCollisions() {
        HoleDeleted holeDeleted = new HoleDeleted(balls, hole, ballStrikeCount, ballCount);
        holeDeleted.checkCollisions();
        ballCount.updateCount(balls.size());

        for (int i = 0; i < balls.size(); i++) {
            for (int j = i + 1; j < balls.size(); j++) {
                Ball ball1 = balls.get(i);
                Ball ball2 = balls.get(j);

                double dx = ball1.getX() - ball2.getX();
                double dy = ball1.getY() - ball2.getY();
                double distance = Math.sqrt(dx * dx + dy * dy);
                double combinedRadius = ball1.getRad() + ball2.getRad();

                if (distance <= combinedRadius) {
                    double overlap = combinedRadius - distance;
                    double moveDistance = overlap / 2;

                    double nx = dx / distance;
                    double ny = dy / distance;

                    double p = 2 * (ball1.getxStep() * nx + ball1.getyStep() * ny - ball2.getxStep() * nx - ball2.getyStep() * ny) /
                            (ball1.getMass() + ball2.getMass());

                    ball1.setxStep(ball1.getxStep() - p * ball2.getMass() * nx);
                    ball1.setyStep(ball1.getyStep() - p * ball2.getMass() * ny);
                    ball2.setxStep(ball2.getxStep() + p * ball1.getMass() * nx);
                    ball2.setyStep(ball2.getyStep() + p * ball1.getMass() * ny);



                    ball1.setxStep(ball1.getxStep() );
                    ball1.setyStep(ball1.getyStep() );
                    ball2.setxStep(ball2.getxStep() );
                    ball2.setyStep(ball2.getyStep() );

                    ball1.setX(ball1.getX() + moveDistance * nx);
                    ball1.setY(ball1.getY() + moveDistance * ny);
                    ball2.setX(ball2.getX() - moveDistance * nx);
                    ball2.setY(ball2.getY() - moveDistance * ny);

                    Random random = new Random();
                    int colorCollisionR = random.nextInt(256);
                    int colorCollisionG = random.nextInt(256);
                    int colorCollisionB = random.nextInt(256);
                    ball1.setColor(new Color(colorCollisionR, colorCollisionG, colorCollisionB));
                }
            }
        }
    }


    class HoleDeleted {
        private ArrayList<Ball> balls;
        private View.HoleView hole;
        private View.BallStrikeCountLabel ballStrikeCount;
        private View.BallCountLabel labelBallCount;

        public HoleDeleted(ArrayList<Ball> balls, View.HoleView hole, View.BallStrikeCountLabel ballStrikeCount, View.BallCountLabel labelBallCount) {
            this.balls = balls;
            this.hole = hole;
            this.ballStrikeCount = ballStrikeCount;
            this.labelBallCount = labelBallCount;
        }

        public void checkCollisions() {
            for (int i = 0; i < balls.size(); i++) {
                Ball ball = balls.get(i);
                if (isColliding(ball, hole)) {
                    ballStrikeCount.incrementStrikeCount();
                    balls.remove(ball);
                    labelBallCount.updateCount(balls.size());
                    break;
                }
            }
        }

        private boolean isColliding(Ball ball, View.HoleView hole) {
            int ballX = (int) ball.getX();
            int ballY = (int) ball.getY();
            int ballRadius = ball.getRad();
            int holeX = hole.getX();
            int holeY = hole.getY();
            int holeRadius = hole.getW() / 2;

            double dx = ballX + ballRadius - (holeX + holeRadius);
            double dy = ballY + ballRadius - (holeY + holeRadius);
            double distance = Math.sqrt(dx * dx + dy * dy);

            return distance < (ballRadius + holeRadius);
        }
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

}
