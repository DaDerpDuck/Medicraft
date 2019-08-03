package daderpduck.medicraft.poisons;

import net.minecraft.entity.player.EntityPlayer;

public class Pneumozine extends Poison {
	public Pneumozine() {
		super(2, 60*20, 20*20, 5*20);
	}

	@Override
	public void poisonEffect(EntityPlayer player, int poisonDuration, int amplifier) {

	}
}
