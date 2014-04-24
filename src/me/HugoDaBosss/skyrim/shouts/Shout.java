package me.HugoDaBosss.skyrim.shouts;

import me.HugoDaBosss.skyrim.Skyrim;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Shout 
{

	protected Player p;
	protected String word1, word2, word3;
	protected Sound sound;
	protected int volume;
	protected double pitch;
	protected BukkitRunnable shout;
	
	public Shout()
	{
	}
	
	public void shout(final Player p)
	{
		this.p = p;
		final org.bukkit.World w = p.getLocation().getWorld();
		p.sendMessage(ChatColor.ITALIC + word1);
		w.playSound(p.getLocation(), Sound.CAT_HISS, 2, (float)0.8);
		Bukkit.getScheduler().runTaskLater(Skyrim.getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				p.sendMessage(ChatColor.ITALIC + word2);
				w.playSound(p.getLocation(), Sound.CAT_HISS, 3, (float)0.6);
			}
		}, 15);
		Bukkit.getScheduler().runTaskLater(Skyrim.getPlugin(), new Runnable()
		{
			@Override
			public void run()
			{
				p.sendMessage(ChatColor.ITALIC + "" + ChatColor.BOLD + word3.toUpperCase());
				w.playSound(p.getLocation(), sound, volume, (float)pitch);
				w.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0,2);
				w.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0,2);
				w.playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 0,2);
				BukkitRunnable a = shout;
				a.runTaskLater(Skyrim.getPlugin(), 1);
			}
		}, 35);
	}
	
}
