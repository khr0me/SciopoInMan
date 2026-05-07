package sciopoinman.game.client;
import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;

public class PlayerInputState extends BaseAppState {

    // VARS

    private boolean moveForward;
    private boolean moveLeft;
    private boolean moveBack;
    private boolean moveRight;
    private float MouseX;
    private float MouseY;

    
    // LISTENERS

    private final AnalogListener analogListener = (name, value, tpf) -> {
        switch (name) {
            case "MouseX" -> MouseX -= value;
            case "MouseXPos" -> MouseX += value;
            case "MouseY" -> MouseY -= value;
            case "MouseYPos" -> MouseY += value;
        }
    };

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

    // @Override
    // public void update(float tpf) {
    //     MouseX = 0;
    //     MouseY = 0;
    // }


    // PLAYER INPUTS
    
    @Override
    protected void cleanup(Application app) {
        app.getInputManager().removeListener(analogListener);
        app.getInputManager().removeListener(actionListener);
        app.getInputManager().deleteMapping("Forward");
        app.getInputManager().deleteMapping("Left");
        app.getInputManager().deleteMapping("Back");
        app.getInputManager().deleteMapping("Right");
        app.getInputManager().deleteMapping("MouseX");
        app.getInputManager().deleteMapping("MouseXPos");
        app.getInputManager().deleteMapping("MouseY");
        app.getInputManager().deleteMapping("MouseYPos");
    }

    @Override
    protected void initialize(Application app) {
        // KEYBOARD
        app.getInputManager().addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        app.getInputManager().addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        app.getInputManager().addMapping("Back", new KeyTrigger(KeyInput.KEY_S));
        app.getInputManager().addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        app.getInputManager().addListener(actionListener, "Forward", "Left", "Back", "Right");

        // MOUSE
        app.getInputManager().setCursorVisible(false);
        app.getInputManager().addMapping("MouseX", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        app.getInputManager().addMapping("MouseXPos", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        app.getInputManager().addMapping("MouseY", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        app.getInputManager().addMapping("MouseYPos", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        app.getInputManager().addListener(analogListener, "MouseX", "MouseXPos", "MouseY", "MouseYPos");
    }


    // GETTERS

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

    public float getMouseX() {
        float val = MouseX;
        MouseX = 0;
        return val;
    }   

    public float getMouseY() {
        float val = MouseY;
        MouseY = 0;
        return val;
    }  
}
