package daderpduck.medicraft.effects.injuries.shaders;

import daderpduck.medicraft.effects.shaders.Desaturate;
import daderpduck.medicraft.effects.shaders.StaticOverlay;
import daderpduck.medicraft.shaders.Visual;
import daderpduck.medicraft.shaders.VisualHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UnconsciousShaders {
	public static final StaticOverlay STATIC_OVERLAY = setPersistentShader(new StaticOverlay(-1, 0));
	public static final Desaturate DESATURATE = setPersistentShader(new Desaturate(-1, 1));

	private static final int id = 3;

	private static <T extends Visual> T setPersistentShader(T shader) {
		return VisualHandler.setPersistentShader(id, shader);
	}
}
