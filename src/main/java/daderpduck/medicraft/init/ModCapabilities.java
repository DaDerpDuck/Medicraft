package daderpduck.medicraft.init;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.DrugCapability;
import daderpduck.medicraft.capabilities.UnconsciousCapability;

public class ModCapabilities {
	public static void registerCapabilities() {
		BloodCapability.register();
		DrugCapability.register();
		UnconsciousCapability.register();
	}
}
