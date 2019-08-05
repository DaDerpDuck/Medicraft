package daderpduck.medicraft.init;

import daderpduck.medicraft.base.DrugType;

import java.util.ArrayList;
import java.util.List;

public class ModDrugTypes {
	public static final List<DrugType> DRUG_TYPES = new ArrayList<>();
	private static int id = 0;

	public static final DrugType BLOOD = new DrugType("blood", 0x8A0303, id++)
			.setDrug(ModDrugs.BLOOD);
	public static final DrugType SUFFORIN = new DrugType("sufforin", 0x872D2D, id++)
			.setDrug(ModDrugs.SUFFORIN);
	public static final DrugType SUFFORIN_ANTIDOTE = new DrugType("sufforin_antidote", 0x4A91B5, id++)
			.setDrug(ModDrugs.SUFFORIN_ANTIDOTE);
}
