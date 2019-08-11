package daderpduck.medicraft.capabilities;

import daderpduck.medicraft.drugs.Drug;
import net.minecraft.entity.player.EntityPlayerMP;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IDrug {
	void setDrugs(@Nonnull List<Drug.DrugEffect> drugEffects);

	List<Drug.DrugEffect> getAllDrugs();

	void addDrug(Drug.DrugEffect drugEffect);

	@Nullable
	Drug.DrugEffect getActiveDrug(Drug drug);

	void removeDrug(Drug.DrugEffect drugEffect);

	void sync(EntityPlayerMP player);
}
