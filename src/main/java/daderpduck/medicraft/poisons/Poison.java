package daderpduck.medicraft.poisons;

import daderpduck.medicraft.capabilities.IPoison;
import daderpduck.medicraft.capabilities.PoisonCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Poison {
	private static int idCounter = 0;
	private static final List<Poison> POISON_LIST = new ArrayList<>();

	public final int id;
	public final int maxAmplifier;
	private final int initialDuration;
	private final int durationIncrement;
	private final int poisonDelay;

	/**
	 * Constructs a poison
	 * @param maxAmplifier Sets the maximum amplifier
	 * @param initialDuration How long poison initially lasts for in ticks
	 * @param durationIncrement Amount to increment in substantial injections
	 * @param poisonDelay How long in ticks until poison effects start firing
	 */
	Poison(int maxAmplifier, int initialDuration, int durationIncrement, int poisonDelay) {
		this.id = idCounter++;
		this.maxAmplifier = maxAmplifier;
		this.initialDuration = initialDuration;
		this.durationIncrement = durationIncrement;
		this.poisonDelay = poisonDelay;

		POISON_LIST.add(this);
	}

	public static class PoisonEffect {
		public final Poison poison;
		public int poisonDuration;
		public int amplifier;
		private final int maxAmplifier;
		public int poisonDelay;

		public PoisonEffect(Poison poison, int poisonDuration, int amplifier, int maxAmplifier, int poisonDelay) {
			this.poison = poison;
			this.poisonDuration = poisonDuration;
			this.amplifier = amplifier;
			this.maxAmplifier = maxAmplifier;
			this.poisonDelay = poisonDelay;
		}

		void incrementAmplifier(int increment) {
			amplifier = MathHelper.clamp(amplifier + increment, 0, maxAmplifier);
		}

		void incrementDuration(int poisonDuration) {
			this.poisonDuration += poisonDuration;
		}
	}

	@Nullable
	public static Poison getPoisonById(int id) {
		for (Poison poison : POISON_LIST) {
			if (poison.id == id) {
				return poison;
			}
		}
		return null;
	}

	public static void curePlayer(EntityPlayer player) {
		IPoison poisonCap = player.getCapability(PoisonCapability.CAP_POISON, null);
		assert poisonCap != null;

		poisonCap.setPoisons(new LinkedList<>());
	}

	/**
	 * Poisons a player
	 * @param player The player to poison
	 * @param amplifier Amplifier starting from 0
	 */
	public void poisonPlayer(EntityPlayer player, int amplifier) {
		IPoison poisonCap = player.getCapability(PoisonCapability.CAP_POISON, null);
		assert poisonCap != null;

		for (PoisonEffect poisonEffect : poisonCap.getPoisons()) {
			if (poisonEffect.poison == this) {
				poisonEffect.incrementDuration(durationIncrement);
				poisonEffect.poisonDelay /= 2;

				//If player was poisoned, increment amplifier (if <= 0, increase amplifier by 1)
				poisonEffect.incrementAmplifier(Math.max(amplifier, 1));
				poisonCap.addPoison(poisonEffect);
				return;
			}
		}

		poisonCap.addPoison(new PoisonEffect(this, initialDuration, amplifier, maxAmplifier, poisonDelay));
	}

	/**
	 * Poisons a player
	 * @param player The player to poison
	 */
	public void poisonPlayer(EntityPlayer player) {
		this.poisonPlayer(player, 0);
	}

	/**
	 * Removes poison from a player
	 * @param player The player to remove poison from
	 */
	public void removePoisonFromPlayer(EntityPlayer player) {
		IPoison poisonCap = player.getCapability(PoisonCapability.CAP_POISON, null);
		assert poisonCap != null;

		for (PoisonEffect poisonEffect : new LinkedList<>(poisonCap.getPoisons())) {
			if (poisonEffect.poison == this) {
				poisonCap.removePoison(poisonEffect);
			}
		}
	}

	/**
	 * Fires every PlayerTick
	 * @param player The player that has been poisoned
	 * @param poisonDuration Current duration of poison
	 * @param amplifier Current amplifier
	 */
	public void poisonEffect(EntityPlayer player, int poisonDuration, int amplifier) {}
}
