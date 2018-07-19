package com.connectcoins.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Mark Ty on 10/14/2016.
 */
public class AutoFileResolver implements FileHandleResolver {

    public AssetLabel.FileDest fileDest;

    public AutoFileResolver() {

    }

    @Override
    public FileHandle resolve(String fileName) {
        FileHandle fileHandle = Gdx.files.internal(fileName);
        if (!fileHandle.exists()) fileHandle = Gdx.files.internal(fileName);
        return fileHandle;
    }

    public void setFileDest(AssetLabel.FileDest fileDest) {
        this.fileDest = fileDest;
    }
}
