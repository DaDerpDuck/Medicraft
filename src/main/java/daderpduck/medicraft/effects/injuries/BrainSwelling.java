package daderpduck.medicraft.effects.injuries;

import daderpduck.medicraft.init.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class BrainSwelling {
	public BrainSwelling(EntityPlayer player, int duration) {
		PotionEffect brainSwell = new PotionEffect(ModPotions.BRAIN_SWELLING, duration, 0);

		player.addPotionEffect(brainSwell);
	}
}
