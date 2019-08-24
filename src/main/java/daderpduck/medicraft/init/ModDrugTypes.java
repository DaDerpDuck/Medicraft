package daderpduck.medicraft.init;

import daderpduck.medicraft.base.DrugType;

import java.util.ArrayList;
import java.util.List;

public class ModDrugTypes {
	public static final List<DrugType> DRUG_TYPES = new ArrayList<>();
	private static int id = 0;

	static {
		new DrugType("blood", 0x8A0303, id++).setDrug(ModDrugs.BLOOD);
		new DrugType("sufforin", 0x872D2D, id++).setDrug(ModDrugs.SUFFORIN);
		new DrugType("sufforin_antidote", 0x4A91B5, id++).setDrug(ModDrugs.SUFFORIN_ANTIDOTE);
		new DrugType("hemostasin", 0xBD9058, id++).setDrug(ModDrugs.HEMOSTASIN);
		new DrugType("cyanide", 0x115DD5, id++).setDrug(ModDrugs.CYANIDE);
		new DrugType("cyanide_antidote", 0x48ACF0, id++).setDrug(ModDrugs.CYANIDE_ANTIDOTE);
		new DrugType("oxygen_microparticles", 0x8CA6B9, id++).setDrug(ModDrugs.OXYGEN_MICROPARTICLES);
		new DrugType("deliriozine", 0xB4ADEA, id++).setDrug(ModDrugs.DELIRIOZINE);
	}
}
