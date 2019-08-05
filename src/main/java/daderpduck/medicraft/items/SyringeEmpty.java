package daderpduck.medicraft.items;

import daderpduck.medicraft.Main;
import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.init.ModDrugs;
import daderpduck.medicraft.init.ModItems;
import daderpduck.medicraft.util.IHasModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class SyringeEmpty extends Item implements IHasModel {

	public SyringeEmpty(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setMaxStackSize(16);
		setCreativeTab(Main.MEDICRAFT_TAB);

		ModItems.ITEMS.add(this);
	}

	//Drawing blood
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
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			IBlood bloodCap = player.getCapability(BloodCapability.CAP_BLOOD, null);
			assert bloodCap != null;

			bloodCap.decrease(100);

			stack.shrink(1);

			if (!stack.isEmpty()) {
				if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.SYRINGE_FILLED, 1, ModDrugs.BLOOD.id))) {
					player.dropItem(new ItemStack(ModItems.SYRINGE_FILLED, 1, ModDrugs.BLOOD.id), false);
				}
			}
		}

		return stack.isEmpty() ? new ItemStack(ModItems.SYRINGE_FILLED, 1, ModDrugs.BLOOD.id) : stack;
	}

	@Override
	public void registerModels() {
		Main.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
