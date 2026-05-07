package sciopoinman.game.world;

import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import sciopoinman.game.world.layers.SkyLayer;
import sciopoinman.game.world.layers.GroundLayer;
import com.jme3.renderer.Camera;

public class WorldRenderer {

    private SkyLayer skyLayer;
    private GroundLayer groundLayer;

    public WorldRenderer(AssetManager assetManager, Node rootNode) {
        skyLayer = new SkyLayer(assetManager, rootNode);
        groundLayer = new GroundLayer(assetManager, rootNode);
    }
}