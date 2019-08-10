package daderpduck.medicraft.events;

import daderpduck.medicraft.base.AttributeModifierBase;
import daderpduck.medicraft.base.CustomPotion;
import daderpduck.medicraft.events.message.MessagePain;
import daderpduck.medicraft.init.ModPotions;
import daderpduck.medicraft.network.NetworkHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class InjuryHandler {
	/**
	 * Jumping penalty if player has some leg/foot injury
	 */
	@SubscribeEvent
	public void onJumpEvent(LivingEvent.LivingJumpEvent event) {
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
	public void onFovUpdate(FOVUpdateEvent event) {
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
}
