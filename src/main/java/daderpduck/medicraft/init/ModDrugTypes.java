package daderpduck.medicraft.init;

import daderpduck.medicraft.base.DrugType;

import java.util.ArrayList;
import java.util.List;

public class ModDrugTypes {
	public static final List<DrugType> DRUG_TYPES = new ArrayList<>();
	private static int id = 0;

	public static final DrugType BLOOD = new DrugType("blood", 0x8A0303, id++).setDrug(ModDrugs.BLOOD);
	public static final DrugType SUFFORIN = new DrugType("sufforin", 0x872D2D, id++).setDrug(ModDrugs.SUFFORIN);
	public static final DrugType SUFFORIN_ANTIDOTE = new DrugType("sufforin_antidote", 0x4A91B5, id++).setDrug(ModDrugs.SUFFORIN_ANTIDOTE);
	public static final DrugType HEMOSTASIN = new DrugType("hemostasin", 0xBD9058, id++).setDrug(ModDrugs.HEMOSTASIN);
	public static final DrugType CYANIDE = new DrugType("cyanide", 0x115DD5, id++).setDrug(ModDrugs.CYANIDE);
	public static final DrugType CYANIDE_ANTIDOTE = new DrugType("cyanide_antidote", 0x48ACF0, id++).setDrug(ModDrugs.CYANIDE_ANTIDOTE);
	public static final DrugType OXYGEN_MICROPARTICLES = new DrugType("oxygen_microparticles", 0x8CA6B9, id++).setDrug(ModDrugs.OXYGEN_MICROPARTICLES);
}
