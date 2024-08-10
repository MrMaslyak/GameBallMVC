import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class GameController {
    private Model model;
    private View view;

    public GameController(Model model, View view) {
        this.model = model;
        this.view = view;
        initialize();
    }

    private void initialize() {
        view.addMouseListener(new Mouse(model.getBalls(), view.getBallCountLabel(), view.getGamePanel()));
        view.addKeyListener(new Key(view.getBallMain()));
    }

    public void update() {
    }
}
