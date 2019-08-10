package daderpduck.medicraft.drugs.medicines;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.drugs.Drug;
import net.minecraft.entity.player.EntityPlayer;

public class Blood extends Drug {
	public Blood() {
		super();
	}

	@Override
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {
		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;

		blood.increaseBlood(200);
	}
}
