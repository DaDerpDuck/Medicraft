package daderpduck.medicraft.capabilities;

import daderpduck.medicraft.drugs.Drug;

import java.util.List;

public interface IDrug {
	void setDrugs(List<Drug.DrugEffect> drugEffects);
	List<Drug.DrugEffect> getDrugs();

	void addDrug(Drug.DrugEffect drugEffect);
	void removeDrug(Drug.DrugEffect drugEffect);
}
