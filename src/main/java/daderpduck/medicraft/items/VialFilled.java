package daderpduck.medicraft.items;

import daderpduck.medicraft.Main;
import daderpduck.medicraft.base.DrugType;
import daderpduck.medicraft.init.ModDrugTypes;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public class VialFilled extends Item {
	public VialFilled(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(64);

		setCreativeTab(Main.MEDICRAFT_TAB);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
		for (DrugType drugType : ModDrugTypes.DRUG_TYPES) {
			if (tab == Main.MEDICRAFT_TAB || tab == CreativeTabs.SEARCH) {
				int metadata = drugType.getId();
				ItemStack subItemStack = new ItemStack(this, 1, metadata);
				subItems.add(subItemStack);
			}
		}
	}

	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();

		for (DrugType drugType : ModDrugTypes.DRUG_TYPES) {
			if (drugType.getId() == metadata)
				return super.getUnlocalizedName() + "." + drugType.getName();
		}

		return super.getUnlocalizedName();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		int metadata = stack.getMetadata();
		for (DrugType drugType : ModDrugTypes.DRUG_TYPES) {
			if (drugType.getId() == metadata) {
				TextComponentTranslation textComponent = new TextComponentTranslation("vial."+drugType.getName()+".description");
				tooltip.add(textComponent.getFormattedText());
			}
		}
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@SideOnly(Side.CLIENT)
	public static class liquidColor implements IItemColor {
		@Override
		public int colorMultiplier(@Nonnull ItemStack stack, int tintIndex) {
			if (tintIndex == 0) {
				int metadata = stack.getMetadata();
				for (DrugType drugType : ModDrugTypes.DRUG_TYPES) {
					if (drugType.getId() == metadata) return drugType.getColor();
				}
			}
			return Color.BLACK.getRGB();
		}
	}
}
