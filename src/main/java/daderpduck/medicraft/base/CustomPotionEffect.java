package daderpduck.medicraft.base;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class CustomPotionEffect extends PotionEffect {
	public CustomPotionEffect(Potion potionIn) {
		super(potionIn);
	}

	public CustomPotionEffect(Potion potionIn, int durationIn) {
		super(potionIn, durationIn);
	}

	public CustomPotionEffect(Potion potionIn, int durationIn, int amplifierIn) {
		super(potionIn, durationIn, amplifierIn);
	}

	public CustomPotionEffect(Potion potionIn, int durationIn, int amplifierIn, boolean ambientIn, boolean showParticlesIn) {
		super(potionIn, durationIn, amplifierIn, ambientIn, showParticlesIn);
	}

	public CustomPotionEffect(PotionEffect other) {
		super(other);
	}
}
