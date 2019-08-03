package daderpduck.medicraft.effects.injuries;

import daderpduck.medicraft.init.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

public class BrokenLeg {

	public BrokenLeg(EntityPlayer player, int duration) {
		PotionEffect brokenLegEffect = new PotionEffect(ModPotions.BROKEN_LEG, duration, 0);

		player.addPotionEffect(brokenLegEffect);
	}
}
