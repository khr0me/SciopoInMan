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
import com.jme3.terrain.heightmap.ImageBasedHeightMap;

import com.jme3.texture.Texture;

public class GroundLayer {
    private TerrainQuad terrain;
    private Material matRock;
    private float fangoScale = 1024;
    private float dirtScale = 32;
    private float rockScale = 128;

    public GroundLayer(AssetManager assetManager, Node rootNode, PhysicsSpace physicsSpace, Camera cam) {

        Texture heightMapImage = assetManager.loadTexture("Textures/Terrain/splat/HeightMapTrenches.png");
        matRock = new Material(assetManager, "Common/MatDefs/Terrain/Terrain.j3md");
        matRock.setBoolean("useTriPlanarMapping", false);
        matRock.setTexture("Alpha", assetManager.loadTexture("Textures/Terrain/splat/alphamapTrenches.png"));

        // GRASS texture
        Texture fango = assetManager.loadTexture("Textures/Terrain/splat/fango.png");
        fango.setWrap(Texture.WrapMode.Repeat);
        matRock.setTexture("Tex1", fango);
        matRock.setFloat("Tex1Scale", fangoScale);

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

        AbstractHeightMap heightmap = null;
        try {
            // heightmap = new HillHeightMap(513, 600, 10, 40, (byte) 3);
            heightmap = new ImageBasedHeightMap(heightMapImage.getImage(), 120f);
            heightmap.load();
            heightmap.smooth(0.9f, 2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        terrain = new TerrainQuad("terrain", 65, 1025, heightmap.getHeightMap());
        TerrainLodControl control = new TerrainLodControl(terrain, cam);
        control.setLodCalculator( new DistanceLodCalculator(65, 2.7f) ); // patch size, and a multiplier
        terrain.addControl(control);
        terrain.setMaterial(matRock);
        terrain.setLocalTranslation(-50f, -300f, 50f);
        terrain.setLocalScale(0.6f, 0.009f, 0.6f);

        RigidBodyControl groundPhysics = new RigidBodyControl(0f);
        terrain.addControl(groundPhysics);
        rootNode.attachChild(terrain);
        physicsSpace.add(groundPhysics);
    }
}