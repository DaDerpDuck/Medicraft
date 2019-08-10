package daderpduck.medicraft.items;

import daderpduck.medicraft.Main;
import daderpduck.medicraft.base.DrugType;
import daderpduck.medicraft.capabilities.DrugCapability;
import daderpduck.medicraft.capabilities.IDrug;
import daderpduck.medicraft.events.message.MessageClientSyncDrugs;
import daderpduck.medicraft.init.ModDrugTypes;
import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.network.NetworkHandler;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
		setMaxStackSize(16);

		setCreativeTab(Main.MEDICRAFT_TAB);
		ModItems.ITEMS.add(this);
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems) {
		if (tab == Main.MEDICRAFT_TAB || tab == CreativeTabs.SEARCH) {
			for (DrugType drugType : ModDrugTypes.DRUG_TYPES) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("DrugId", drugType.getId());

				ItemStack subStack = new ItemStack(this);
				subStack.setTagCompound(tag);

				subItems.add(subStack);
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
		DrugType drugType = DrugType.getDrugTypeFromNBT(stack);

		if (entityLiving instanceof EntityPlayer && drugType != null) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			stack.shrink(1);

			if (drugType.getDrug() != null) {
				drugType.getDrug().drugPlayer(player);
			}

			if (!stack.isEmpty()) {
				if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.SYRINGE_EMPTY))) {
					player.dropItem(new ItemStack(ModItems.SYRINGE_EMPTY), false);
				}
			}
		}

		return stack.isEmpty() ? new ItemStack(ModItems.SYRINGE_EMPTY) : stack;
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		DrugType drugType = DrugType.getDrugTypeFromNBT(stack);

		if (attacker instanceof EntityPlayer && target instanceof EntityPlayer && drugType != null) {
			EntityPlayer attackerPlayer = (EntityPlayer) attacker;
			EntityPlayer targetPlayer = (EntityPlayer) target;

			stack.shrink(1);

			if (!attackerPlayer.inventory.addItemStackToInventory(new ItemStack(ModItems.SYRINGE_EMPTY))) {
				attackerPlayer.dropItem(new ItemStack(ModItems.SYRINGE_EMPTY), false);
			}

			if (drugType.getDrug() != null) {
				drugType.getDrug().drugPlayer(targetPlayer);

				IDrug drugCap = targetPlayer.getCapability(DrugCapability.CAP_DRUG, null);
				assert drugCap != null;
				NetworkHandler.FireClient(new MessageClientSyncDrugs(drugCap.getAllDrugs()), (EntityPlayerMP) targetPlayer);

				return true;
			}
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	public static class liquidColor implements IItemColor {
		@Override
		public int colorMultiplier(@Nonnull ItemStack stack, int tintIndex) {
			switch (tintIndex) {
				case 0:
					return Color.WHITE.getRGB();
				case 1: {
					DrugType drugType = DrugType.getDrugTypeFromNBT(stack);

					if (drugType != null) {
						return drugType.getColor();
					}
				}
				default:
					return Color.BLACK.getRGB();
			}
		}
	}
}
