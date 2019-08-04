package daderpduck.medicraft.base;

import daderpduck.medicraft.drugs.Drug;
import daderpduck.medicraft.init.ModDrugTypes;

import javax.annotation.Nullable;

public class DrugType {
	private final String name;
	private final int color;
	private final int id;

	private Drug drug;

	public DrugType(String name, int color, int id) {
		this.name = name;
		this.color = color;
		this.id = id;

		ModDrugTypes.DRUG_TYPES.add(this);
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

	public DrugType setDrug(Drug drug) {
		this.drug = drug;
		return this;
	}

	@Nullable
	public Drug getDrug() {
		return drug;
	}
}
