import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JFrame {
    private ArrayList<Ball> balls = new ArrayList<>();
    private BallCountLabel labels;
    private BallStrikeCountLabel labelsStrikeCount;
    private GamePanel panelLine;
    private Timer timer;
    private Ball ballMain;
    private HoleView hole;
    private Key key;

    public View() {
        setTitle("Game Balls");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        initUI();
        setVisible(true);

    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(mainPanel);

        hole = new HoleView(5, 5);

        labels = new BallCountLabel(0);
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(labels, BorderLayout.WEST);

        labelsStrikeCount = new BallStrikeCountLabel(0);
        topPanel.add(labelsStrikeCount, BorderLayout.EAST);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        panelLine = new GamePanel(balls, hole);
        mainPanel.add(panelLine, BorderLayout.CENTER);

        Mouse mouse = new Mouse(balls, labels, panelLine);
        panelLine.addMouseListener(mouse);

        panelLine.add(hole, BorderLayout.SOUTH);

        timer = new Timer(32, e -> {
            key.update();
            panelLine.repaint();
        });
        timer.start();

        ballMain = new Ball(350, 250, 0, 0, Color.black, 50, 50);
        ballMain.applyDamping();
        key = new Key(ballMain);
        addKeyListener(key);
        balls.add(ballMain);

        Model model = new Model(balls, hole, labelsStrikeCount, labels);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        panelLine.repaint();
    }



  class HoleView extends JLabel {
        private int x, y, w = 50, h = 50;

        public HoleView(int x, int y) {
            this.x = x;
            this.y = y;
            initHole();
        }

        private void initHole() {
            setBounds(x, y, w, h);
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/img/holeImg.png"));
            Image originalImage = originalIcon.getImage();
            Image resizedImage = originalImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(resizedImage));
            setSize(w, h);
            setOpaque(false);
            setLayout(null);
            setVisible(true);
        }

        public int getX() { return x; }
        public int getY() { return y; }
        public int getW() { return w; }
        public int getH() { return h; }
        public void setW(int w) { this.w = w; }
        public void setH(int h) { this.h = h; }
    }

    class GamePanel extends JPanel {
        private ArrayList<Ball> balls;
        private HoleView holeView;

        public GamePanel(ArrayList<Ball> balls, HoleView holeView) {
            this.balls = balls;
            this.holeView = holeView;
            setupPanel();
        }

        private void setupPanel() {
            setOpaque(true);
            setLayout(null);
            setPreferredSize(new Dimension(750, 550));
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            setDoubleBuffered(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.setColor(Color.BLACK);
            g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

            for (Ball ball : balls) {
                ball.paint(g2d);
            }

            holeView.paint(g2d);
        }
    }

    class BallCountLabel extends JLabel {
        private int countBalls;

        public BallCountLabel(int countBalls) {
            this.countBalls = countBalls;
            setupUI();
        }

        private void setupUI() {
            setForeground(Color.GRAY);
            setFont(new Font("Arial", Font.BOLD, 15));
            setText("Balls: " + countBalls);
            setOpaque(true);
        }

        public void updateCount(int countBalls) {
            this.countBalls = countBalls;
            setText("Balls: " + countBalls);
        }
    }

    class BallStrikeCountLabel extends JLabel {
        private int countBallsStrike;

        public BallStrikeCountLabel(int countBallsStrike) {
            this.countBallsStrike = countBallsStrike;
            setupUI();
        }

        private void setupUI() {
            setForeground(Color.GRAY);
            setFont(new Font("Arial", Font.BOLD, 15));
            setText("Balls Strike: " + countBallsStrike);
            setOpaque(true);
        }

        public void updateCount(int countBallsStrike) {
            setText("Balls Strike: " + countBallsStrike);
        }

        public void incrementStrikeCount() {
            countBallsStrike++;
            setText("Balls Strike: " + countBallsStrike);
        }
    }
    public GamePanel getGamePanel() {
        return panelLine;
    }

    public BallCountLabel getBallCountLabel() {
        return labels;
    }
    public Ball getBallMain() {
        return ballMain;
    }
    public BallStrikeCountLabel getBallStrikeCountLabel() {
        return labelsStrikeCount;
    }
    public HoleView getHoleView() {
        return hole;
    }
    public ArrayList<Ball> getBalls() {
        return balls;
    }

}
