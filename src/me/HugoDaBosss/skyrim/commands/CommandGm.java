package me.HugoDaBosss.skyrim.commands;

import me.HugoDaBosss.skyrim.Users.Rank;
import me.HugoDaBosss.skyrim.util.RankUtils;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandGm extends SkyrimCommand
{

	public CommandGm(Plugin plugin, me.HugoDaBosss.skyrim.Skyrim skyrim) {
		super(plugin, skyrim);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("static-access")
	@Override
	public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("gm"))
		{
			if(!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.RED + "Stupid Console");
				return true;
			}
			if(!RankUtils.isHighEnough(sender, Rank.ADMIN))
			{
				sender.sendMessage(ChatColor.RED + "You don't have permission!");
				return true;
			}
			if(args.length == 0)
			{
				sender.sendMessage(ChatColor.RED + "Please use /gm <0|1|2>");
			}
			else if(args.length == 1)
			{
				if(args[0].length() > 1)
				{
					sender.sendMessage(ChatColor.RED + "Please use /gm <0|1|2>");
				}
				else
				{
					int gm = -1;
					try
					{
						gm = Integer.parseInt(args[0]);
					}
					catch(Exception e)
					{
						sender.sendMessage(ChatColor.RED + "Please use /gm <0|1|2>");
						return true;
					}
					Player p = (Player) sender;
					if(gm == 0)
					{
						p.setGameMode(GameMode.SURVIVAL);
					}
					else if(gm == 1)
					{
						p.setGameMode(GameMode.CREATIVE);
					}
					else if(gm == 2)
					{
						p.setGameMode(GameMode.ADVENTURE);
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "Please use /gm <0|1|2>");
					}
				}
			}
			else if(args.length == 2)
			{
				if(args[0].length() > 1)
				{
					sender.sendMessage(ChatColor.RED + "Please use /gm <0|1|2> <player>");
				}
				else
				{
					int gm = -1;
					try
					{
						gm = Integer.parseInt(args[0]);
					}
					catch(Exception e)
					{
						sender.sendMessage(ChatColor.RED + "Please use /gm <0|1|2> <player>");
						return true;
					}
					if(Skyrim.getPlayer(args[1]) == null)
					{
						sender.sendMessage(ChatColor.RED + "That player is not online!");
					}
					Player p = Skyrim.getPlayer(args[1]);
					if(gm == 0)
					{
						p.setGameMode(GameMode.SURVIVAL);
					}
					else if(gm == 1)
					{
						p.setGameMode(GameMode.CREATIVE);
					}
					else if(gm == 2)
					{
						p.setGameMode(GameMode.ADVENTURE);
					}
					else
					{
						sender.sendMessage(ChatColor.RED + "Please use /gm <0|1|2> <player>");
						return true;
					}
					sender.sendMessage(ChatColor.GREEN + "Changed the gamemode of " + args[1] + " to " + p.getGameMode().toString());
				}
			}
			return true;
		}
		return false;
	}

}
