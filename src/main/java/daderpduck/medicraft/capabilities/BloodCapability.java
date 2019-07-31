package daderpduck.medicraft.capabilities;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTPrimitive;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BloodCapability {
	public static void register(CapabilityManager INSTANCE) {
		INSTANCE.register(IBlood.class, new BloodStorage(), DefaultImplementationBlood::new);
	}

	public static class DefaultImplementationBlood implements IBlood {
		private final float maxBloodLevel = 2000F;
		private float bloodLevel = 2000F;

		@Override
		public void increase(float points) {
			bloodLevel += points;
			bloodLevel = Math.min(bloodLevel, maxBloodLevel);
		}

		@Override
		public void decrease(float points) {
			bloodLevel -= points;
		}

		@Override
		public void setBlood(float points) {
			bloodLevel = Math.min(points, maxBloodLevel);
		}

		@Override
		public float getBlood() {
			return bloodLevel;
		}

		@Override
		public float getMaxBlood() {
			return maxBloodLevel;
		}
	}

	public static class BloodStorage implements Capability.IStorage<IBlood> {

		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IBlood> capability, IBlood instance, EnumFacing side) {
			return new NBTTagFloat(instance.getBlood());
		}

		@Override
		public void readNBT(Capability<IBlood> capability, IBlood instance, EnumFacing side, NBTBase nbt) {
			instance.setBlood(((NBTPrimitive) nbt).getFloat());
		}
	}

	public static class BloodProvider implements ICapabilitySerializable<NBTBase> {

		@CapabilityInject(IBlood.class)
		public static Capability<IBlood> CAP_BLOOD;

		private final IBlood instance = CAP_BLOOD.getDefaultInstance();

		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
			return capability == CAP_BLOOD;
		}

		@Nullable
		@Override
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
			return capability == CAP_BLOOD ? CAP_BLOOD.cast(instance) : null;
		}

		@Override
		public NBTBase serializeNBT() {
			return CAP_BLOOD.getStorage().writeNBT(CAP_BLOOD, instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			CAP_BLOOD.getStorage().readNBT(CAP_BLOOD, instance, null, nbt);
		}
	}
}
