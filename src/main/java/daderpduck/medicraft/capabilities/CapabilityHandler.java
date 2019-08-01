package daderpduck.medicraft.capabilities;

import daderpduck.medicraft.events.message.MessageClientSyncBlood;
import daderpduck.medicraft.network.NetworkHandler;
import daderpduck.medicraft.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class CapabilityHandler {
	private static final ResourceLocation CAP_BLOOD = new ResourceLocation(Reference.MOD_ID, "blood");

	@SubscribeEvent
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			event.addCapability(CAP_BLOOD, new BloodCapability.BloodProvider());
		}
	}

	/* Syncing data to client */
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		IBlood blood = event.player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;
		NetworkHandler.FireClient(new MessageClientSyncBlood(blood.getBlood(), blood.getMaxBlood()), (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		IBlood blood = event.player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;
		NetworkHandler.FireClient(new MessageClientSyncBlood(blood.getBlood(), blood.getMaxBlood()), (EntityPlayerMP) event.player);
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		IBlood blood = event.player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;
		NetworkHandler.FireClient(new MessageClientSyncBlood(blood.getBlood(), blood.getMaxBlood()), (EntityPlayerMP) event.player);
	}
}
