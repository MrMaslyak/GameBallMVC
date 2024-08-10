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
        view.addMouseListener(createMouseListener());
        view.addKeyListener(createKeyListener());
    }

    private MouseListener createMouseListener() {
        return new Mouse(model.getBalls(), view.getBallCountLabel(), view.getGamePanel());
    }

    private KeyListener createKeyListener() {
        return new Key(view.getBallMain());
    }

    public void update() {

    }
}
