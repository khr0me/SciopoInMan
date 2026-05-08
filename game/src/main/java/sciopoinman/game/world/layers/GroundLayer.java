    package sciopoinman.game.world.layers;

    import com.jme3.asset.AssetManager;
    import com.jme3.material.Material;
    import com.jme3.math.FastMath;
    import com.jme3.scene.Geometry;
    import com.jme3.scene.Node;
    import com.jme3.scene.shape.Quad;
    import com.jme3.texture.Texture;

    public class GroundLayer {

        public GroundLayer(AssetManager assetManager, Node rootNode) {
            Quad q = new Quad(100f, 100f);
            q.scaleTextureCoordinates(new com.jme3.math.Vector2f(17f, 17f));
            Geometry ground = new Geometry("Ground", q);
            
            // carica texture invece del colore
            Texture tex = assetManager.loadTexture("Textures/ground.png");
            tex.setWrap(Texture.WrapMode.Repeat); // ← ripete la texture invece di stirarla
            
            Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            m.setTexture("ColorMap", tex); // ← setTexture invece di setColor
            
            ground.setMaterial(m);
            ground.setLocalTranslation(-50f, -5f, 50f);
            ground.rotate(-FastMath.HALF_PI, 0f, 0f);
            rootNode.attachChild(ground);
        }
    }