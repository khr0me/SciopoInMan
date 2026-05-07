package sciopoinman.game;

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;

import sciopoinman.game.world.WorldRenderer;
import sciopoinman.game.world.WorldState;

import com.jme3.app.state.AppState;

/**
 * The JMonkeyEngine game entry, you should only do initializations for your game here, game logic is handled by
 * Custom states {@link com.jme3.app.state.BaseAppState}, Custom controls {@link com.jme3.scene.control.AbstractControl}
 * and your custom entities implementations of the previous.
 *
 */
public class SciopoInMan extends SimpleApplication {
    private WorldRenderer worldRenderer;
    private WorldState worldState;

    public SciopoInMan() {
    }

    public SciopoInMan(AppState... initialStates) {
        super(initialStates);
    }

   @Override
   public void simpleInitApp() {

       flyCam.setEnabled(true);

       worldState = new WorldState();
       worldRenderer = new WorldRenderer(assetManager, rootNode);
   }
}