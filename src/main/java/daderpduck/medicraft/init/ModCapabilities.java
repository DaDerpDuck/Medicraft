package daderpduck.medicraft.init;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.PoisonCapability;

public class ModCapabilities {
	public static void registerCapabilities() {
		BloodCapability.register();
		PoisonCapability.register();
	}
}
