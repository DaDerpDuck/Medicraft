package daderpduck.medicraft.entities;

import com.mojang.authlib.GameProfile;
import com.sun.istack.internal.Nullable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.apache.commons.lang3.ObjectUtils;

import java.util.concurrent.atomic.AtomicReference;

public class EntityUnconsciousBody extends EntityLiving {
	private final AtomicReference<GameProfile> gameProfile = new AtomicReference<>();

	private EntityUnconsciousBody(World worldIn) {
		super(worldIn);

		setSize(0.85F, 0.75F);
	}

	public EntityUnconsciousBody(EntityPlayer player) {
		this(player.getEntityWorld());
		setPosition(player.posX, player.posY+0.5,  player.posZ);

		motionX = player.motionX;
		motionY = player.motionY+0.0784000015258789;
		motionZ = player.motionZ;
	}

	private void setGameProfile(GameProfile gameProfile) {
		this.gameProfile.set(gameProfile);
	}

	@Nullable
	public GameProfile getGameProfile() {
		return gameProfile.get();
	}

	private String oldName = null;
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();

		String nameUpdate = getCustomNameTag();
		if (ObjectUtils.notEqual(oldName,nameUpdate)) {
			oldName = nameUpdate;
			if (nameUpdate.trim().length() > 0) {
				GameProfile gp = new GameProfile(null, nameUpdate);
				if (getEntityWorld().isRemote) gp = TileEntitySkull.updateGameprofile(gp);
				setGameProfile(gp);
				setCustomNameTag(nameUpdate);
			} else {
				setGameProfile(null);
				setCustomNameTag("");
			}
		}
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	public void playLivingSound() {}

	@Override
	protected SoundEvent getAmbientSound() {
		return null;
	}

	@Override
	protected Item getDropItem() {
		return null;
	}

	@Override
	public boolean canBeSteered() {
		return false;
	}

	@Override
	public boolean canBeLeashedTo(EntityPlayer player) {
		return !this.getLeashed(); //LET'S DRAG THE UNCONSCIOUS BODY
	}

	@Override
	public boolean canPickUpLoot() {
		return false;
	}
}
