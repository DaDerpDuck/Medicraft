package daderpduck.medicraft.init;

import daderpduck.medicraft.base.BloodRegenModifierBase;
import daderpduck.medicraft.blood.BloodRegenModifier;

import java.util.LinkedList;

public class ModBloodRegenModifier {
	public static final LinkedList<BloodRegenModifier> MODIFIERS = new LinkedList<>();

	public static final BloodRegenModifier BLEEDING_MODIFIER = new BloodRegenModifierBase("CCF6C0FE-069E-4D73-93A3-BACC80B73616", 0);
}
