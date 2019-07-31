package daderpduck.medicraft.effects.injuries.shaders;

import daderpduck.medicraft.effects.shaders.Blur;
import daderpduck.medicraft.effects.shaders.Desaturate;
import daderpduck.medicraft.shaders.Visual;
import daderpduck.medicraft.shaders.VisualHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BloodLossShaders {
	public static final Desaturate DESATURATE = setPersistentShader(new Desaturate(-1,1F));
	public static final Blur BLUR = setPersistentShader(new Blur(-1, 0));

	private static final Class currentClass = new Object(){}.getClass().getEnclosingClass();
	private static <T extends Visual> T setPersistentShader(T shader) {
		return VisualHandler.setPersistentShader(currentClass, shader);
	}
}
