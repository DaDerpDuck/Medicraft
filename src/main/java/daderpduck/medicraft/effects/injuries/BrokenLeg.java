package daderpduck.medicraft.effects.injuries;

import daderpduck.medicraft.init.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;

public class BrokenLeg {

	public BrokenLeg(EntityPlayer player, int duration) {
		PotionEffect brokenLegEffect = new PotionEffect(ModPotions.BROKEN_LEG, duration, -1);
		brokenLegEffect.setCurativeItems(new ArrayList<>());

		player.addPotionEffect(brokenLegEffect);
	}
}
