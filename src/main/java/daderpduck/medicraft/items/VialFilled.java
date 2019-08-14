package daderpduck.medicraft.items;

import daderpduck.medicraft.Main;
import daderpduck.medicraft.base.DrugType;
import daderpduck.medicraft.init.ModDrugTypes;
import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.util.DrugUtil;
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
		setContainerItem(ModItems.VIAL_EMPTY);

		setCreativeTab(Main.MEDICRAFT_TAB);
		ModItems.ITEMS.add(this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
		if (tab == Main.MEDICRAFT_TAB || tab == CreativeTabs.SEARCH) {
			for (DrugType drugType : ModDrugTypes.DRUG_TYPES) {
				if (drugType.getDrug() == null) continue;
				subItems.add(DrugUtil.getVialFromDrug(drugType.getDrug(), 1));
			}
		}
	}

	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		DrugType drugType = DrugType.getDrugTypeFromNBT(stack);

		if (drugType != null) {
			return super.getUnlocalizedName() + "." + drugType.getName();
		}

		return super.getUnlocalizedName();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		DrugType drugType = DrugType.getDrugTypeFromNBT(stack);

		if (drugType != null) {
			TextComponentTranslation textComponent = new TextComponentTranslation("vial."+drugType.getName()+".description");
			tooltip.add(textComponent.getFormattedText());
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
				DrugType drugType = DrugType.getDrugTypeFromNBT(stack);

				if (drugType != null) {
					return drugType.getColor();
				}
			}
			return Color.BLACK.getRGB();
		}
	}
}
