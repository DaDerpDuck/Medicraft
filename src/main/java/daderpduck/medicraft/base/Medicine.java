package daderpduck.medicraft.base;

import daderpduck.medicraft.init.ModMedicines;
import daderpduck.medicraft.poisons.Poison;

import javax.annotation.Nullable;

public class Medicine {
	private final String name;
	private final int color;
	private final int id;

	private Poison poison;

	public Medicine(String name, int color, int id) {
		this.name = name;
		this.color = color;
		this.id = id;

		ModMedicines.MEDICINES.add(this);
	}

	public String getName() {
		return name;
	}

	public int getColor() {
		return color;
	}

	public int getId() {
		return id;
	}

	public Medicine setPoison(Poison poison) {
		this.poison = poison;
		return this;
	}

	@Nullable
	public Poison getPoison() {
		return poison;
	}
}
