package daderpduck.medicraft.capabilities;

import daderpduck.medicraft.poisons.Poison;

import java.util.List;

public interface IPoison {
	void setPoisons(List<Poison.PoisonEffect> poisonEffects);
	List<Poison.PoisonEffect> getPoisons();

	void addPoison(Poison.PoisonEffect poisonEffect);
	void removePoison(Poison.PoisonEffect poisonEffect);
}
