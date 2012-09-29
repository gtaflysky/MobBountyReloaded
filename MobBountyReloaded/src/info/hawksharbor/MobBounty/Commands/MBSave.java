package info.hawksharbor.MobBounty.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MBSave implements CommandExecutor
{
	private final info.hawksharbor.MobBounty.MobBountyReloaded _plugin;

	public MBSave(info.hawksharbor.MobBounty.MobBountyReloaded plugin)
	{
		_plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args)
	{
		if (!(sender instanceof Player))
		{
			if (sender instanceof ConsoleCommandSender)
			{
				_plugin.getAPIManager().getConfigManager().saveConfig();

				String message = _plugin.getAPIManager().getLocaleManager()
						.getString("MBSSaved");
				if (message != null)
					sender.sendMessage(message);
				return true;
			}
			sender.sendMessage("Commands are designed to be run by players only.");
			return true;
		}
		Player player = ((Player) sender);
		if (_plugin.getAPIManager().getPermissionsManager()
				.hasPermission(player, "mbr.command.mbs"))
		{
			_plugin.getAPIManager().getConfigManager().saveConfig();

			String message = _plugin.getAPIManager().getLocaleManager()
					.getString("MBSSaved");
			if (message != null)
				sender.sendMessage(message);
		}
		else
		{
			String message = _plugin.getAPIManager().getLocaleManager()
					.getString("NoAccess");
			if (message != null)
				sender.sendMessage(message);
		}

		return true;
	}
}