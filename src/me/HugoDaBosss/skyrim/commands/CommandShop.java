package me.HugoDaBosss.skyrim.commands;

import java.util.ArrayList;

import me.HugoDaBosss.skyrim.Skyrim;
import me.HugoDaBosss.skyrim.util.Locations;
import me.HugoDaBosss.skyrim.util.Locations.Names;
import me.HugoDaBosss.skyrim.util.RegionUtils;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandShop implements org.bukkit.command.CommandExecutor
{

	public CommandShop() 
	{
		players = new ArrayList<String>();
		shop = Locations.getLocation(Names.SHOP);
	}
	
	private ArrayList<String> players;
	private Location shop;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if(cmd.getName().equalsIgnoreCase("shop"))
		{
			if(!(sender instanceof Player))
			{
				sender.sendMessage(ChatColor.RED + "Stupid console");
				return true;
			}
			if(players.contains(sender.getName()))return true;
			Player p = (Player)sender;
			if(RegionUtils.isInSpawn(p.getLocation()))
			{
				p.teleport(shop);
				p.sendMessage(ChatColor.GOLD + "Teleported to shops");
				final String player = p.getName();
				players.add(player);
				new org.bukkit.scheduler.BukkitRunnable()
				{
					@Override
					public void run() 
					{
						players.remove(player);
					}
					
				}.runTaskLater(me.HugoDaBosss.skyrim.Skyrim.getPlugin(), 80L);
				return true;
			}
			p.sendMessage(ChatColor.GOLD + "Teleporting to the shops in 5...");
			final String name = p.getName();
			players.add(p.getName());
			new org.bukkit.scheduler.BukkitRunnable()
			{
				int left = 100;
				double x = 0;
				double z = 0;
				
				@Override
				public void run() {
					left -= 20;
					Player p = Skyrim.getPlayer(name);
					org.bukkit.Location l = p.getLocation();
					if(x != 0)
					{
						if(x != l.getX() || z != l.getZ())
						{
							p.sendMessage(ChatColor.RED + "Teleportation cancelled due to movement");
							players.remove(name);
							cancel();
							return;
						}
					}
					x = l.getX();
					z = l.getZ();
					if(left == 0)
					{
						p.teleport(shop);
						p.sendMessage(ChatColor.GOLD + "Teleported to shops");
						players.remove(name);
						cancel();
						return;
					}
				}
				
			}.runTaskTimer(Skyrim.getPlugin(), 20L, 20L);
			
			return true;
		}
		return false;
	}	
}
