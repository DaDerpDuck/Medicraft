package daderpduck.medicraft.effects.injuries.shaders;

import daderpduck.medicraft.effects.shaders.Blur;
import daderpduck.medicraft.effects.shaders.Desaturate;
import daderpduck.medicraft.effects.shaders.Tint;
import daderpduck.medicraft.shaders.Visual;
import daderpduck.medicraft.shaders.VisualHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.util.vector.Vector3f;

@SideOnly(Side.CLIENT)
public class BloodLossShaders {
	public static final Desaturate DESATURATE = setPersistentShader(new Desaturate(-1, 1F));
	public static final Blur BLUR = setPersistentShader(new Blur(-1, 0));
	public static final Tint TINT = setPersistentShader(new Tint(-1, new Vector3f(0,0,0), 0));

	private static final int id = 2;

	private static <T extends Visual> T setPersistentShader(T shader) {
		return VisualHandler.setPersistentShader(id, shader);
	}
}
