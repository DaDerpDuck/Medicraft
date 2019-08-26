package daderpduck.medicraft.effects.injuries.shaders;

import daderpduck.medicraft.effects.shaders.Cracked;
import daderpduck.medicraft.shaders.Visual;
import daderpduck.medicraft.shaders.VisualHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.vecmath.Vector2f;

@SideOnly(Side.CLIENT)
public class CrackShaders {
	public static final Cracked CRACKED = setPersistentShader(new Cracked(-1, new Vector2f(.5F, .5F), .5F, .5F, 0, 0));

	private static final int id = 4;

	private static <T extends Visual> T setPersistentShader(T shader) {
		return VisualHandler.setPersistentShader(id, shader);
	}
}
