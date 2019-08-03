package daderpduck.medicraft.base;

import daderpduck.medicraft.init.ModDamageSources;
import daderpduck.medicraft.init.ModPotions;
import daderpduck.medicraft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class CustomPotion extends Potion {
	private boolean unPressKeysOnJump = false;
	private float damageOnJump = 0;
	private DamageSource damageSourceOnJump = ModDamageSources.WOUND;
	private IAttribute attribute;
	private AttributeModifier attributeModifier;
	private List<ItemStack> curativeItems;

	public CustomPotion(String name, boolean isBadPotion, int iconIndexX, int iconIndexY) {
		super(isBadPotion, 0);
		setPotionName("effect." + name);
		setIconIndex(iconIndexX, iconIndexY);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, name));

		ModPotions.POTIONS.add(this);
	}
	public CustomPotion(String name, boolean isBadPotion, int iconIndexX, int iconIndexY, List<ItemStack> curativeItems) {
		this(name, isBadPotion, iconIndexX, iconIndexY);

		this.curativeItems = curativeItems;
	}

	/**
	 * Will the keys be unpressed after jumping
	 * Keys unpressed in WorldEvents
	 */
	public CustomPotion unPressKeysOnJump() {
		this.unPressKeysOnJump = true;

		return this;
	}

	public boolean isUnPressKeysOnJump() {
		return this.unPressKeysOnJump;
	}

	/**
	 * Controls how much damage will be inflicted after jumping
	 * Damage given in WorldEvents
	 */
	public CustomPotion setDamageOnJump(float damage) {
		this.damageOnJump = damage;

		return this;
	}

	public float getDamageOnJump() {
		return this.damageOnJump;
	}

	/**
	 * Sets the damage source that'll be used to damage the player on jump
	 * Damage given in WorldEvents
	 */
	public CustomPotion setDamageSourceOnJump(DamageSource damageSource) {
		this.damageSourceOnJump = damageSource;

		return this;
	}

	public DamageSource getDamageSourceOnJump() {
		return this.damageSourceOnJump;
	}

	/**
	 * Sets what attribute modifier this potion gives
	 */
	public CustomPotion setAttributeModifier(IAttribute attribute, AttributeModifier attributeModifier) {
		this.attribute = attribute;
		this.attributeModifier = attributeModifier;

		return this;
	}

	public IAttribute getAttribute() {
		return this.attribute;
	}

	public AttributeModifier getAttributeModifier() {
		return this.attributeModifier;
	}

	public CustomPotion setCurativeItems(List<ItemStack> curativeItems) {
		this.curativeItems = curativeItems;

		return this;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasStatusIcon() {
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/potion_effects.png"));
		return true;
	}

	@Nonnull
	@Override
	public List<ItemStack> getCurativeItems() {
		return curativeItems == null ? super.getCurativeItems() : curativeItems;
	}
}