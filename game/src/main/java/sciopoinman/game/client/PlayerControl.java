package sciopoinman.game.client;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class PlayerControl extends AbstractControl {

    private PlayerInputState inputState;
    private Camera cam;
    private BetterCharacterControl physicsChar;
    private boolean thirdPerson = false;

    private final float speed = 7f;
    private final float sensitivity = 3f;
    private float yaw = 0f;
    private float pitch = 0f;
    private final Vector3f firstPersonOffset = new Vector3f(0, 1.7f, 0);
    private final Vector3f thirdPersonOffset = new Vector3f(0, 2.5f, -5f);

    public PlayerControl(PlayerInputState inputState, Camera cam, BetterCharacterControl physicsChar) {
        this.inputState = inputState;
        this.cam = cam;
        this.physicsChar = physicsChar;
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

        physicsChar.setWalkDirection(movement.mult(speed));

        spatial.move(movement.mult(speed * tpf));
        Vector3f offset = thirdPerson ? thirdPersonOffset : firstPersonOffset;
        if (inputState.isSwitchView()) thirdPerson = !thirdPerson;

        Quaternion bodyRotation = new Quaternion();
        bodyRotation.fromAngles(0, yaw, 0);
        spatial.setLocalRotation(bodyRotation);

        Quaternion camRotation = new Quaternion();
        camRotation.fromAngles(pitch, yaw, 0);
        cam.setRotation(camRotation);
        cam.setLocation(spatial.getWorldTranslation().add(offset));

        if (thirdPerson) {
            float clampedPitch = Math.max(-0.3f, Math.min(0.6f, pitch));
    
            // CAM DISTANCE AND HEIGHT - THIRD PERSON
            float distance = 7f;
            float height = 3f;
            
            // THIRD PERSON CAMERA POSITION (BEHIND PLAYER)
            Vector3f playerPos = spatial.getWorldTranslation().add(0, height, 0);
            
            float camX = playerPos.x - (float)(Math.sin(yaw) * distance);
            float camY = playerPos.y + (float)(Math.sin(clampedPitch) * distance);
            float camZ = playerPos.z - (float)(Math.cos(yaw) * distance);
            
            cam.setLocation(new Vector3f(camX, camY, camZ));
            cam.lookAt(playerPos, Vector3f.UNIT_Y);
        } else {
            cam.setLocation(spatial.getWorldTranslation().add(0, 3f, 0));
            camRotation.fromAngles(pitch, yaw, 0);
            cam.setRotation(camRotation);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        // lascia vuoto se non ti serve rendering custom
    }
    
}