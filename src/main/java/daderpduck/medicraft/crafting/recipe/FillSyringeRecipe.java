package daderpduck.medicraft.crafting.recipe;

import com.google.gson.JsonObject;
import daderpduck.medicraft.items.VialFilled;
import daderpduck.medicraft.util.RecipeUtil;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;

@SuppressWarnings({"unused", "WeakerAccess"})
public class FillSyringeRecipe extends ShapelessOreRecipe {
	public FillSyringeRecipe(ResourceLocation group, NonNullList<Ingredient> input, ItemStack result) {
		super(group, input, result);
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(@Nonnull InventoryCrafting inv) {
		ItemStack output = super.getCraftingResult(inv);

		if (!output.isEmpty()) {
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack ingrediant = inv.getStackInSlot(i);

				if (!ingrediant.isEmpty() && ingrediant.getItem() instanceof VialFilled && ingrediant.getTagCompound() != null && ingrediant.getTagCompound().getInteger("DrugId") != 0) {
					NBTTagCompound tag = new NBTTagCompound();
					tag.setInteger("DrugId", ingrediant.getTagCompound().getInteger("DrugId"));

					output.setTagCompound(tag);

					break;
				}
			}
		}

		return output;
	}

	@Nonnull
	@Override
	public String getGroup() {
		return group == null ? "" : group.toString();
	}

	public static class Factory implements IRecipeFactory {
		@Override
		public IRecipe parse(JsonContext context, JsonObject json) {
			String group = JsonUtils.getString(json, "group", "");
			NonNullList<Ingredient> ingredients = RecipeUtil.parseShapeless(context, json);
			ItemStack result = CraftingHelper.getItemStack(JsonUtils.getJsonObject(json, "result"), context);

			return new FillSyringeRecipe(group.isEmpty() ? null : new ResourceLocation(group),ingredients, result);
		}
	}
}
