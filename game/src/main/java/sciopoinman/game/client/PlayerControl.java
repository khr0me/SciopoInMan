package sciopoinman.game.client;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class PlayerControl extends AbstractControl {

    private PlayerInputState inputState;
    private Camera cam;
    private final float speed = 5f;

    public PlayerControl(PlayerInputState inputState, Camera cam) {
        this.inputState = inputState;
        this.cam = cam;
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        Vector3f movement = new Vector3f(0, 0, 0);
        
        if (inputState.isMoveForward()) 
            movement.addLocal(cam.getDirection());
        if (inputState.isMoveLeft()) 
            movement.addLocal(cam.getDirection().negate());
        if (inputState.isMoveBack()) 
            movement.addLocal(cam.getLeft());
        if (inputState.isMoveRight()) 
            movement.addLocal(cam.getLeft().negate());

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