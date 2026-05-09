package sciopoinman.game.world.layers;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.terrain.geomipmap.TerrainLodControl;
import com.jme3.terrain.geomipmap.TerrainQuad;
import com.jme3.terrain.geomipmap.lodcalc.DistanceLodCalculator;
import com.jme3.terrain.heightmap.AbstractHeightMap;
import com.jme3.terrain.heightmap.HillHeightMap;
import com.jme3.texture.Texture;

public class GroundLayer {
    private final TerrainQuad terrain;
    private Material matRock;
    private float grassScale = 64;
    private float dirtScale = 16;
    private float rockScale = 128;

    public GroundLayer(AssetManager assetManager, Node rootNode, PhysicsSpace physicsSpace, Camera cam) {
            /*Quad q = new Quad(100f, 100f);
            q.scaleTextureCoordinates(new com.jme3.math.Vector2f(17f, 17f));
            Geometry ground = new Geometry("Ground", q);

            // carica texture invece del colore
            Texture tex = assetManager.loadTexture("Textures/ground.png");
            tex.setWrap(Texture.WrapMode.Repeat);

            Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            m.setTexture("ColorMap", tex); // ← setTexture invece di setColor

            ground.setMaterial(m);
            ground.setLocalTranslation(-50f, -5f, 50f);
            ground.rotate(-FastMath.HALF_PI, 0f, 0f);
            rootNode.attachChild(ground);


            */
        matRock = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
        matRock.setBoolean("useTriPlanarMapping", false);
        matRock.setTexture("Alpha", assetManager.loadTexture("Textures/Terrain/splat/alphamap.png"));

        // GRASS texture
        Texture grass = assetManager.loadTexture("Textures/Terrain/splat/grass.jpg");
        grass.setWrap(Texture.WrapMode.Repeat);
        matRock.setTexture("Tex1", grass);
        matRock.setFloat("Tex1Scale", grassScale);

        // DIRT texture
        Texture dirt = assetManager.loadTexture("Textures/Terrain/splat/dirt.jpg");
        dirt.setWrap(Texture.WrapMode.Repeat);
        matRock.setTexture("Tex2", dirt);
        matRock.setFloat("Tex2Scale", dirtScale);

        // ROCK texture
        Texture rock = assetManager.loadTexture("Textures/Terrain/splat/road.jpg");
        rock.setWrap(Texture.WrapMode.Repeat);
        matRock.setTexture("Tex3", rock);
        matRock.setFloat("Tex3Scale", rockScale);

        AbstractHeightMap heightmap;
        try {
            heightmap = new HillHeightMap(513, 600, 1, 40, (byte) 3);
            heightmap.load();
        } catch (Exception e) {
            System.err.println("Failed to load heightmap: " + e.getMessage());
            // Create a default flat heightmap as fallback
            try {
                heightmap = new HillHeightMap(513, 600, 0, 0, (byte) 1);
                heightmap.load();
            } catch (Exception ex) {
                throw new RuntimeException("Failed to create fallback heightmap", ex);
            }
        }

        terrain = new TerrainQuad("terrain", 65, 513, heightmap.getHeightMap());
        TerrainLodControl control = new TerrainLodControl(terrain, cam);
        control.setLodCalculator( new DistanceLodCalculator(65, 2.7f) ); // patch size, and a multiplier
        terrain.addControl(control);
        terrain.setMaterial(matRock);
        terrain.setLocalTranslation(-50f, -300f, 50f);
        terrain.setLocalScale(1f, 0.05f, 1f);

        RigidBodyControl groundPhysics = new RigidBodyControl(0f);
        terrain.addControl(groundPhysics);
        rootNode.attachChild(terrain);
        physicsSpace.add(groundPhysics);
    }
}