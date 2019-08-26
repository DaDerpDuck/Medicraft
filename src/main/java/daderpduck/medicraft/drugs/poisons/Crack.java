package daderpduck.medicraft.drugs.poisons;

import daderpduck.medicraft.drugs.Drug;
import daderpduck.medicraft.effects.injuries.shaders.CrackShaders;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class Crack extends Drug {
	public Crack() {
		super(-1);
		setInitialDuration(180*20);
		setDurationIncrement(60*20);
	}

	@Override
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {
		if (player.world.isRemote) {
			//((EntityPlayerSP) player).playSound();
			CrackShaders.CRACKED.lineOpacity = MathHelper.clamp(.0017F*drugDuration - .02F, 0, 1);
			CrackShaders.CRACKED.crackScale = MathHelper.clamp(.0034F*drugDuration - .02F, 0, 2);
		}
	}
}
