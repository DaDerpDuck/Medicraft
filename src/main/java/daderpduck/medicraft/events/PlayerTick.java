package daderpduck.medicraft.events;

import daderpduck.medicraft.base.CustomPotion;
import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.DrugCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.capabilities.IDrug;
import daderpduck.medicraft.drugs.Drug;
import daderpduck.medicraft.effects.injuries.shaders.BloodLossShaders;
import daderpduck.medicraft.effects.injuries.shaders.BrainSwellingShaders;
import daderpduck.medicraft.effects.injuries.shaders.ConcussionShaders;
import daderpduck.medicraft.init.ModDamageSources;
import daderpduck.medicraft.init.ModDrugs;
import daderpduck.medicraft.init.ModPotions;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class PlayerTick {
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

		if (event.phase == TickEvent.Phase.END) {
			onCommonPlayerTick(event);

			if (event.side.isServer()) {
				onServerPlayerTick(event);
			} else if (event.side.isClient()) {
				onClientPlayerTick(event);
			}
		}
	}

	/**
	 * Called on client and server
	 * Currently only used for decreasing blood for bleeding effect
	 */
	private static void onCommonPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;

		IBlood bloodCap = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert bloodCap != null;
		IDrug drugCap = player.getCapability(DrugCapability.CAP_DRUG, null);
		assert drugCap != null;

		if (player.isPotionActive(ModPotions.BLEEDING)) {
			PotionEffect bleedingEffect = player.getActivePotionEffect(ModPotions.BLEEDING);
			assert bleedingEffect != null;

			bloodCap.decreaseBlood(0.5F*(bleedingEffect.getAmplifier() + 1));
		}
	}

	/**
	 * Called on server
	 * Applies modifiers and damages players from injuries
	 */
	private static void onServerPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;

		IBlood bloodCap = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert bloodCap != null;
		IDrug drugCap = player.getCapability(DrugCapability.CAP_DRUG, null);
		assert drugCap != null;

		double bloodRatio = (bloodCap.getBlood()/bloodCap.getMaxBlood())*(bloodCap.getOxygen()/bloodCap.getMaxOxygen());

		for (CustomPotion potion : ModPotions.POTIONS) {

			//Remove modifiers
			if (potion.getAttribute() != null && potion.getAttributeModifier() != null) {
				IAttributeInstance attribute = player.getEntityAttribute(potion.getAttribute());

				if (attribute.getModifier(potion.getAttributeModifier().getID()) != null)
					attribute.removeModifier(potion.getAttributeModifier());
			}

			if (player.isPotionActive(potion)) {
				//Apply modifiers
				if (potion.getAttribute() != null && potion.getAttributeModifier() != null) {
					IAttributeInstance attribute = player.getEntityAttribute(potion.getAttribute());
					attribute.applyModifier(potion.getAttributeModifier());
				}
			}
		}

		/* CUSTOM POTION EFFECTS */

		//Brain Swelling
		if (player.isPotionActive(ModPotions.BRAIN_SWELLING)) {
			PotionEffect brainSwellEffect = player.getActivePotionEffect(ModPotions.BRAIN_SWELLING);

			assert brainSwellEffect != null;
			if (brainSwellEffect.getDuration() <= 20) {
				player.attackEntityFrom(ModDamageSources.BRAIN_SWELLING, Float.MAX_VALUE);
			}
		}

		/* OTHER */

		//Exsanguination
		System.out.println(bloodRatio);
		if (bloodRatio <= 0) {
			player.attackEntityFrom(ModDamageSources.BLOOD_LOSS, Float.MAX_VALUE);
		}
	}

	/**
	 * Called on client
	 * Activates shaders
	 */
	private static void onClientPlayerTick(TickEvent.PlayerTickEvent event) {
		EntityPlayer player = event.player;

		IBlood bloodCap = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert bloodCap != null;
		IDrug drugCap = player.getCapability(DrugCapability.CAP_DRUG, null);
		assert drugCap != null;

		double bloodRatio = (bloodCap.getBlood()/bloodCap.getMaxBlood())*(bloodCap.getOxygen()/bloodCap.getMaxOxygen());

		/* CUSTOM POTION EFFECTS */

		//Concussion (Client)
		if (player.isPotionActive(ModPotions.CONCUSSION)) {
			PotionEffect concussionEffect = player.getActivePotionEffect(ModPotions.CONCUSSION);

			assert concussionEffect != null;
			float f = Math.min(concussionEffect.getDuration() / 8400F, 1);

			ConcussionShaders.BLOBS.radius = (int) (f * 7);
			ConcussionShaders.DOUBLE_VISION.distance = f * 0.2F;
			ConcussionShaders.DOUBLE_VISION.intensity = f;
		} else {
			ConcussionShaders.BLOBS.radius = 0;
			ConcussionShaders.DOUBLE_VISION.distance = 0;
			ConcussionShaders.DOUBLE_VISION.intensity = 0;
		}

		//Brain Swelling (Client)
		if (player.isPotionActive(ModPotions.BRAIN_SWELLING)) {
			PotionEffect brainSwellEffect = player.getActivePotionEffect(ModPotions.BRAIN_SWELLING);

			assert brainSwellEffect != null;
			float f = brainSwellEffect.getDuration();

			BrainSwellingShaders.VIGNETTE.strength = 250F / (2F * f + 50F); //y = a*b/(c*x+b)
		} else {
			BrainSwellingShaders.VIGNETTE.strength = 0;
		}

		/* OTHER */
		double fBloodRatio = bloodRatio;

		//Sufforin effect
		Drug.DrugEffect sufforinEffect = drugCap.getActiveDrug(ModDrugs.SUFFORIN);
		if (sufforinEffect != null && sufforinEffect.drugDelay <= 0) {
			fBloodRatio = (float) MathHelper.clampedLerp(bloodRatio, 1, sufforinEffect.drugDuration/100D);
		}

		BloodLossShaders.DESATURATE.saturation = (float) Math.min(fBloodRatio * 2F, 1);
		BloodLossShaders.BLUR.radius = (int) Math.max(-10 * fBloodRatio + 4.5, 0);
		BloodLossShaders.TINT.opacity = (float) MathHelper.clamp(bloodRatio*-12.5F + 1,0,1);
	}
}
