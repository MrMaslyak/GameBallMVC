public class GameThread extends Thread {
    private Model model;
    private View view;

    public GameThread(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void run() {
        while (true) {
            try {
                view.repaint();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
