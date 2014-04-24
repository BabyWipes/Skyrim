package me.HugoDaBosss.skyrim.commands;

import me.HugoDaBosss.skyrim.Skyrim;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

@SuppressWarnings("unused")
public class SkyrimCommand implements org.bukkit.command.CommandExecutor
{

	protected Plugin plugin;
	protected me.HugoDaBosss.skyrim.Skyrim Skyrim;

	public SkyrimCommand(Plugin plugin, me.HugoDaBosss.skyrim.Skyrim skyrim)
	{
		this.plugin = plugin;
		this.Skyrim = skyrim;
	}

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		return false;
	}
	
}
