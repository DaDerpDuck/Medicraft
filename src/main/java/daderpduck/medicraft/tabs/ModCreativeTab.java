package daderpduck.medicraft.tabs;

import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.util.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class ModCreativeTab extends CreativeTabs {
	public ModCreativeTab(String label) {
		super(label);
		setBackgroundImageName(Reference.MOD_ID + ".png");
	}

	@Nonnull
	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ModItems.BANDAGE);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> itemsToShow) {
		System.out.println("HELLO WORLD!!!");
		for (Item item : Item.REGISTRY) {
			if (item != null) {
				System.out.println(item.getUnlocalizedName());
				if (item.getUnlocalizedName().contains("." + Reference.MOD_ID)) {
					item.getSubItems(CreativeTabs.SEARCH, itemsToShow);
				}
			}
		}
	}
}
