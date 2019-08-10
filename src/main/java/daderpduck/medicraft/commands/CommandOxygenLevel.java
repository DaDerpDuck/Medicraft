package daderpduck.medicraft.commands;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.events.message.MessageClientSyncBlood;
import daderpduck.medicraft.network.NetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandOxygenLevel extends CommandBase {
	private static final String USAGE_TRANSLATION = "commands.medicraft.oxygenlevel.usage";
	private static final String GET_TRANSLATION = "commands.medicraft.oxygenlevel.success.get";
	private static final String SET_TRANSLATION = "commands.medicraft.oxygenlevel.success.set";

	@Nonnull
	@Override
	public String getName() {
		return "oxygenlevel";
	}

	@Nonnull
	@Override
	public String getUsage(@Nonnull ICommandSender sender) {
		return USAGE_TRANSLATION;
	}

	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP senderAsPlayer = getCommandSenderAsPlayer(sender);
		IBlood bloodCap = senderAsPlayer.getCapability(BloodCapability.CAP_BLOOD, null);
		assert bloodCap != null;

		if (args.length <= 0) {
			//Get blood amount
			senderAsPlayer.sendMessage(new TextComponentTranslation(GET_TRANSLATION, senderAsPlayer.getDisplayName(), bloodCap.getOxygen()));
		} else if (args.length == 1) {
			String s = args[0];

			try {
				//Get blood amount of specified player
				EntityPlayerMP target = getPlayer(server, sender, s);
				IBlood targetBloodCap = target.getCapability(BloodCapability.CAP_BLOOD, null);
				assert targetBloodCap != null;

				senderAsPlayer.sendMessage(new TextComponentTranslation(GET_TRANSLATION, target.getDisplayName(), targetBloodCap.getOxygen()));

				return;
			} catch (CommandException ignored) {
			}

			//Set blood amount of sender
			double amount = parseDouble(s);

			bloodCap.setOxygen((float) amount);
			notifyCommandListener(sender, this, SET_TRANSLATION, sender.getDisplayName(), bloodCap.getOxygen());
			NetworkHandler.FireClient(new MessageClientSyncBlood(bloodCap.getBlood(), bloodCap.getMaxBlood(), bloodCap.getOxygen()), senderAsPlayer);
		} else {
			EntityPlayerMP target = getPlayer(server, sender, args[0]);
			double amount = parseDouble(args[1]);

			IBlood targetBloodCap = target.getCapability(BloodCapability.CAP_BLOOD, null);
			assert targetBloodCap != null;

			targetBloodCap.setOxygen((float) amount);
			notifyCommandListener(sender, this, SET_TRANSLATION, target.getDisplayName(), targetBloodCap.getOxygen());
			NetworkHandler.FireClient(new MessageClientSyncBlood(bloodCap.getBlood(), bloodCap.getMaxBlood(), bloodCap.getOxygen()), target);
		}
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}

	@Nonnull
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
}
