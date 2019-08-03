package daderpduck.medicraft.capabilities;

import daderpduck.medicraft.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class CapabilityAttach {
	private static final ArrayList<CapabilityInfo> capabilities = new ArrayList<>();

	static <T> void addCapability(String resourceName, ICapabilityProvider cap, RunnableSyncFunction syncFunction) {
		capabilities.add(new CapabilityInfo(
				new ResourceLocation(Reference.MOD_ID, resourceName),
				cap,
				syncFunction
		));
	}

	interface RunnableSyncFunction {
		void run(PlayerEvent event);
	}

	private static class CapabilityInfo {
		final ResourceLocation resource;
		final ICapabilityProvider cap;
		final RunnableSyncFunction syncFunction;

		CapabilityInfo(ResourceLocation resource, ICapabilityProvider cap, RunnableSyncFunction syncFunction) {
			this.resource = resource;
			this.cap = cap;
			this.syncFunction = syncFunction;
		}
	}

	/* Attach capability to players */
	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof EntityPlayer) {
			for (CapabilityInfo Info : capabilities) {
				event.addCapability(Info.resource, Info.cap);
			}
		}
	}

	/* Syncing data to client */
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		for (CapabilityInfo Info : capabilities) {
			Info.syncFunction.run(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
		for (CapabilityInfo Info : capabilities) {
			Info.syncFunction.run(event);
		}
	}

	@SubscribeEvent
	public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
		for (CapabilityInfo Info : capabilities) {
			Info.syncFunction.run(event);
		}
	}
}
