package me.HugoDaBosss.skyrim.commands;

import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.Users.Rank;
import me.HugoDaBosss.skyrim.util.Locations;
import me.HugoDaBosss.skyrim.util.RankUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CommandLocation implements org.bukkit.command.CommandExecutor
{
	
	@Override
	public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("location"))
		{
			if(!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.RED + "Stupid console");
				return true;
			}
			if(!RankUtils.isHighEnough(sender, Rank.ADMIN))
			{
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
			if(args.length > 0)
			{
				Locations.setLocation(Skyrim.getPlayer(sender.getName()).getLocation(), args[0]);
				sender.sendMessage(ChatColor.GREEN + "Updated location " + args[0]);
			}
			else
			{
				sender.sendMessage(ChatColor.GOLD + "rl_out1, rl_out2, rl_in1, rl_in2");
			}
			return true;
		}
		return false;
	}
	
}
