package daderpduck.medicraft.base;

import daderpduck.medicraft.init.ModMedicines;

public class Medicine {
	private final String name;
	private final int color;
	private final int id;

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
}
