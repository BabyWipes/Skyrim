package me.HugoDaBosss.skyrim.commands;

import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.User;
import me.HugoDaBosss.skyrim.Users.Rank;
import me.HugoDaBosss.skyrim.util.RankUtils;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMoney implements org.bukkit.command.CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("money"))
		{
			if(args.length == 0)
			{
				if(!(sender instanceof org.bukkit.entity.Player))
				{
					sender.sendMessage(ChatColor.RED + "Consoles don't have money, you faggot");
				}
				else
				{
					User u = me.HugoDaBosss.skyrim.Skyrim.Users.getUserSender(sender);
					sender.sendMessage(ChatColor.GREEN + "Your Gold: " + ChatColor.RED + "" + u.getMoney() + "g");
				}
			}
			else if(args.length == 1)
			{
				Player p = Skyrim.getPlayer(args[0]);
				if(p == null)
					sender.sendMessage(ChatColor.RED + "That user is not online!");
				else
				{
					User u = me.HugoDaBosss.skyrim.Skyrim.Users.getUserPlayer(p);
					sender.sendMessage(ChatColor.GREEN + "Gold of " + u.getName() + ": " + ChatColor.RED + "" + u.getMoney() + "g");
				}
			}
			else if(args.length == 2)
			{
				sender.sendMessage(ChatColor.RED + "Please use /gold [player]");
			}
			else if(args.length == 3)
			{
				if(!RankUtils.isHighEnough(sender, Rank.ADMIN))
				{
					sender.sendMessage(ChatColor.RED + "You don't have permission!");
					return true;
				}
				else if(args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("add"))
				{
					Player p = Skyrim.getPlayer(args[1]);
					if(p == null)
						sender.sendMessage(ChatColor.RED + "That user is not online!");
					else
					{
						User u = me.HugoDaBosss.skyrim.Skyrim.Users.getUserPlayer(p);
						int i = 0;
						try{
						i = Integer.parseInt(args[2]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.RED + "Please use /gold a|add <player> <value>");
						}
						u.addMoney(i);
						if(!args[1].equalsIgnoreCase(sender.getName()))
						sender.sendMessage(ChatColor.GREEN + args[2] + "G has been given to " + args[1]);
						p.sendMessage(ChatColor.GREEN + "You received " + args[2] + "g!");
					}	
				}
				else if(args[0].equalsIgnoreCase("r") || args[0].equalsIgnoreCase("remove"))
				{
					Player p = Skyrim.getPlayer(args[1]);
					if(p == null)
						sender.sendMessage(ChatColor.RED + "That user is not online!");
					else
					{
						User u = me.HugoDaBosss.skyrim.Skyrim.Users.getUserPlayer(p);
						int i = 0;
						try{
						i = Integer.parseInt(args[2]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.RED + "Please use /gold r|remove <player> <value>");
						}
						int m = u.removeMoney(i);
						if(m == -1)
							u.setMoney(0);
						if(!args[1].equalsIgnoreCase(sender.getName()))
						sender.sendMessage(ChatColor.GREEN + args[2] + "G has been taken from " + args[1]);
						p.sendMessage(ChatColor.GREEN + "You lost " + args[2] + "g!");
					}	
				}
				else if(args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("set"))
				{
					Player p = Skyrim.getPlayer(args[1]);
					if(p == null)
						sender.sendMessage(ChatColor.RED + "That user is not online!");
					else
					{
						User u = me.HugoDaBosss.skyrim.Skyrim.Users.getUserPlayer(p);
						int i = 0;
						try{
						i = Integer.parseInt(args[2]);
						}catch(Exception e){
							sender.sendMessage(ChatColor.RED + "Please use /gold s|set <player> <value>");
						}
						u.setMoney(i);
						if(!args[1].equalsIgnoreCase(sender.getName()))
						sender.sendMessage(ChatColor.GREEN + args[2] + "G is the new balance of " + args[1]);
						p.sendMessage(ChatColor.GREEN + "You now have " + args[2] + "g!");
					}	
				}
				else
				{
					sender.sendMessage(ChatColor.RED + "Please use /gold add|remove|set <player> <value>");
				}
			}
			return true;
		}
		return false;
	}

	
	
}
