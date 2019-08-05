package daderpduck.medicraft.drugs;

import daderpduck.medicraft.capabilities.DrugCapability;
import daderpduck.medicraft.capabilities.IDrug;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Drug {
	private static int idCounter = 0;
	private static final List<Drug> DRUG_LIST = new ArrayList<>();

	public final int id;
	public final int maxAmplifier;
	private int initialDuration;
	private int durationIncrement;
	private int drugDelay;

	/**
	 * Constructs a drug
	 * @param maxAmplifier Sets the maximum amplifier
	 * @param initialDuration How long drug initially lasts for in ticks
	 * @param durationIncrement Amount to increment in substantial injections
	 * @param drugDelay How long in ticks until drug effects starts
	 */
	public Drug(int maxAmplifier, int initialDuration, int durationIncrement, int drugDelay) {
		this.id = idCounter++;
		this.maxAmplifier = maxAmplifier;
		this.initialDuration = initialDuration;
		this.durationIncrement = durationIncrement;
		this.drugDelay = drugDelay;

		DRUG_LIST.add(this);
	}

	public Drug(int maxAmplifier) {
		this(maxAmplifier, 0, 0, 0);
	}

	/**
	 * Constructs a drug that acts instantly
	 */
	public Drug() {
		this(0, 0, 0, 0);
	}

	protected void setDrugDelay(int drugDelay) {
		this.drugDelay = drugDelay;
	}

	protected void setInitialDuration(int initialDuration) {
		this.initialDuration = initialDuration;
	}

	protected void setDurationIncrement(int durationIncrement) {
		this.durationIncrement = durationIncrement;
	}

	public static class DrugEffect {
		public final Drug drug;
		public int drugDuration;
		public int amplifier;
		private final int maxAmplifier;
		public int drugDelay;

		public DrugEffect(Drug drug, int drugDuration, int amplifier, int maxAmplifier, int drugDelay) {
			this.drug = drug;
			this.drugDuration = drugDuration;
			this.amplifier = amplifier;
			this.maxAmplifier = maxAmplifier;
			this.drugDelay = drugDelay;
		}

		void incrementAmplifier(int increment) {
			amplifier = MathHelper.clamp(amplifier + increment, 0, maxAmplifier);
		}

		void incrementDuration(int poisonDuration) {
			this.drugDuration += poisonDuration;
		}
	}

	@Nullable
	public static Drug getDrugById(int id) {
		for (Drug drug : DRUG_LIST) {
			if (drug.id == id) {
				return drug;
			}
		}
		return null;
	}

	/**
	 * Removes any drug effects from a player
	 * @param player The player to cure
	 */
	public static void curePlayer(EntityPlayer player) {
		IDrug drugCap = player.getCapability(DrugCapability.CAP_DRUG, null);
		assert drugCap != null;

		drugCap.setDrugs(new LinkedList<>());
	}

	/**
	 * Drugs a player
	 * @param player The player to drug
	 * @param amplifier Amplifier starting from 0
	 */
	public void drugPlayer(EntityPlayer player, int amplifier) {
		IDrug drugCap = player.getCapability(DrugCapability.CAP_DRUG, null);
		assert drugCap != null;

		for (Drug.DrugEffect drugEffect : drugCap.getAllDrugs()) {
			if (drugEffect.drug == this) {
				drugEffect.incrementDuration(durationIncrement);
				drugEffect.drugDelay /= 2;

				//If player was poisoned, increment amplifier (if <= 0, increase amplifier by 1)
				drugEffect.incrementAmplifier(Math.max(amplifier, 1));
				drugCap.addDrug(drugEffect);
				return;
			}
		}

		drugCap.addDrug(new Drug.DrugEffect(this, initialDuration, amplifier, maxAmplifier, drugDelay));
	}

	/**
	 * Drugs a player
	 * @param player The player to poison
	 */
	public void drugPlayer(EntityPlayer player) {
		this.drugPlayer(player, 0);
	}

	/**
	 * Removes drug from a player
	 * @param player The player to remove drug from
	 */
	public void removeDrugFromPlayer(EntityPlayer player) {
		IDrug drugCap = player.getCapability(DrugCapability.CAP_DRUG, null);
		assert drugCap != null;

		for (Drug.DrugEffect drugEffect : new LinkedList<>(drugCap.getAllDrugs())) {
			if (drugEffect.drug == this) {
				drugCap.removeDrug(drugEffect);
			}
		}
	}

	/**
	 * Fires every PlayerTick after drug delay has passed
	 * @param player The player that has been drugged
	 * @param drugDuration Current duration of drug
	 * @param amplifier Current amplifier
	 */
	public void drugEffect(EntityPlayer player, int drugDuration, int amplifier) {}

	/**
	 * Fires every PlayerTick  before drug delay has passed
	 * @param player The player that has been drugged
	 * @param drugDelay Current drug delay
	 * @param amplifier Current amplifier
	 */
	public void preDrugEffect(EntityPlayer player, int drugDelay, int amplifier) {}
}
