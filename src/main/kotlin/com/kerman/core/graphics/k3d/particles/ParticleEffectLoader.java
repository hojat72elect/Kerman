package com.kerman.core.graphics.k3d.particles;

import com.kerman.core.Kerman;
import com.kerman.core.assets.AssetDescriptor;
import com.kerman.core.assets.AssetLoaderParameters;
import com.kerman.core.assets.AssetManager;
import com.kerman.core.assets.loaders.AsynchronousAssetLoader;
import com.kerman.core.assets.loaders.FileHandleResolver;
import com.kerman.core.files.FileHandle;
import com.kerman.core.graphics.k3d.particles.ResourceData.AssetData;
import com.kerman.core.graphics.k3d.particles.batches.ParticleBatch;
import com.kerman.core.utils.KermanArray;
import com.kerman.core.utils.Json;
import com.kerman.core.utils.JsonWriter;
import com.kerman.core.utils.ObjectMap;

import java.io.IOException;

/**
 * Info : This class was inspired by "com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader".
 * <p>
 * This class can save and load a {@link ParticleEffect}. It should be added as {@link AsynchronousAssetLoader} to the
 * {@link AssetManager} so it will be able to load the effects. It's important to note that the two classes
 * {@link ParticleEffectLoadParameter} and {@link ParticleEffectSaveParameter} should be passed in whenever possible, because when
 * present the batches settings will be loaded automatically. When the load and save parameters are absent, once the effect will
 * be created, one will have to set the required batches manually otherwise the {@link ParticleController} instances contained
 * inside the effect will not be able to render themselves.
 */
public class ParticleEffectLoader
        extends AsynchronousAssetLoader<ParticleEffect, ParticleEffectLoader.ParticleEffectLoadParameter> {
    protected final KermanArray<ObjectMap.Entry<String, ResourceData<ParticleEffect>>> items = new KermanArray<>();

    public ParticleEffectLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, ParticleEffectLoadParameter parameter) {
    }

    @Override
    @SuppressWarnings("rawtypes")
    public KermanArray<AssetDescriptor> getDependencies(String fileName, FileHandle file, ParticleEffectLoadParameter parameter) {
        Json json = new Json();
        ResourceData<ParticleEffect> data = json.fromJson(ResourceData.class, file);
        KermanArray<AssetData> assets;
        synchronized (items) {
            ObjectMap.Entry<String, ResourceData<ParticleEffect>> entry = new ObjectMap.Entry<>();
            entry.key = fileName;
            entry.value = data;
            items.add(entry);
            assets = data.getAssets();
        }

        KermanArray<AssetDescriptor> descriptors = new KermanArray<>();
        for (AssetData<?> assetData : assets) {

            // If the asset doesn't exist try to load it from loading effect directory
            if (!resolve(assetData.filename).exists()) {
                assetData.filename = file.parent().child(Kerman.files.internal(assetData.filename).name()).path();
            }

            if (assetData.type == ParticleEffect.class) {
                descriptors.add(new AssetDescriptor(assetData.filename, assetData.type, parameter));
            } else
                descriptors.add(new AssetDescriptor(assetData.filename, assetData.type));
        }

        return descriptors;
    }

    /**
     * Saves the effect to the given file contained in the passed in parameter.
     */
    public void save(ParticleEffect effect, ParticleEffectSaveParameter parameter) throws IOException {
        ResourceData<Object> data = new ResourceData<>(effect);

        // effect assets
        effect.save(parameter.manager, data);

        // Batches configurations
        if (parameter.batches != null) {
            for (ParticleBatch<?> batch : parameter.batches) {
                boolean save = false;
                for (ParticleController controller : effect.getControllers()) {
                    if (controller.renderer.isCompatible(batch)) {
                        save = true;
                        break;
                    }
                }

                if (save) batch.save(parameter.manager, data);
            }
        }

        // save
        Json json = new Json(parameter.jsonOutputType);
        if (parameter.prettyPrint) {
            String prettyJson = json.prettyPrint(data);
            parameter.file.writeString(prettyJson, false);
        } else {
            json.toJson(data, parameter.file);
        }
    }

    @Override
    public ParticleEffect loadSync(AssetManager manager, String fileName, FileHandle file,
                                   ParticleEffectLoadParameter parameter) {
        ResourceData<ParticleEffect> effectData = null;
        synchronized (items) {
            for (int i = 0; i < items.size; ++i) {
                ObjectMap.Entry<String, ResourceData<ParticleEffect>> entry = items.get(i);
                if (entry.key.equals(fileName)) {
                    effectData = entry.value;
                    items.removeIndex(i);
                    break;
                }
            }
        }

        effectData.resource.load(manager, effectData);
        if (parameter != null) {
            if (parameter.batches != null) {
                for (ParticleBatch<?> batch : parameter.batches) {
                    batch.load(manager, (ResourceData) effectData);
                }
            }
            effectData.resource.setBatch(parameter.batches);
        }
        return effectData.resource;
    }

    public static class ParticleEffectLoadParameter extends AssetLoaderParameters<ParticleEffect> {
        KermanArray<ParticleBatch<?>> batches;

        public ParticleEffectLoadParameter(KermanArray<ParticleBatch<?>> batches) {
            this.batches = batches;
        }
    }

    public static class ParticleEffectSaveParameter extends AssetLoaderParameters<ParticleEffect> {
        /**
         * Optional parameters, but should be present to correctly load the settings
         */
        KermanArray<ParticleBatch<?>> batches;

        /**
         * Required parameters
         */
        FileHandle file;
        AssetManager manager;
        JsonWriter.OutputType jsonOutputType;
        boolean prettyPrint;

        public ParticleEffectSaveParameter(FileHandle file, AssetManager manager, KermanArray<ParticleBatch<?>> batches,
                                           JsonWriter.OutputType jsonOutputType, boolean prettyPrint) {
            this.batches = batches;
            this.file = file;
            this.manager = manager;
            this.jsonOutputType = jsonOutputType;
            this.prettyPrint = prettyPrint;
		}
	}
}
