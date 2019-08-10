package daderpduck.medicraft.base;

import daderpduck.medicraft.drugs.Drug;
import daderpduck.medicraft.init.ModDrugTypes;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Objects;

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

	@Nullable
	public static DrugType getDrugTypeFromNBT(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (Objects.requireNonNull(stack.getTagCompound()).hasKey("DrugId")) {
				int drugId = stack.getTagCompound().getInteger("DrugId");
				for (DrugType drugType : ModDrugTypes.DRUG_TYPES) {
					if (drugType.getId() == drugId) return drugType;
				}
			}
		}
		return null;
	}
}
