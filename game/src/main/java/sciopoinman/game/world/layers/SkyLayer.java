package sciopoinman.game.world.layers;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.SkyFactory;

public class SkyLayer{
     public SkyLayer(AssetManager assetManager, Node rootNode) {
        Spatial sky = SkyFactory.createSky(
            assetManager,
            "Textures/sky.png",
            SkyFactory.EnvMapType.EquirectMap
        );
        rootNode.attachChild(sky);
    }
}
