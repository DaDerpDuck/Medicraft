package daderpduck.medicraft.items;

import daderpduck.medicraft.Main;
import daderpduck.medicraft.base.Medicine;
import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.init.ModMedicines;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.awt.*;

public class SyringeFilled extends Item {

	public SyringeFilled(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setHasSubtypes(true);
		setMaxDamage(0);
		setMaxStackSize(1);

		setCreativeTab(Main.MEDICRAFT_TAB);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
		for (Medicine medicine : ModMedicines.MEDICINES) {
			if (tab == Main.MEDICRAFT_TAB || tab == CreativeTabs.SEARCH) {
				int metadata = medicine.getId();
				ItemStack subItemStack = new ItemStack(this, 1, metadata);
				subItems.add(subItemStack);
			}
		}
	}

	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int metadata = stack.getMetadata();

		for (Medicine medicine : ModMedicines.MEDICINES) {
			if (medicine.getId() == metadata)
				return super.getUnlocalizedName() + "." + medicine.getName();
		}

		return super.getUnlocalizedName();
	}

	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 30;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand hand) {
		ItemStack itemStack = playerIn.getHeldItem(hand);
		playerIn.setActiveHand(hand);
		return new ActionResult<>(EnumActionResult.PASS, itemStack);
	}

	@Nonnull
	@Override
	public ItemStack onItemUseFinish(@Nonnull ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		int metadata = stack.getMetadata();

		if (!worldIn.isRemote && entityLiving instanceof EntityPlayer) {
			stack.shrink(1);

			for (Medicine medicine : ModMedicines.MEDICINES) {
				if (medicine.getId() == metadata) {
					if (medicine.getPoison() != null) {
						medicine.getPoison().poisonPlayer((EntityPlayer) entityLiving);

						break;
					}
				}
			}
		}

		return stack.isEmpty() ? new ItemStack(ModItems.SYRINGE_EMPTY) : stack;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		if (attacker instanceof EntityPlayer && target instanceof EntityPlayer && !attacker.world.isRemote) {
			int metadata = stack.getMetadata();

			stack.shrink(1);

			for (Medicine medicine : ModMedicines.MEDICINES) {
				if (medicine.getId() == metadata) {
					if (medicine.getPoison() != null) {
						medicine.getPoison().poisonPlayer((EntityPlayer) target);

						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}

	@SideOnly(Side.CLIENT)
	public static class liquidColor implements IItemColor {
		@Override
		public int colorMultiplier(@Nonnull ItemStack stack, int tintIndex) {
			switch (tintIndex) {
				case 0:
					return Color.WHITE.getRGB();
				case 1: {
					int metadata = stack.getMetadata();
					for (Medicine medicine : ModMedicines.MEDICINES) {
						if (medicine.getId() == metadata) return medicine.getColor();
					}
				}
				default:
					return Color.BLACK.getRGB();
			}
		}
	}
}
