package me.HugoDaBosss.skyrim.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandTutorial implements org.bukkit.command.CommandExecutor 
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("tutorial"))
		{
			
		}
		return false;
	}
	
}
