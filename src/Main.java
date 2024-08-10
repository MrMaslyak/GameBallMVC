import Objects.GameController;
import Objects.Model;
import Objects.View;

public class Main {
    public static void main(String[] args) {

        View view = new View();
        Model model = new Model(
                view.getBalls(),
                view.getHoleView(),
                view.getBallStrikeCountLabel(),
                view.getBallCountLabel()
        );
        GameController controller = new GameController(model, view);
        GameThread gameThread = new GameThread(model, view);
        gameThread.start();

    }
}