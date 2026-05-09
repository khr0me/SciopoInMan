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
    private boolean switchView;
    private boolean jump;
    private boolean sprint;
    private boolean crouch;
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
            case "SwitchView" -> {
                if (isPressed) switchView = true;
            }
            case "Jump" -> jump = isPressed;
            case "Crouch" -> crouch = isPressed;
            case "Sprint" -> sprint = isPressed;
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
    

    // PLAYER INPUTS
    
    @Override
    protected void cleanup(Application app) {
        app.getInputManager().removeListener(analogListener);
        app.getInputManager().removeListener(actionListener);
        app.getInputManager().deleteMapping("Forward");
        app.getInputManager().deleteMapping("Left");
        app.getInputManager().deleteMapping("Back");
        app.getInputManager().deleteMapping("Right");
        app.getInputManager().deleteMapping("SwitchView");
        app.getInputManager().deleteMapping("Jump");
        app.getInputManager().deleteMapping("Crouch");
        app.getInputManager().deleteMapping("Sprint");
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

        app.getInputManager().addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
        app.getInputManager().addMapping("Sprint", new KeyTrigger(KeyInput.KEY_LSHIFT));
        app.getInputManager().addMapping("Crouch", new KeyTrigger(KeyInput.KEY_C));
        app.getInputManager().addMapping("SwitchView", new KeyTrigger(KeyInput.KEY_V));

        // MOUSE
        app.getInputManager().setCursorVisible(false);
        app.getInputManager().addMapping("MouseX", new MouseAxisTrigger(MouseInput.AXIS_X, true));
        app.getInputManager().addMapping("MouseXPos", new MouseAxisTrigger(MouseInput.AXIS_X, false));
        app.getInputManager().addMapping("MouseY", new MouseAxisTrigger(MouseInput.AXIS_Y, true));
        app.getInputManager().addMapping("MouseYPos", new MouseAxisTrigger(MouseInput.AXIS_Y, false));
        
        app.getInputManager().addListener(actionListener, "Forward", "Left", "Back", "Right", "SwitchView", "Jump", "Sprint", "Crouch");
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
    
    public boolean isSwitchView() {
        boolean val = switchView;
        switchView = false;
        return val;
    }
    
    public boolean isJump() {
        boolean val = jump;
        jump = false;
        return val;
    }

    public boolean isSprint() {
        return sprint;
    }

    public boolean isCrouch() {
        boolean val = crouch;
        crouch = false;
        return val;
    }

    public boolean isCrouchHeld() {
        return crouch;
    }
}
