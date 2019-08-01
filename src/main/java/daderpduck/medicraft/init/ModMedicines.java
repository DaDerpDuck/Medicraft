package daderpduck.medicraft.init;

import daderpduck.medicraft.base.Medicine;

import java.util.ArrayList;
import java.util.List;

public class ModMedicines {
	public static final List<Medicine> MEDICINES = new ArrayList<>();
	private static int id = 0;

	public static final Medicine SUFFORIN = new Medicine("sufforin",0x872D2D, id++);
}
