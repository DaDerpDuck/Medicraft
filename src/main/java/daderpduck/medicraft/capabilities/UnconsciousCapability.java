package daderpduck.medicraft.capabilities;

import daderpduck.medicraft.network.NetworkHandler;
import daderpduck.medicraft.network.message.MessageClientSyncUnconscious;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UnconsciousCapability {
	@CapabilityInject(IUnconscious.class)
	public static Capability<IUnconscious> CAP_UNCONSCIOUS;

	public static void register() {
		CapabilityManager.INSTANCE.register(IUnconscious.class, new UnconsciousCapability.UnconsciousStorage(), UnconsciousCapability.ImplementationUnconscious::new);
		CapabilityAttach.addCapability("unconscious", new UnconsciousCapability.UnconsciousProvider(), new UnconsciousCapability.UnconsciousSyncFunction());
	}

	/**
	 * Default implementation
	 */
	public static class ImplementationUnconscious implements IUnconscious {
		boolean unconscious = false;
		Vec3d lastPos = new Vec3d(0, 0, 0);

		@Override
		public void setUnconscious(boolean flag) {
			unconscious = flag;
		}

		@Override
		public boolean getUnconscious() {
			return unconscious;
		}

		@Override
		public void setLastPos(double posX, double posY, double posZ) {
			lastPos = new Vec3d(posX, posY, posZ);
		}

		@Override
		public Vec3d getLastPos() {
			return lastPos;
		}


		@Override
		public void sync(EntityPlayerMP player) {
			(new UnconsciousSyncFunction()).run(player);
		}
	}

	/**
	 * Storage
	 */
	public static class UnconsciousStorage implements Capability.IStorage<IUnconscious> {

		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IUnconscious> capability, IUnconscious instance, EnumFacing side) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setByte("unconscious", (byte) (instance.getUnconscious() ? 1 : 0));

			NBTTagList nbtLastPos = new NBTTagList();
			Vec3d pos = instance.getLastPos();
			nbtLastPos.appendTag(new NBTTagDouble(pos.x));
			nbtLastPos.appendTag(new NBTTagDouble(pos.y));
			nbtLastPos.appendTag(new NBTTagDouble(pos.z));

			nbt.setTag("lastPos", nbtLastPos);

			return nbt;
		}

		@Override
		public void readNBT(Capability<IUnconscious> capability, IUnconscious instance, EnumFacing side, NBTBase nbt) {
			NBTTagCompound nbtTagCompound = (NBTTagCompound) nbt;
			instance.setUnconscious(nbtTagCompound.getByte("unconscious") == 1);

			NBTTagList lastPos = (NBTTagList) nbtTagCompound.getTag("lastPos");
			instance.setLastPos(lastPos.getDoubleAt(0), lastPos.getDoubleAt(1), lastPos.getDoubleAt(2));
		}
	}

	/**
	 * Provider
	 */
	public static class UnconsciousProvider implements ICapabilitySerializable<NBTBase> {
		private final IUnconscious instance = CAP_UNCONSCIOUS.getDefaultInstance();

		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
			return capability == CAP_UNCONSCIOUS;
		}

		@Nullable
		@Override
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
			return capability == CAP_UNCONSCIOUS ? CAP_UNCONSCIOUS.cast(instance) : null;
		}

		@Override
		public NBTBase serializeNBT() {
			return CAP_UNCONSCIOUS.getStorage().writeNBT(CAP_UNCONSCIOUS, instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			CAP_UNCONSCIOUS.getStorage().readNBT(CAP_UNCONSCIOUS, instance, null, nbt);
		}
	}

	/**
	 * Sync implementation
	 */
	static class UnconsciousSyncFunction implements CapabilityAttach.RunnableSyncFunction {
		@Override
		public void run(EntityPlayerMP player) {
			IUnconscious unconscious = player.getCapability(UnconsciousCapability.CAP_UNCONSCIOUS, null);
			assert unconscious != null;
			NetworkHandler.FireClient(new MessageClientSyncUnconscious(unconscious.getUnconscious()), player);
		}
	}
}
