package com.kerman.core.graphics.k2d;

import com.kerman.core.assets.AssetDescriptor;
import com.kerman.core.assets.AssetLoaderParameters;
import com.kerman.core.assets.AssetManager;
import com.kerman.core.assets.loaders.FileHandleResolver;
import com.kerman.core.assets.loaders.SynchronousAssetLoader;
import com.kerman.core.assets.loaders.resolvers.InternalFileHandleResolver;
import com.kerman.core.files.FileHandle;
import com.kerman.core.graphics.Texture;
import com.kerman.core.math.EarClippingTriangulator;
import com.kerman.core.utils.KermanArray;
import com.kerman.core.utils.KermanRuntimeException;
import com.kerman.core.utils.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g2d.PolygonRegionLoader".
 * <p>
 * loads {@link PolygonRegion PolygonRegions} using a {@link com.kerman.core.graphics.k2d.PolygonRegionLoader}
 */
public class PolygonRegionLoader extends SynchronousAssetLoader<PolygonRegion, PolygonRegionLoader.PolygonRegionParameters> {

    private final PolygonRegionParameters defaultParameters = new PolygonRegionParameters();
    private final EarClippingTriangulator triangulator = new EarClippingTriangulator();

    public PolygonRegionLoader() {
        this(new InternalFileHandleResolver());
    }

    public PolygonRegionLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public PolygonRegion load(AssetManager manager, String fileName, FileHandle file, PolygonRegionParameters parameter) {
        Texture texture = manager.get(manager.getDependencies(fileName).first());
        return load(new TextureRegion(texture), file);
    }

    /**
     * If the PSH file contains a line starting with {@link PolygonRegionParameters#texturePrefix params.texturePrefix}, an
     * {@link AssetDescriptor} for the file referenced on that line will be added to the returned Array. Otherwise a sibling of the
     * given file with the same name and the first found extension in {@link PolygonRegionParameters#textureExtensions
     * params.textureExtensions} will be used. If no suitable file is found, the returned Array will be empty.
     */
    @Override
    public KermanArray<AssetDescriptor> getDependencies(String fileName, FileHandle file, PolygonRegionParameters params) {
        if (params == null) params = defaultParameters;
        String image = null;
        try {
            BufferedReader reader = file.reader(params.readerBuffer);
            for (String line = reader.readLine(); line != null; line = reader.readLine())
                if (line.startsWith(params.texturePrefix)) {
                    image = line.substring(params.texturePrefix.length());
                    break;
                }
            reader.close();
        } catch (IOException e) {
            throw new KermanRuntimeException("Error reading " + fileName, e);
        }

        if (image == null && params.textureExtensions != null) for (String extension : params.textureExtensions) {
            FileHandle sibling = file.sibling(file.nameWithoutExtension().concat("." + extension));
            if (sibling.exists()) image = sibling.name();
        }

        if (image != null) {
            KermanArray<AssetDescriptor> deps = new KermanArray<>(1);
            deps.add(new AssetDescriptor<>(file.sibling(image), Texture.class));
            return deps;
        }

        return null;
    }

    /**
     * Loads a PolygonRegion from a PSH (Polygon SHape) file. The PSH file format defines the polygon vertices before
     * triangulation:
     * <p>
     * s 200.0, 100.0, ...
     * <p>
     * Lines not prefixed with "s" are ignored. PSH files can be created with external tools, eg: <br>
     * <a href="https://code.google.com/p/libgdx-polygoneditor/">example 1</a> <br>
     * <a href="http://www.codeandweb.com/physicseditor/">example 2</a>
     *
     * @param file file handle to the shape definition file.
     */
    public PolygonRegion load(TextureRegion textureRegion, FileHandle file) {
        BufferedReader reader = file.reader(256);
        try {
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                if (line.startsWith("s")) {
                    // Read shape.
                    String[] polygonStrings = line.substring(1).trim().split(",");
                    float[] vertices = new float[polygonStrings.length];
                    for (int i = 0, n = vertices.length; i < n; i++)
                        vertices[i] = Float.parseFloat(polygonStrings[i]);
                    // It would probably be better if PSH stored the vertices and triangles, then we don't have to triangulate here.
                    return new PolygonRegion(textureRegion, vertices, triangulator.computeTriangles(vertices).toArray());
                }
            }
        } catch (IOException ex) {
            throw new KermanRuntimeException("Error reading polygon shape file: " + file, ex);
        } finally {
            StreamUtils.closeQuietly(reader);
        }
        throw new KermanRuntimeException("Polygon shape not found: " + file);
    }

    public static class PolygonRegionParameters extends AssetLoaderParameters<PolygonRegion> {

        /**
         * what the line starts with that contains the file name of the texture for this {@code PolygonRegion}
         */
        public String texturePrefix = "i ";

        /**
         * what buffer size of the reader should be used to read the {@link #texturePrefix} line
         *
         * @see FileHandle#reader(int)
         */
        public int readerBuffer = 1024;

        /**
         * the possible file name extensions of the texture file
         */
        public String[] textureExtensions = new String[]{"png", "PNG", "jpeg", "JPEG", "jpg", "JPG", "cim", "CIM", "etc1", "ETC1",
                "ktx", "KTX", "zktx", "ZKTX"};
    }
}
