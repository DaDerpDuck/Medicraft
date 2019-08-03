package daderpduck.medicraft.capabilities;

import daderpduck.medicraft.poisons.Poison;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;

public class PoisonCapability {
	@CapabilityInject(IPoison.class)
	public static Capability<IPoison> CAP_POISON;

	public static void register() {
		CapabilityManager.INSTANCE.register(IPoison.class, new PoisonStorage(), ImplementationPoison::new);
		CapabilityAttach.addCapability("poison", new PoisonProvider(), new PoisonSyncFunction());
	}

	/**
	 * Default implementation of blood
	 */
	public static class ImplementationPoison implements IPoison {
		private List<Poison.PoisonEffect> poisonEffects = new LinkedList<>();

		@Override
		public void setPoisons(List<Poison.PoisonEffect> poisonEffects) {
			this.poisonEffects = poisonEffects;
		}

		@Override
		public List<Poison.PoisonEffect> getPoisons() {
			return poisonEffects;
		}

		@Override
		public void addPoison(Poison.PoisonEffect poisonEffect) {
			//Cleanup duplicates
			for (Poison.PoisonEffect poisonEffect1 : poisonEffects) {
				if (poisonEffect1 == poisonEffect) poisonEffects.remove(poisonEffect1);
			}

			poisonEffects.add(poisonEffect);
		}

		@Override
		public void removePoison(Poison.PoisonEffect poisonEffect) {
			poisonEffects.remove(poisonEffect);
		}
	}

	/**
	 * Poison storage
	 */
	public static class PoisonStorage implements Capability.IStorage<IPoison> {

		@Nullable
		@Override
		public NBTBase writeNBT(Capability<IPoison> capability, IPoison instance, EnumFacing side) {
			NBTTagList nbtTagList = new NBTTagList();

			for (Poison.PoisonEffect poisonEffect : instance.getPoisons()) {
				NBTTagCompound nbtTagCompound = new NBTTagCompound();
				nbtTagCompound.setInteger("poisonId", poisonEffect.poison.id);
				nbtTagCompound.setInteger("poisonDuration", poisonEffect.poisonDuration);
				nbtTagCompound.setInteger("amplifier", poisonEffect.amplifier);
				nbtTagCompound.setInteger("poisonDelay", poisonEffect.poisonDelay);

				nbtTagList.appendTag(nbtTagCompound);
			}

			return nbtTagList;
		}

		@Override
		public void readNBT(Capability<IPoison> capability, IPoison instance, EnumFacing side, NBTBase nbt) {
			NBTTagList nbtTagList = (NBTTagList) nbt;

			for (NBTBase nbtBase : nbtTagList) {
				NBTTagCompound nbtTagCompound = (NBTTagCompound) nbtBase;
				Poison poison = Poison.getPoisonById(nbtTagCompound.getInteger("poisonId"));
				if (poison == null) continue;

				instance.addPoison(new Poison.PoisonEffect(
						poison,
						nbtTagCompound.getInteger("poisonDuration"),
						nbtTagCompound.getInteger("amplifier"),
						poison.maxAmplifier,
						nbtTagCompound.getInteger("poisonDelay")
				));
			}
		}
	}

	/**
	 * Poison provider
	 */
	public static class PoisonProvider implements ICapabilitySerializable<NBTBase> {
		private final IPoison instance = CAP_POISON.getDefaultInstance();

		@Override
		public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
			return capability == CAP_POISON;
		}

		@Nullable
		@Override
		public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
			return capability == CAP_POISON ? CAP_POISON.cast(instance) : null;
		}

		@Override
		public NBTBase serializeNBT() {
			return CAP_POISON.getStorage().writeNBT(CAP_POISON, instance, null);
		}

		@Override
		public void deserializeNBT(NBTBase nbt) {
			CAP_POISON.getStorage().readNBT(CAP_POISON, instance, null, nbt);
		}
	}

	/**
	 * Sync implementation of poison
	 */
	static class PoisonSyncFunction implements CapabilityAttach.RunnableSyncFunction {
		@Override
		public void run(PlayerEvent event) {

		}
	}
}
