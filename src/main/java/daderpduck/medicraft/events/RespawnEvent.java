package daderpduck.medicraft.events;

import daderpduck.medicraft.capabilities.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import java.util.LinkedList;

public class RespawnEvent {
	@SubscribeEvent
	public void onRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
		if (event.isEndConquered()) return;

		EntityPlayerMP player = (EntityPlayerMP) event.player;

		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;
		blood.setOxygen(blood.getMaxOxygen());
		blood.setBlood(blood.getMaxBlood());
		blood.sync(player);

		IDrug drug = player.getCapability(DrugCapability.CAP_DRUG, null);
		assert drug != null;
		drug.setDrugs(new LinkedList<>());
		drug.sync(player);

		IUnconscious unconscious = player.getCapability(UnconsciousCapability.CAP_UNCONSCIOUS, null);
		assert unconscious != null;
		unconscious.setUnconscious(false);
		unconscious.sync(player);
	}
}
