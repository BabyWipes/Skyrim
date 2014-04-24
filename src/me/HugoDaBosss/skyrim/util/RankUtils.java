package me.HugoDaBosss.skyrim.util;

import me.HugoDaBosss.skyrim.Users.Rank;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.HugoDaBosss.skyrim.Skyrim;

public class RankUtils 
{

	public static boolean isHighEnough(CommandSender sender, Rank needed)
	{
		if(!(sender instanceof Player))
			return true;
		Rank r = Skyrim.Users.getUserSender(sender).getRank();
		if(r.getInt() >= needed.getInt())
			return true;
		return false;
	}
	
	public static boolean isHighEnough(Rank rank, Rank needed)
	{
		if(rank.getInt() >= needed.getInt())
			return true;
		return false;
	}
	
	public static boolean isHighEnough(Player p, Rank needed)
	{
		Rank r = Skyrim.Users.getUserPlayer(p).getRank();
		if(r.getInt() >= needed.getInt())
			return true;
		return false;
	}
	
}
