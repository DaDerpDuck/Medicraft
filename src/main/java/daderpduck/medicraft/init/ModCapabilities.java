package daderpduck.medicraft.init;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.DrugCapability;

public class ModCapabilities {
	public static void registerCapabilities() {
		BloodCapability.register();
		DrugCapability.register();
	}
}
