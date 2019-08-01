package daderpduck.medicraft.init;

import daderpduck.medicraft.base.ItemBase;
import daderpduck.medicraft.items.SyringeEmpty;
import daderpduck.medicraft.items.VialEmpty;
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
}
