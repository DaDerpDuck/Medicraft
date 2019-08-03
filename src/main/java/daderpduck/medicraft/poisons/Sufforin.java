package daderpduck.medicraft.poisons;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import net.minecraft.entity.player.EntityPlayer;

public class Sufforin extends Poison {
	private static final int startDelay = 30000;
	private static final int endTime = 120000 + startDelay;

	public Sufforin() {
		super(2, 120*20, 30*20, 30*20);
	}

	@Override
	public void poisonEffect(EntityPlayer player, int poisonDuration, int amplifier) {
		System.out.println(amplifier);

		IBlood blood = player.getCapability(BloodCapability.CAP_BLOOD, null);
		assert blood != null;

		blood.decrease(0.8F + (0.2F*amplifier));

		if (poisonDuration <= 0) removePoisonFromPlayer(player);
	}
}
