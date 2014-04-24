package me.HugoDaBosss.skyrim.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class CommandTutorial extends SkyrimCommand implements Listener{

	public CommandTutorial(Plugin plugin, me.HugoDaBosss.skyrim.Skyrim skyrim) {
		super(plugin, skyrim);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("tutorial"))
		{
			
		}
		return false;
	}
	
	//@EventHandler
	
	
	

}
