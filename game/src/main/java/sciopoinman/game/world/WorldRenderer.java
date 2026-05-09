package sciopoinman.game.world;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;

import sciopoinman.game.world.layers.GroundLayer;
import sciopoinman.game.world.layers.SkyLayer;

public class WorldRenderer {

    @SuppressWarnings("unused")
    private final SkyLayer skyLayer;
    @SuppressWarnings("unused")
    private final GroundLayer groundLayer;

    public WorldRenderer(AssetManager assetManager, Node rootNode, PhysicsSpace physicsSpace, Camera cam) {
        this.skyLayer = new SkyLayer(assetManager, rootNode);
        this.groundLayer = new GroundLayer(assetManager, rootNode, physicsSpace, cam);
    }
}