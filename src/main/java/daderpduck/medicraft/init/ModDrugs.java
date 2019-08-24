package daderpduck.medicraft.init;

import daderpduck.medicraft.drugs.Drug;
import daderpduck.medicraft.drugs.antidotes.CyanideAntidote;
import daderpduck.medicraft.drugs.antidotes.SufforinAntidote;
import daderpduck.medicraft.drugs.medicines.Blood;
import daderpduck.medicraft.drugs.medicines.Hemostasin;
import daderpduck.medicraft.drugs.medicines.OxygenMicroparticles;
import daderpduck.medicraft.drugs.poisons.Cyanide;
import daderpduck.medicraft.drugs.poisons.Deliriozine;
import daderpduck.medicraft.drugs.poisons.Sufforin;

public class ModDrugs {
	public static final Drug BLOOD = new Blood();
	public static final Drug SUFFORIN = new Sufforin();
	public static final Drug SUFFORIN_ANTIDOTE = new SufforinAntidote();
	public static final Drug HEMOSTASIN = new Hemostasin();
	public static final Drug CYANIDE = new Cyanide();
	public static final Drug CYANIDE_ANTIDOTE = new CyanideAntidote();
	public static final Drug OXYGEN_MICROPARTICLES = new OxygenMicroparticles();
	public static final Drug DELIRIOZINE = new Deliriozine();
}
