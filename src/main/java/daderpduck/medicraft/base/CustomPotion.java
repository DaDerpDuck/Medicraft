package daderpduck.medicraft.base;

import daderpduck.medicraft.init.ModDamageSources;
import daderpduck.medicraft.init.ModPotions;
import daderpduck.medicraft.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CustomPotion extends Potion {
	private boolean hideParticles = false;
	private boolean unPressKeysOnJump = false;
	private float damageOnJump = 0;
	private DamageSource damageSourceOnJump = ModDamageSources.WOUND;
	private IAttribute attribute;
	private AttributeModifier attributeModifier;

	public CustomPotion(String name, boolean isBadPotion, int iconIndexX, int iconIndexY, int color) {
		super(isBadPotion, color);
		setPotionName("effect." + name);
		setIconIndex(iconIndexX, iconIndexY);
		setRegistryName(new ResourceLocation(Reference.MOD_ID, name));

		ModPotions.POTIONS.add(this);
	}

	/**
	 * Function for hiding the particles while still showing the icon
	 * Amplifier is set to -1 in WorldEvents
	 */
	public CustomPotion hideParticles(boolean invisible) {
		this.hideParticles = invisible;

		return this;
	}

	public boolean isParticlesHidden() {
		return this.hideParticles;
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

	@SideOnly(Side.CLIENT)
	@Override
	public boolean hasStatusIcon() {
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID, "textures/gui/potion_effects.png"));
		return true;
	}
}