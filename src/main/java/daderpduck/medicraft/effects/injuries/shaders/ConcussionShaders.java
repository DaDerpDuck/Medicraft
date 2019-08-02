package daderpduck.medicraft.effects.injuries.shaders;

import daderpduck.medicraft.effects.shaders.Blobs2;
import daderpduck.medicraft.effects.shaders.DoubleVision;
import daderpduck.medicraft.shaders.Visual;
import daderpduck.medicraft.shaders.VisualHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ConcussionShaders {
	public static final Blobs2 BLOBS = setPersistentShader(new Blobs2(-1, 0));
	public static final DoubleVision DOUBLE_VISION = setPersistentShader(new DoubleVision(-1, 0, 0));

	private static final Class currentClass = new Object() {
	}.getClass().getEnclosingClass();

	private static <T extends Visual> T setPersistentShader(T shader) {
		return VisualHandler.setPersistentShader(currentClass, shader);
	}
}
