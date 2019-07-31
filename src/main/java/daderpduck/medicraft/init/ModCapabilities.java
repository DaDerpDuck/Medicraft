package daderpduck.medicraft.init;

import daderpduck.medicraft.capabilities.BloodCapability;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class ModCapabilities {
	private static final CapabilityManager INSTANCE = CapabilityManager.INSTANCE;

	public static void registerCapabilities() {
		BloodCapability.register(INSTANCE);
	}
}
