package sciopoinman.game;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

import sciopoinman.game.client.PlayerControl;
import sciopoinman.game.client.PlayerInputState;

/**
 * The JMonkeyEngine game entry, you should only do initializations for your game here, game logic is handled by
 * Custom states {@link com.jme3.app.state.BaseAppState}, Custom controls {@link com.jme3.scene.control.AbstractControl}
 * and your custom entities implementations of the previous.
 *
 */
public class SciopoInMan extends SimpleApplication {

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
        playerNode.setLocalTranslation(0, 0, 5f); 
        rootNode.attachChild(playerNode);
        
        PlayerInputState inputState = new PlayerInputState();
        stateManager.attach(inputState);
        playerNode.addControl(new PlayerControl(inputState, cam));

        // STATES
        flyCam.setEnabled(false);
        inputManager.setCursorVisible(false);

        // OBJECTS
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }

}
