package me.HugoDaBosss.skyrim.commands;

import me.HugoDaBosss.skyrim.User;
import me.HugoDaBosss.skyrim.Users.Rank;
import me.HugoDaBosss.skyrim.util.RankUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandUser extends SkyrimCommand
{

	public CommandUser(Plugin plugin, me.HugoDaBosss.skyrim.Skyrim skyrim) {
		super(plugin, skyrim);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("user"))
		{
			if(!RankUtils.isHighEnough(sender, Rank.OWNER))
			{
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
			if(args.length == 0)
			{
				sender.sendMessage(ChatColor.RED + "Not enough arguments!");
			}
			else if(args[0].equalsIgnoreCase("rank"))
			{
				if(args.length <= 2)
				{
					sender.sendMessage(ChatColor.RED + "Use: /user rank <rank> <user>");
				}				
				else
				{
					Rank r = me.HugoDaBosss.skyrim.Skyrim.Users.toRank(args[1]);
					if(r == Rank.NONE)
					{
						sender.sendMessage(ChatColor.RED + args[1] + " isn't a valid rank!");
						return true;
					}
					Player p = Skyrim.getPlayer(args[2]);
					if(p == null)
						sender.sendMessage(ChatColor.RED + "That user is not online!");
					else
					{
						User u = me.HugoDaBosss.skyrim.Skyrim.Users.getUserPlayer(p);
						u.setRank(r);
						sender.sendMessage(ChatColor.GREEN + "You made " + u.getName() + " " + r.getName() + "!");
						p.sendMessage(ChatColor.GREEN + "You are now " + r.getName() + "!");
					}
				}
			}
			return true;
		}
		return false;
	}

	
	
}
