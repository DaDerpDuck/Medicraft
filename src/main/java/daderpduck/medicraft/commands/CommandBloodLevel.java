package daderpduck.medicraft.commands;

import daderpduck.medicraft.capabilities.BloodCapability;
import daderpduck.medicraft.capabilities.IBlood;
import daderpduck.medicraft.events.message.MessageClientSyncBlood;
import daderpduck.medicraft.network.NetworkHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandBloodLevel extends CommandBase {
	private static final String USAGE_TRANSLATION = "commands.medicraft.bloodlevel.usage";
	private static final String GET_TRANSLATION = "commands.medicraft.bloodlevel.success.get";
	private static final String SET_TRANSLATION = "commands.medicraft.bloodlevel.success.set";

	@Nonnull
	@Override
	public String getName() {
		return "bloodlevel";
	}

	@Nonnull
	@Override
	public String getUsage(@Nonnull ICommandSender sender) {
		return USAGE_TRANSLATION;
	}

	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP senderAsPlayer = getCommandSenderAsPlayer(sender);
		IBlood bloodCap = senderAsPlayer.getCapability(BloodCapability.BloodProvider.CAP_BLOOD, null);
		assert bloodCap != null;

		if (args.length <= 0) {
			//Get blood amount
			notifyCommandListener(sender, this, GET_TRANSLATION, sender.getDisplayName(), bloodCap.getBlood());
		} else if (args.length == 1) {
			String s = args[0];

			try {
				//Get blood amount of specified player
				EntityPlayerMP target = getPlayer(server, sender, s);
				IBlood targetBloodCap = target.getCapability(BloodCapability.BloodProvider.CAP_BLOOD, null);
				assert targetBloodCap != null;

				notifyCommandListener(sender, this, GET_TRANSLATION, target.getDisplayName(), targetBloodCap.getBlood());

				return;
			} catch (CommandException ignored) {}

			//Set blood amount of sender
			double amount = parseDouble(s);

			bloodCap.setBlood((float) amount);
			notifyCommandListener(sender, this, SET_TRANSLATION, sender.getDisplayName(), bloodCap.getBlood());
			NetworkHandler.FireClient(new MessageClientSyncBlood(bloodCap.getBlood()), senderAsPlayer);
		} else {
			EntityPlayerMP target = getPlayer(server, sender, args[0]);
			double amount = parseDouble(args[1]);

			IBlood targetBloodCap = target.getCapability(BloodCapability.BloodProvider.CAP_BLOOD, null);
			assert targetBloodCap != null;

			targetBloodCap.setBlood((float) amount);
			notifyCommandListener(sender, this, SET_TRANSLATION, target.getDisplayName(), targetBloodCap.getBlood());
			NetworkHandler.FireClient(new MessageClientSyncBlood(bloodCap.getBlood()), target);
		}
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return index == 0;
	}

	@Nonnull
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
	{
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.emptyList();
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 2;
	}
}
