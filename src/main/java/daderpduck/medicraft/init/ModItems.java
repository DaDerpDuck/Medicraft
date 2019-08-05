package daderpduck.medicraft.init;

import daderpduck.medicraft.base.ItemBase;
import daderpduck.medicraft.items.SyringeEmpty;
import daderpduck.medicraft.items.SyringeFilled;
import daderpduck.medicraft.items.VialEmpty;
import daderpduck.medicraft.items.VialFilled;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ModItems {
	public static final List<Item> ITEMS = new ArrayList<>();

	//Items
	public static final Item YOGURT = new ItemBase("yogurt");
	public static final Item BANDAGE = new ItemBase("bandage");
	public static final Item SYRINGE_EMPTY = new SyringeEmpty("syringe_empty");
	public static final Item VIAL_EMPTY = new VialEmpty("vial_empty");
	public static final Item SYRINGE_FILLED = new SyringeFilled("syringe_filled");
	public static final Item VIAL_FILLED = new VialFilled("vial_filled");
}
