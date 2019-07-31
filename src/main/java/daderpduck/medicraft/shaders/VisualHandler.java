package daderpduck.medicraft.shaders;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class VisualHandler {
	protected static List<Visual> overlayShaders = new LinkedList<>();
	protected static HashMap<Object, HashMap<Class, Visual>> singleShaders = new HashMap<>();
	protected static HashMap<Object, HashMap<Class, Visual>> persistentShaders = new HashMap<>();

	private static List<Visual> toRemove = new LinkedList<>();

	/** Layers shaders on each other */
	public static <T extends Visual> T addShader(T shader) {
		overlayShaders.add(shader);

		return shader;
	}

	/** Allows only one type of shader at a time */
	public static <T extends Visual> T setSingleShader(Object key, T shader) {
		if (!singleShaders.containsKey(key)) {
			singleShaders.put(key, new HashMap<>());
		}

		singleShaders.get(key).put(shader.getClass(), shader);

		return shader;
	}

	/** Same as setSingleShader, but cannot be removed */
	public static <T extends Visual>  T setPersistentShader(Object key, T shader) {
		if (!persistentShaders.containsKey(key)) {
			persistentShaders.put(key, new HashMap<>());
		}

		if (!persistentShaders.get(key).containsValue(shader.getClass())) {
			persistentShaders.get(key).put(shader.getClass(), shader);
		}

		return shader;
	}

	public static void removeShader(Visual shader) {
		toRemove.add(shader);
	}

	public static void flushShaders() {
		for (Visual shader : toRemove) {
			int index = overlayShaders.indexOf(shader);
			if (index >= 0) {
				overlayShaders.remove(index);
			}

			for (HashMap map : singleShaders.values()) {
				if (map.containsKey(shader.getClass())) {
					singleShaders.remove(shader.getClass());
				}
			}

			shader.getShaderGroup().deleteShaderGroup();
		}

		toRemove.clear();
	}

	public static void resetShaders() {
		overlayShaders.clear();
		singleShaders.clear();
	}
}
