package sciopoinman.game;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Quaternion;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import sciopoinman.game.client.PlayerControl;
import sciopoinman.game.client.PlayerInputState;
import sciopoinman.game.world.WorldRenderer;
import sciopoinman.game.world.WorldState;

/**
 * The JMonkeyEngine game entry, you should only do initializations for your game here, game logic is handled by
 * Custom states {@link com.jme3.app.state.BaseAppState}, Custom controls {@link com.jme3.scene.control.AbstractControl}
 * and your custom entities implementations of the previous.
 *
 */
public class SciopoInMan extends SimpleApplication {
    private WorldRenderer worldRenderer;
    private WorldState worldState;

    private Node playerNode;

    public SciopoInMan() {
    }

    public SciopoInMan(AppState... initialStates) {
        super(initialStates);
    }

    @Override
    public void simpleInitApp() {
        
        // PLAYER
        playerNode = new Node("player");
        playerNode.setLocalTranslation(0, 0, 0f); 
        rootNode.attachChild(playerNode);
        cam.setRotation(new Quaternion().fromAngles(0, 0, 0));

        PlayerInputState inputState = new PlayerInputState();
        stateManager.attach(inputState);

        // STATES
        flyCam.setEnabled(false);
        inputManager.setCursorVisible(false);

        // OBJECTS
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        worldState = new WorldState();

        // PLAYER
        BulletAppState bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        worldRenderer = new WorldRenderer(assetManager, rootNode, bulletAppState.getPhysicsSpace());

        BetterCharacterControl physicsChar = new BetterCharacterControl(0.4f, 1.8f, 80f);
        playerNode.addControl(physicsChar);
        playerNode.addControl(new PlayerControl(inputState, cam, physicsChar));
        bulletAppState.getPhysicsSpace().add(playerNode);
   }
}