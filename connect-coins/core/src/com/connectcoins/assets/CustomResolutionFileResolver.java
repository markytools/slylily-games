/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.connectcoins.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.ResolutionFileResolver.Resolution;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/** This {@link FileHandleResolver} uses a given list of {@link Resolution}s to determine the best match based on the current
 * Screen size. An example of how this resolver works:
 * 
 * <p>
 * Let's assume that we have only a single {@link Resolution} added to this resolver. This resolution has the following
 * properties:
 * </p>
 * 
 * <ul>
 * <li>{@code portraitWidth = 1920}</li>
 * <li>{@code portraitHeight = 1080}</li>
 * <li>{@code folder = "1920x1080"}</li>
 * </ul>
 * 
 * <p>
 * One would now supply a file to be found to the resolver. For this example, we assume it is "{@code textures/walls/brick.png}".
 * Since there is only a single {@link Resolution}, this will be the best match for any screen size. The resolver will now try to
 * find the file in the following ways:
 * </p>
 * 
 * <ul>
 * <li>{@code "textures/walls/1920x1080/brick.png"}</li>
 * <li>{@code "textures/walls/brick.png"}</li>
 * </ul>
 * 
 * <p>
 * The files are ultimately resolved via the given {{@link #baseResolver}. In case the first version cannot be resolved, the
 * fallback will try to search for the file without the resolution folder.
 * </p> */
public class CustomResolutionFileResolver implements FileHandleResolver {
	protected final AutoFileResolver baseResolver;
	protected final Resolution[] descriptors;
	protected final Array<String> exceptions;
	private boolean lowMemory;

	/** Creates a {@code ResolutionFileResolver} based on a given {@link FileHandleResolver} and a list of {@link Resolution}s.
	 * @param baseResolver The {@link FileHandleResolver} that will ultimately used to resolve the file.
	 * @param descriptors A list of {@link Resolution}s. At least one has to be supplied. */
	public CustomResolutionFileResolver (AutoFileResolver baseResolver, boolean lowMemory, Resolution... descriptors) {
		if (descriptors.length == 0) throw new IllegalArgumentException("At least one Resolution needs to be supplied.");
		this.baseResolver = baseResolver;
		this.descriptors = descriptors;
		this.lowMemory = lowMemory;
		exceptions = new Array<String>();
	}
	
	/**
	 * @param exception the filename
	 */
	public void addExceptions(String exception){
		exceptions.add(exception);
	}

	public AutoFileResolver getBaseResolver() {
		return baseResolver;
	}
	
	public void clearExceptions(){
		exceptions.clear();
	}

	@Override
	public FileHandle resolve (String fileName) {
		Resolution bestResolution;
		if (lowMemory){
			if (exceptions.contains(fileName, false)){
				bestResolution = choose(descriptors);
			}
			else {
				bestResolution = descriptors[0];
			}
		}
		else {
			bestResolution = choose(descriptors);
		}
		FileHandle originalHandle = new FileHandle(fileName);
		FileHandle handle = baseResolver.resolve(resolve(originalHandle, bestResolution.folder));
		if (!handle.exists()) handle = baseResolver.resolve(fileName);
		return handle;
	}

	public Resolution getBestResolution(){
		Resolution bestResolution;
		if (lowMemory){
			bestResolution = descriptors[0];
		}
		else {
			bestResolution = choose(descriptors);
		}
		return bestResolution;
	}

	protected String resolve (FileHandle originalHandle, String suffix) {
		String parentString = "";
		FileHandle parent = originalHandle.parent();
		if (parent != null && !parent.name().equals("")) {
			parentString = parent + "/";
		}
		return parentString + suffix + "/" + originalHandle.name();
	}

	static public Resolution choose (Resolution... descriptors) {
		int w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();

		// Prefer the shortest side.
		Resolution best = descriptors[0];
		if (w < h) {
			for (int i = 0, n = descriptors.length; i < n; i++) {
				Resolution other = descriptors[i];
				if (w >= other.portraitWidth && other.portraitWidth >= best.portraitWidth && h >= other.portraitHeight
					&& other.portraitHeight >= best.portraitHeight) best = descriptors[i];
			}
		} else {
			for (int i = 0, n = descriptors.length; i < n; i++) {
				Resolution other = descriptors[i];
				if (w >= other.portraitHeight && other.portraitHeight >= best.portraitHeight && h >= other.portraitWidth
					&& other.portraitWidth >= best.portraitWidth) best = descriptors[i];
			}
		}
		return best;
	}
}
