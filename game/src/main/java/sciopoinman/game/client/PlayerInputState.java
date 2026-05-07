package sciopoinman.game.client;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;

public class PlayerInputState extends BaseAppState {

    private boolean moveForward;
    private boolean moveLeft;
    private boolean moveBack;
    private boolean moveRight;

    private final ActionListener actionListener = (name, isPressed, tpf) -> {
        switch (name) {
            case "Forward" -> moveForward = isPressed;
            case "Left" -> moveLeft = isPressed;
            case "Back" -> moveBack = isPressed;
            case "Right" -> moveRight = isPressed;
            default -> {
            }
        }
    };

    @Override
    protected void onEnable() {

    }
    
    @Override
    protected void onDisable() {

    }
    
    @Override
    protected void cleanup(Application app) {
        app.getInputManager().removeListener(actionListener);
        app.getInputManager().deleteMapping("Forward");
        app.getInputManager().deleteMapping("Left");
        app.getInputManager().deleteMapping("Back");
        app.getInputManager().deleteMapping("Right");
    }

    @Override
    protected void initialize(Application app) {
        app.getInputManager().addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        app.getInputManager().addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        app.getInputManager().addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        app.getInputManager().addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        app.getInputManager().addListener(actionListener, "Forward", "Left", "Back", "Right");
    }

    public boolean isMoveForward() {
        return moveForward;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public boolean isMoveBack() {
        return moveBack;
    }
    
    public boolean isMoveRight() {
        return moveRight;
    }
}
