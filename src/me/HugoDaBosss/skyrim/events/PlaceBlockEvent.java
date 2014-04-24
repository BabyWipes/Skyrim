package me.HugoDaBosss.skyrim.events;

import org.bukkit.ChatColor;

public class PlaceBlockEvent implements org.bukkit.event.Listener
{

	public PlaceBlockEvent(){}
	
	@org.bukkit.event.EventHandler
	public void placeblock(org.bukkit.event.block.BlockPlaceEvent e)
	{
		if(e.getPlayer().getGameMode() != org.bukkit.GameMode.CREATIVE)
		{
			e.setCancelled(true);
			return;
		}
	}
	
	@SuppressWarnings("unused")
	@org.bukkit.event.EventHandler
	public void placesign(org.bukkit.event.block.SignChangeEvent e)
	{
		String lines[] = e.getLines();
		if(e.getLine(0).contains("[buy]"))
		{
			int i;
			try{
				i = Integer.parseInt(e.getLine(3));
			}catch(Exception ex)
			{
				ex.printStackTrace();
				return;
				}
			if(lines[1].equalsIgnoreCase(""))
				e.setLine(0, ChatColor.UNDERLINE + "=======");
			else
				e.setLine(0, ChatColor.UNDERLINE + lines[1]);
			e.setLine(1, ChatColor.BOLD + lines[2]);
			e.setLine(2, ChatColor.ITALIC + "buy for");
			e.setLine(3, lines[3] + "g");
		}
		else if(e.getLine(0).contains("[random]"))
		{
			e.setLine(0, ChatColor.ITALIC + "click for");
			e.setLine(1, ChatColor.BOLD + "Random");
			e.setLine(2, ChatColor.BOLD + "Location");
			e.setLine(3, "");
		}
		else
		{
			e.setLine(0, ChatColor.translateAlternateColorCodes('&', lines[0]));
			e.setLine(1, ChatColor.translateAlternateColorCodes('&', lines[1]));
			e.setLine(2, ChatColor.translateAlternateColorCodes('&', lines[2]));
			e.setLine(3, ChatColor.translateAlternateColorCodes('&', lines[3]));
		}
	}
}
