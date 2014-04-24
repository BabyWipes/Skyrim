package me.HugoDaBosss.skyrim.commands;

import me.HugoDaBosss.skyrim.User;
import me.HugoDaBosss.skyrim.Users.Rank;
import me.HugoDaBosss.skyrim.shouts.ShoutType;
import me.HugoDaBosss.skyrim.util.RankUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandShouts extends SkyrimCommand 
{

	public CommandShouts(Plugin plugin, me.HugoDaBosss.skyrim.Skyrim skyrim) {
		super(plugin, skyrim);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("shouts"))
		{
			if(!RankUtils.isHighEnough(sender, Rank.MODERATOR))
			{
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
			if(args.length == 1)
			{
				if(args[0].equalsIgnoreCase("list"))
				{
					String shouts = ChatColor.GOLD + "All shouts: ";
					for(ShoutType st : ShoutType.values())
					{
						shouts += st.getFullName() + ", ";
					}
					shouts = shouts.substring(0, shouts.length() -3);
					sender.sendMessage(shouts);
				}
			}
			else if(args.length < 3)
			{
				sender.sendMessage(ChatColor.RED + "Please use /shouts unlock|lock <shout> <player>");
			}
			else
			{
				if(args[0].equalsIgnoreCase("u") || args[0].equalsIgnoreCase("unlock"))
				{
					Player p = Skyrim.getPlayer(args[2]);
					if(p == null)
						sender.sendMessage(ChatColor.RED + "That user is not online!");
					else
					{
						User u = me.HugoDaBosss.skyrim.Skyrim.Users.getUserPlayer(p);
						if(args[1].equalsIgnoreCase("all"))
						{
							for(ShoutType type : ShoutType.values())
							{
								if(type != ShoutType.NONE && type != ShoutType.RELOADING && type != ShoutType.BLOCKED)
								u.unlockShout(type);
							}
							if(!sender.getName().equalsIgnoreCase(u.getName()))
								sender.sendMessage(ChatColor.GREEN + "Unlocked all shouts for " + u.getName());
							u.toPlayer().sendMessage(ChatColor.GREEN + "You unlocked all shouts!");
							return true;							
						}
						ShoutType st = me.HugoDaBosss.skyrim.Skyrim.Shouts.getShout(args[1]);
						if(st == ShoutType.NONE)
						{
							sender.sendMessage(ChatColor.RED + "That shout doesn't exist, use /shouts list");
						}
						else
						{
							if(u.unlockShout(st))
							{
								if(!sender.getName().equalsIgnoreCase(u.getName()))
									sender.sendMessage(ChatColor.GREEN + "Unlocked " + st.getFullName() + ChatColor.GREEN + "  for " + u.getName());
								u.toPlayer().sendMessage(ChatColor.GREEN + "You unlocked the shout " + st.getFullName() + ChatColor.GREEN + "!");
							}
							else
								sender.sendMessage(ChatColor.RED + "That shout is already unlocked by " + u.getName());
						}
					}
				}
				else if(args[0].equalsIgnoreCase("l") || args[0].equalsIgnoreCase("lock"))
				{
					Player p = Skyrim.getPlayer(args[0]);
					if(p == null)
						sender.sendMessage(ChatColor.RED + "That user is not online!");
					else
					{
						User u = me.HugoDaBosss.skyrim.Skyrim.Users.getUserPlayer(p);
						ShoutType st = me.HugoDaBosss.skyrim.Skyrim.Shouts.getShout(args[1]);
						if(st == ShoutType.NONE)
						{
							sender.sendMessage(ChatColor.RED + "That shout doesn't exist, use /shouts list");
						}
						else
						{
							if(u.lockShout(st))
							{
								if(!sender.getName().equalsIgnoreCase(u.getName()))
									sender.sendMessage(ChatColor.GREEN + "Locked " + st.getFullName() + ChatColor.GREEN + "  for " + u.getName());
								u.toPlayer().sendMessage(ChatColor.GREEN + "You forget how to use " + st.getFullName() + ChatColor.GREEN + "!");
							}
							else
								sender.sendMessage(ChatColor.RED + "That shout is still locked for " + u.getName());
						}
					}
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "Please use /shouts unlock|lock <shout> <player>");
				}
			}
			return true;
		}
		return false;		
	}

	
	
}
