package daderpduck.medicraft.events;

import daderpduck.medicraft.base.AttributeModifierBase;
import daderpduck.medicraft.base.CustomPotion;
import daderpduck.medicraft.blood.BloodRegen;
import daderpduck.medicraft.blood.BloodRegenModifier;
import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.effects.injuries.BrokenLeg;
import daderpduck.medicraft.effects.injuries.Concussion;
import daderpduck.medicraft.effects.injuries.SprainedAnkle;
import daderpduck.medicraft.effects.injuries.shaders.BloodLossShaders;
import daderpduck.medicraft.effects.injuries.shaders.BrainSwellingShaders;
import daderpduck.medicraft.effects.injuries.shaders.ConcussionShaders;
import daderpduck.medicraft.events.message.MessageExplodeDamage;
import daderpduck.medicraft.events.message.MessageHurt;
import daderpduck.medicraft.events.message.MessagePain;
import daderpduck.medicraft.init.ModBloodRegenModifier;
import daderpduck.medicraft.init.ModDamageSources;
import daderpduck.medicraft.init.ModPotions;
import daderpduck.medicraft.network.NetworkHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;
import java.util.UUID;

@EventBusSubscriber
public class WorldEvents {

	/**
	 * Deals with damage..
	 */
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onDamageEvent(LivingDamageEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		DamageSource damageSource = event.getSource();
		float damage = event.getAmount();

		if (entity instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) entity;

			//Hurt
			NetworkHandler.FireClient(new MessageHurt(damage), player);

			//Explosion
			if (damageSource.isExplosion()) {
				NetworkHandler.FireClient(new MessageExplodeDamage(damage), player);

				float f = damage / player.getMaxHealth() / 2F;
				float rand = player.world.rand.nextFloat();

				if (f > rand) {
					new Concussion(player, 8400);
				}
			}

			//Fall damage
			if (damageSource == DamageSource.FALL) {
				float f = damage / player.getMaxHealth();
				float rand = player.world.rand.nextFloat();

				if (player.isPotionActive(ModPotions.BROKEN_LEG)) {
					//Player fell with broken legs (idiot)
					event.setAmount(damage * 2);

					NetworkHandler.FireClient(new MessagePain(2), player);
				} else {
					//Player fell without broken legs
					if (f >= 0.5F && f > rand) {
						player.sendMessage(new TextComponentTranslation("injury.medicraft.broken_leg"));
						new BrokenLeg(player, (int) (f * 12000));

						NetworkHandler.FireClient(new MessagePain(2), player);
					}
				}

				if (player.isPotionActive(ModPotions.SPRAINED_ANKLE)) {
					//Player fell with sprained ankle
					event.setAmount(damage * 1.1F);

					NetworkHandler.FireClient(new MessagePain(0.9F), player);
				} else {
					//Player fell without sprained ankle
					if (f < 0.5F && f > rand) {
						player.sendMessage(new TextComponentTranslation("injury.medicraft.sprained_ankle"));
						new SprainedAnkle(player, (int) (f * 6000));

						NetworkHandler.FireClient(new MessagePain(1.2F), player);
					}
				}
			}
		}
	}

	/**
	 * Activates injury effects
	 */
	@SubscribeEvent
	public static void onPlayerTick(TickEvent.PlayerTickEvent event) {

		if (event.phase == TickEvent.Phase.START) {
			/* COMMON */
			EntityPlayer player = event.player;
			IBlood bloodCap = player.getCapability(BloodCapability.CAP_BLOOD, null);

			BloodRegenModifier bloodRegenModifier = BloodRegen.getBloodRegenModifier(player, ModBloodRegenModifier.BLEEDING_MODIFIER.getId());
			if (bloodRegenModifier != null) {
				if (player.isPotionActive(ModPotions.BLEEDING)) {
					bloodRegenModifier.setModifier(-1);
				} else {
					bloodRegenModifier.setModifier(0);
				}
			}

			/* SERVER */
			if (event.side.isServer()) {
				for (CustomPotion potion : ModPotions.POTIONS) {

					//Remove modifiers
					if (potion.getAttribute() != null && potion.getAttributeModifier() != null) {
						IAttributeInstance attribute = player.getEntityAttribute(potion.getAttribute());

						if (attribute.getModifier(potion.getAttributeModifier().getID()) != null)
							attribute.removeModifier(potion.getAttributeModifier());
					}

					if (player.isPotionActive(potion)) {
						//Hide potion particles
						if (potion.isParticlesHidden()) {
							PotionEffect effect = player.getActivePotionEffect(potion);
							ReflectionHelper.setPrivateValue(PotionEffect.class, effect, -1, "amplifier", "field_76461_c");

						}

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
				assert bloodCap != null;
				if (bloodCap.getBlood() <= 0) {
					player.attackEntityFrom(ModDamageSources.BLOOD_LOSS, Float.MAX_VALUE);
				}
			}

			/* CLIENT */
			if (event.side.isClient()) {
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

				//Exsanguination (Client)
				assert bloodCap != null;
				float bloodRatio = bloodCap.getBlood() / bloodCap.getMaxBlood();
				BloodLossShaders.DESATURATE.saturation = Math.min(bloodRatio * 2F, 1);
				BloodLossShaders.BLUR.radius = (int) Math.max(-10 * bloodRatio + 4.5, 0);
			}
		}
	}

	/**
	 * Give some disease if eating specific foods
	 */
	@SubscribeEvent
	public static void onEaten(LivingEntityUseItemEvent.Finish event) {

	}

	/**
	 * Jumping penalty if player has some leg/foot injury
	 */
	@SubscribeEvent
	public static void onJumpEvent(LivingEvent.LivingJumpEvent event) {
		EntityLivingBase entity = event.getEntityLiving();

		if (entity instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP) entity;
			EntityPlayer player = (EntityPlayer) entity;

			if (player.world.isRemote) {
				for (CustomPotion potion : ModPotions.POTIONS) {
					if (player.isPotionActive(potion) && potion.isUnPressKeysOnJump()) {
						KeyBinding.unPressAllKeys();
					}
				}

				if (player.isPotionActive(ModPotions.BROKEN_LEG))
					player.motionY /= 2F;
				if (player.isPotionActive(ModPotions.SPRAINED_ANKLE))
					player.motionY /= 2F;
			} else {
				for (CustomPotion potion : ModPotions.POTIONS) {
					if (player.isPotionActive(potion) && potion.getDamageOnJump() != 0) {
						player.attackEntityFrom(potion.getDamageSourceOnJump(), potion.getDamageOnJump());
					}
				}

				if (player.isPotionActive(ModPotions.BROKEN_LEG)) {

					NetworkHandler.FireClient(new MessagePain(0.6F), playerMP);
				}
				if (player.isPotionActive(ModPotions.SPRAINED_ANKLE)) {
					NetworkHandler.FireClient(new MessagePain(0.2F), playerMP);
				}
			}

		}
	}

	/**
	 * Cancels FOV change from various injuries
	 */
	@SubscribeEvent
	public static void onFovUpdate(FOVUpdateEvent event) {
		EntityPlayer player = event.getEntity();

		float f = 1F;

		if (player.capabilities.isFlying) {
			f *= 1.1F;
		}

		IAttributeInstance movement = player.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
		float modifier = 1F;

		for (UUID id : AttributeModifierBase.getExcludeFromFOV()) {
			AttributeModifier attr = movement.getModifier(id);
			if (attr != null)
				modifier *= 1F + attr.getAmount();
		}

		double oldValue = movement.getAttributeValue() / modifier;
		f = (float) ((double) f * (((oldValue / (double) player.capabilities.getWalkSpeed()) + 1D) / 2D));

		if (player.capabilities.getWalkSpeed() == 0 || Float.isNaN(f) || Float.isInfinite(f)) {
			f = 1F;
		}

		if (player.isHandActive() && (player.getActiveItemStack().getItem() == Items.BOW)) {
			int i = player.getItemInUseMaxCount();
			float f1 = (float) i / 20F;

			if (f1 > 1F) {
				f1 = 1F;
			} else {
				f1 = f1 * f1;
			}

			f *= 1F - f1 * 0.15F;
		}

		event.setNewfov(f);
	}

	/**
	 * Sends a warning if a player has Optifine's fast render setting enabled
	 */
	@SubscribeEvent
	public static void onPlayerJoinClient(EntityJoinWorldEvent event) {
		if (event.getEntity() instanceof EntityPlayer && event.getWorld().isRemote) {
			try {
				@SuppressWarnings("JavaReflectionMemberAccess")
				Field fastRender = GameSettings.class.getDeclaredField("ofFastRender");

				if (fastRender.getBoolean(Minecraft.getMinecraft().gameSettings)) {
					event.getEntity().sendMessage(new TextComponentTranslation("warning.medicraft.optifine.fastrender"));
				}
			} catch (NoSuchFieldException | IllegalAccessException ignored) {}
		}
	}
}
