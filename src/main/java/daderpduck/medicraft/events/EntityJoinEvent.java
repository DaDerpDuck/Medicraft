package daderpduck.medicraft.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class EntityJoinEvent {
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
