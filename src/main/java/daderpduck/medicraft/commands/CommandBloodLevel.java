package daderpduck.medicraft.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

public class CommandBloodLevel extends CommandBase {

	@Nonnull
	@Override
	public String getName() {
		return "bloodlevel";
	}

	@Nonnull
	@Override
	public String getUsage(@Nonnull ICommandSender sender) {
		return "commands.medicraft.bloodlevel.usage";
	}

	@Override
	public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) throws CommandException {
		EntityPlayerMP senderAsPlayer = getCommandSenderAsPlayer(sender);



		if (args.length < 1) {
			sender.sendMessage(new TextComponentString(sender.getName() + "has blood level of "));
		}

		double amount = parseDouble(args[0]);
	}

	@Override
	public int getRequiredPermissionLevel()
	{
		return 3;
	}
}
