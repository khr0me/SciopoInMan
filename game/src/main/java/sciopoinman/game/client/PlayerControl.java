package sciopoinman.game.client;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class PlayerControl extends AbstractControl {

    private PlayerInputState inputState;
    private Camera cam;

    private final float speed = 7f;
    private final float sensitivity = 3f;
    private float yaw = 0f;
    private float pitch = 0f;

    public PlayerControl(PlayerInputState inputState, Camera cam) {
        this.inputState = inputState;
        this.cam = cam;
    }
    
    @Override
    protected void controlUpdate(float tpf) {

        // MOUSE ROTATION        
        // LEFT/RIGHT
        
        yaw   -= inputState.getMouseX() * sensitivity;
        pitch -= inputState.getMouseY() * sensitivity;

        pitch = Math.max(-1.5f, Math.min(1.5f, pitch));

        Quaternion rotation = new Quaternion();
        rotation.fromAngles(pitch, yaw, 0);
        cam.setRotation(rotation);

        // KEYBOARD MOVEMENT
        Vector3f movement = new Vector3f(0, 0, 0);
        
        if (inputState.isMoveForward()) {
            Vector3f dir = cam.getDirection().clone();
            dir.y = 0;
            movement.addLocal(dir);
        }
        if (inputState.isMoveBack()) {
            Vector3f dir = cam.getDirection().clone().negate();
            dir.y = 0;
            movement.addLocal(dir);
        }
        if (inputState.isMoveLeft()) {
            Vector3f left = cam.getLeft().clone();
            left.y = 0;
            movement.addLocal(left);
        }
        if (inputState.isMoveRight()) {
            Vector3f left = cam.getLeft().clone().negate();
            left.y = 0;
            movement.addLocal(left);
        }
        if (movement.length() > 0) 
            movement.normalizeLocal();

        spatial.move(movement.mult(speed * tpf));
        cam.setLocation(spatial.getWorldTranslation().add(0, 1.7f, 0));
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        // lascia vuoto se non ti serve rendering custom
    }
    
}