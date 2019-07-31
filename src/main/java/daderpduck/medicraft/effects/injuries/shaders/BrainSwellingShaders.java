package daderpduck.medicraft.effects.injuries.shaders;

import daderpduck.medicraft.effects.shaders.Vignette;
import daderpduck.medicraft.shaders.Visual;
import daderpduck.medicraft.shaders.VisualHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector3f;

@SideOnly(Side.CLIENT)
public class BrainSwellingShaders {
	public static final Vignette VIGNETTE = setPersistentShader(new Vignette(-1, new Vector3f(0.9F, 0, 0), 0));

	private static final Class currentClass = new Object(){}.getClass().getEnclosingClass();
	private static <T extends Visual> T setPersistentShader(T shader) {
		return VisualHandler.setPersistentShader(currentClass, shader);
	}
}
