package daderpduck.medicraft.effects.injuries;

import daderpduck.medicraft.init.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class BloodLoss {

	public BloodLoss(EntityPlayer player, int duration) {
		PotionEffect bleedingEffect = new PotionEffect(ModPotions.BLEEDING, duration);

		player.addPotionEffect(bleedingEffect);
	}
}
