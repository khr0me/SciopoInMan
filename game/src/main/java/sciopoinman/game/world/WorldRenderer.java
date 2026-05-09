package sciopoinman.game.world;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

import sciopoinman.game.world.layers.GroundLayer;
import sciopoinman.game.world.layers.SkyLayer;

public class WorldRenderer {

    private SkyLayer skyLayer;
    private GroundLayer groundLayer;
    private PhysicsSpace physicsSpace;

    public WorldRenderer(AssetManager assetManager, Node rootNode, PhysicsSpace physicsSpace, Camera cam) {
        skyLayer = new SkyLayer(assetManager, rootNode);
        groundLayer = new GroundLayer(assetManager, rootNode, physicsSpace, cam);
    }
}