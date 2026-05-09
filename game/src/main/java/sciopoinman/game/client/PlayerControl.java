package sciopoinman.game.client;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class PlayerControl extends AbstractControl {

    public static float getCamDistance() {
        return CAM_DISTANCE;
    }

    private final PlayerInputState inputState;
    private final Camera cam;
    private final BetterCharacterControl physicsChar;
    private boolean thirdPerson = false;

    private final float speed = 7f;
    private final float sensitivity = 15f;
    private float yaw = 0f;
    private float pitch = 0f;

    // Prima persona: offset occhi
    private static final Vector3f firstPersonOffset = new Vector3f(0, 1.7f, 0);

    // CAM DISTANCE AND HEIGHT - THIRD PERSON DEFAULT
    private static final float CAM_DISTANCE = 7f;
    private static final float CAM_HEIGHT = 3f;

    public PlayerControl(PlayerInputState inputState, Camera cam, BetterCharacterControl physicsChar) {
        this.inputState  = inputState;
        this.cam         = cam;
        this.physicsChar = physicsChar;
    }

    @Override
    protected void controlUpdate(float tpf) {

        // TOGGLE VISTA
        if (inputState.isSwitchView()) {
            thirdPerson = !thirdPerson;
        }

        // MOUSE ROTATION
        // LEFT/RIGHT
        yaw   -= inputState.getMouseX() * sensitivity * tpf;
        pitch -= inputState.getMouseY() * sensitivity * tpf;
        pitch  = Math.max(-1.5f, Math.min(1.5f, pitch));

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
            movement.addLocal(cam.getLeft().clone().setY(0));
        }
        if (inputState.isMoveRight()) {
            movement.addLocal(cam.getLeft().clone().negate().setY(0));
        }

        if (movement.length() > 0)
            movement.normalizeLocal();

        // Solo physicsChar muove il personaggio — rimuovi spatial.move()
        physicsChar.setWalkDirection(movement.mult(speed));

        // JUMP
        if (inputState.isJump() && physicsChar.isOnGround()) {
            physicsChar.jump();
        }

        // ROTAZIONE CORPO (solo asse Y)
        Quaternion bodyRotation = new Quaternion();
        bodyRotation.fromAngles(0, yaw, 0);
        spatial.setLocalRotation(bodyRotation);

        if (!thirdPerson) {

            // FIRST PERSON CAMERA POSITION
            cam.setLocation(spatial.getWorldTranslation().add(firstPersonOffset));
            Quaternion camRot = new Quaternion();
            camRot.fromAngles(pitch, yaw, 0);
            cam.setRotation(camRot);

        } else if (thirdPerson) {
            updateThirdPersonCamera(CAM_DISTANCE, -0.3f, 0.5f);
        }
    }

    private void updateThirdPersonCamera(float distance, float pitchMin, float pitchMax) {
        float clampedPitch = Math.max(pitchMin, Math.min(pitchMax, pitch));

        // THIRD PERSON CAMERA POSITION (BEHIND PLAYER)
        Vector3f playerPos = spatial.getWorldTranslation().add(0, CAM_HEIGHT, 0);

        float camX = playerPos.x - (float)(Math.sin(yaw)   * Math.cos(clampedPitch) * distance);
        float camY = playerPos.y + (float)(Math.sin(clampedPitch) * distance);
        float camZ = playerPos.z - (float)(Math.cos(yaw)   * Math.cos(clampedPitch) * distance);

        cam.setLocation(new Vector3f(camX, camY, camZ));
        cam.lookAt(playerPos, Vector3f.UNIT_Y);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        // lascia vuoto se non ti serve rendering custom
    }
}